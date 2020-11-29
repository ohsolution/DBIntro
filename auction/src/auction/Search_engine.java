package auction;

import java.sql.*;
import java.util.*;

public class Search_engine 
{
    String arr[];
    int n;
    int tp;
    ArrayList<Integer> info;

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

        if(type == 5) 
        {
            arr[0] += "Sold ";
            arr[2] = "    sold price: ";
        }
        else if(type==6) 
        {
            arr[0] += "Purchased ";
            arr[2] = "    purchase price: ";
        }

        arr[0] += "Item";
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

                String crr[] = new String[n];                

				crr[0] = arr[0] + Integer.toString(num++)+']';
								
				for(int i=1;i<n;++i) crr[i] = arr[i] + rs.getString(i+1);
                
                if(tp==1) crr[2] += " bids";

                if(tp==1 && rs.getInt(3) == 0) crr[4] = arr[4] + " - ";

				for(int i=0;i<n;++i) System.out.println(crr[i]);				
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
        }
    
    }
}
