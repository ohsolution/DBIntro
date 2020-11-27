package auction;

import java.sql.*;

public class Home_menu extends Menu 
{
	private boolean root;
	public int index;

	public Home_menu(boolean ck)
	{
		root = ck;
		index = 0;
	}

	public void show_home_menu()
	{
		Manager.clean();
		this.pLine(0, "Main menu","");
		this.pLine(1,"Sell item","");
		this.pLine(2,"Status of Your Item Listed on Auction","");
		this.pLine(3,"Search item","");
		this.pLine(4,"Check Status of your Bid","");
		this.pLine(5,"Check your Account","");
		this.pLine(6,"Quit","");
	}
	
	public void exec()
	{
		show_home_menu();
		while((this.index = this.getInt(6))==-1) show_home_menu();		
	}
	
}
