
package auction;

import java.sql.*;

public class Sell_item_menu extends Menu 
{
    public int category = 0;
    public int condition = 0;
	public String description = "";
	public int buyPrice = 0;
	public String enddate = "";

	public void show_sell_item_menu(int idx)
	{
		Manager.clean();

		String c_cate = (category > 0 ? Integer.toString(category) : "");
		String c_condi = (condition > 0 ? Integer.toString(condition) : "");
		String c_bp = (buyPrice > 0 ? Integer.toString(buyPrice) : "");
		
		this.pLine(0, "Sell item", "");
		this.pLine(-1, "select from the following category",c_cate);
		this.pLine(1, "Electronics", "");
		this.pLine(2, "Books", "");
		this.pLine(3, "Home", "");
		this.pLine(4, "Clothing", "");
		this.pLine(5, "Sporting Goods", "");

		if(idx > 1)
		{
			this.pLine(-1, "condition",c_condi);
			this.pLine(1, "New", "");
			this.pLine(2, "Like-New", "");
			this.pLine(3, "Used (Good)", "");
			this.pLine(4, "Used (Acceptable)", "");
		}

		if(idx > 3)
		{
			this.pLine(-1,"description:",description);
			this.pLine(-1, "buy-it-now price:",c_bp);
		}		
		
	}
	
	public void exec()
	{
		int level = 1;
		show_sell_item_menu(level);
		while((this.category = this.getInt(5))==-1) show_sell_item_menu(level);
		

		++level;

		show_sell_item_menu(level);
		while((this.condition= this.getInt(4))==-1) show_sell_item_menu(level);
		
		++level;

		show_sell_item_menu(level);
		this.description = this.pQuest("---- description: ");

		++level;

		show_sell_item_menu(level);
		while((this.buyPrice = this.getInt(-1))==-1) show_sell_item_menu(level);

		++level;

		show_sell_item_menu(level);
		while(!check())
		{
			enddate = pQuest("---- bid ending date(yyyy-mm-dd HH:mm): ");
			
		}
	}

	public boolean check()
	{
		try 
		{
			
		} 
		catch (Exception e) {
			
			return false;
		}

		return true;
	}
}
