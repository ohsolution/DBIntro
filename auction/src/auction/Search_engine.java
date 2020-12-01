package auction;

import java.sql.*;
import java.util.*;

@SuppressWarnings("deprecation")
public class Search_engine 
{
    String arr[];
    int n;
    int tp;
    ArrayList<Integer> info;
    String status_message_1 = "You are the highest bidder.";
    String status_message_2 = "You are outbidded.";
    String status_message_3 = "You won the item.";
    String status_message_4 = "You are outbidded and the item is sold.";
    int month[];
    int sum = 0;
    int fee = 0;
    int prev = 0;
    int c = 0;
    int pm = 0;

    public Search_engine(int type,int sz)
    {
        arr = new String[sz];
        n = sz;
        tp = type;

        arr[1] = "    description: ";
        arr[2] = "    status: ";

        if(type == 1)
        {
            arr[3] = "    current bidding price: ";
            arr[4] = "    current highest bidder: ";
            arr[5] = "    date posted: ";
            arr[6] = "    bid ending date: ";
            arr[7] = "    buy-it-now price: ";
        }
        else if(type == 2)
        {
            arr[3] = "    sold price: ";
            arr[4] = "    buyer: ";
            arr[5] = "    sold date: ";
        }
        else if(type == 3)
        {
            arr[3] = "    your bidding price: ";
            arr[4] = "    current highest bidding price: ";
            arr[5] = "    bid ending date: ";
        }
        else if(type ==4)
        {
            arr[3] = "    sold price: ";
            arr[4] = "    sold date: ";
        }

        arr[0] = "[";

        if(type == 5 || type == 10) 
        {
            arr[0] += "Sold ";
            arr[2] = "    sold price: ";
            month = new int[13];
        }
        else if(type==6) 
        {
            arr[0] += "Purchased ";
            arr[2] = "    purchase price: ";
        }

        arr[0] += "Item";

        if(type==7)
        {
            arr[0] = "[ ";
            arr[1] = "    Total transaction amount: ";
            arr[2] = "    Total Number of transaction: ";
        }

        if(type == 8)
        {
            arr[0] = "[ TOP ";
            arr[1] = "    Total transaction amount: ";
            arr[2] = "    Total Number of transaction: ";
            arr[3] = "    User email : ";
            arr[4] = "    User name : ";
        }
        else if(type ==9)
        {
            arr[0] = "[ TOP ";
            arr[2] = "    a successful bidder: ";
            arr[3] = "    Total Number of bid: ";
            arr[4] = "    Hammer price: ";
        }
    }

    public void search(String select,int num)
    {    
        info = new ArrayList<Integer>();

        try {
            ResultSet rs = Driver.query(select);	                            

			while(rs.next())
			{	
                if(tp == 1)	
                {
                    info.add(rs.getInt(1));
                    info.add(rs.getInt(4));
                    info.add(rs.getInt(8));
                }
                else info.add(1);

                String crr[] = new String[n];                

				crr[0] = arr[0] + Integer.toString(num++)+']';
								
                if(tp <=2) for(int i=1;i<n;++i) crr[i] = arr[i] + rs.getString(i+1);
                else if(tp==3)
                {
                    crr[1] = arr[1] + rs.getString(1);
                    crr[2] = arr[2] + ((rs.getInt(2) == rs.getInt(3)) ? status_message_1 : status_message_2);
                    for(int i=3;i<n;++i) crr[i] = arr[i] + rs.getString(i-1);                   
                }
                else if(tp==4)
                {
                    crr[1] = arr[1] + rs.getString(1);
                    crr[2] = arr[2] + ((rs.getInt(2) == rs.getInt(3)) ? status_message_3 : status_message_4);
                    for(int i=3;i<n;++i) crr[i] = arr[i] + rs.getString(i);
                }
                else if(tp <7)
                {
                    crr[1] = arr[1] + rs.getString(1);
                    crr[2] = arr[2] + rs.getString(2);
                    sum += rs.getInt(2);
                    if(tp==5)
                    {
                        int m = ((rs.getTimestamp(3)).getMonth())+1;
                        if(++month[m] > 2) fee -= rs.getDouble(2) * 0.01;
                        else fee -= (int)((double)rs.getInt(2) * 0.02);
                    }                    
                }
                

                if(tp==7)
                {
                    crr[0] = arr[0] + rs.getString(3) + " ]";
                    crr[1] = arr[1] + rs.getInt(1) + " won";
                    crr[2] = arr[2] + rs.getInt(2);
                }

                if(tp>=8 && tp <10)
                {
                    for(int i=1;i<n;++i) crr[i] = arr[i] + rs.getString(i);
                }                
                
                if(tp==1) crr[2] += " bids";
                else if(tp==2) crr[2] = "    status: " + (rs.getInt(3)==0 ? "unsold" : "sold");


                if(tp==1 && rs.getInt(3) == 0) crr[4] = arr[4] + " - ";

                if(tp!=10) for(int i=0;i<n;++i) System.out.println(crr[i]);				
                else
                {
                    int m = ((rs.getTimestamp(3)).getMonth())+1;

                    if(prev != rs.getInt(1) || pm != m)
                    {
                        prev = rs.getInt(1);
                        pm = m;
                        c=0;
                    }
                    
                    if(++c > 2) month[m] += (int)(rs.getDouble(2) * 0.01);
                    else month[m] += (int)(rs.getDouble(2) * 0.02);
                }
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
        }
    
    }
}
