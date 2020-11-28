
package auction;

import java.sql.*;
import java.text.SimpleDateFormat;

public class Sell_item_menu extends Menu 
{
    public int category = 0;
    public int condition = 0;
	public String description = "";
	public int buyPrice = 0;
	public String enddate = "";
	public Timestamp ENDDATE;
	SimpleDateFormat transistor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
	
	public void exec(int id)
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
			enddate = pQuest("---- bid ending date(yyyy-mm-dd HH:mm): ") + ":00";			
		}


		String values = '\'' + Integer.toString(id) + '\'' + ','
						+'\'' + description + '\'' +','
						+'\'' + Integer.toString(condition) + '\''+','
						+'\'' + Integer.toString(category) + '\'';
						

		int itid = Driver.insert("item","user_id,description,condition_id,category_id", values,"item_id");

		values = '\'' + Integer.toString(itid) + '\'' + ','
		+'\'' + Integer.toString(buyPrice)+ '\''+','
		+'\'' + ENDDATE + '\'';

		Driver.insert("bid_info","item_id,buy_now_price,ending_date", values,"");		
	}

	public boolean check()
	{	
		try 
		{			
			ENDDATE = new Timestamp(transistor.parse(enddate).getTime());
		} 
		catch (Exception e) {
			
			return false;
		}

		return true;
		
	}
}
