
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
	
	public void exec(int id)
	{

		this.pLine(0, "Sell item", "\n");
		this.pLine(-1, "select from the following category","\n");
		this.pLine(1, "Electronics", "\n");
		this.pLine(2, "Books", "\n");
		this.pLine(3, "Home", "\n");
		this.pLine(4, "Clothing", "\n");
		this.pLine(5, "Sporting Goods", "\n");

		while((this.category = this.getInt(5))==-1);
		
		this.pLine(-1, "condition","\n");
		this.pLine(1, "New", "\n");
		this.pLine(2, "Like-New", "\n");
		this.pLine(3, "Used (Good)", "\n");
		this.pLine(4, "Used (Acceptable)", "\n");

		while((this.condition= this.getInt(4))==-1);

		this.description = this.pQuest("---- description: ");

		this.pLine(-1, "buy-it-now price:","");
		
		while((this.buyPrice = this.getInt(-1))==-1) this.pLine(-1, "buy-it-now price:","");

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
