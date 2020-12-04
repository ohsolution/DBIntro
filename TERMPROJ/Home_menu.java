package auction;

import java.sql.*;

public class Home_menu extends Menu 
{
	public int index;

	public Home_menu()
	{
		index = 0;
	}
	
	public void exec()
	{
		this.pLine(0, "Main menu","\n");
		this.pLine(1,"Sell item","\n");
		this.pLine(2,"Status of Your Item Listed on Auction","\n");
		this.pLine(3,"Search item","\n");
		this.pLine(4,"Check Status of your Bid","\n");
		this.pLine(5,"Check your Account","\n");
        
        System.out.println("----(Q) Quit");

		while((this.index = this.getTargetInt("","Q",1,5))==-1);		
	}
	
}
