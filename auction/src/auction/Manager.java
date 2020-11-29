package auction;

import java.util.ArrayList;
import java.util.Locale.Category;
import java.sql.*;

public final class Manager {
	public static boolean root = false;
	public static int id = 0;
	public static String name;
	
	public static void clean() 
	{
		 System.out.print("\033[H\033[2J");  
		 System.out.flush();  
	}
	
	public static boolean top_level() throws Exception
	{
		Top_menu top = new Top_menu();
		top.exec();

		switch(top.index)
		{
		case 1: 
		case 3:
			while(Login(top.index-1)==false);
			root = (top.index == 3);
			break;
		case 2:
			while(!Sign_up());
			while(Login(0)==false);
			break;
		case 4:
			return false;	
		}

		return true;
	}
	
	public static boolean Sign_up() throws Exception
	{
		Sign_up_menu sign = new Sign_up_menu();
		return sign.exec();
		
	}
	
	public static boolean Login(int idx)
	{
		Login_menu login = new Login_menu();
		boolean ret = login.exec(idx);
		id = login.id;
		name = login.fn;		
		return ret;
	}

	public static boolean Home()
	{
		Home_menu home = new Home_menu(root);
		home.exec();
		boolean state = true;

		switch(home.index)
		{
		case 1:
			sell_item();
			break;
		case 2:		
		case 3:
			state = search_item();			
			break;
		case 4:
		case 5:
		case 6:
			state = false;
			break;
		}

		return state;
	}

	public static void sell_item()
	{
		Sell_item_menu sell_menu = new Sell_item_menu();
		sell_menu.exec(id);
	}

	public static boolean search_item()
	{
		Search_menu search_memu = new Search_menu();
		search_memu.exec();

		boolean loop = false;

		do
		{
			switch(search_memu.index)
			{
				case 1:
					loop = search_category();
					break;
				case 2:
					//search_keyword();
					break;
				case 3:
					//search_seller();
					break;
				case 4:
					//search_date();
					break;
				case 5:
					return true;
				case 6:
					return false;
			}
		}while(loop);

		return true;
	}

	public static boolean search_category()
	{
		Category_menu category_menu = new Category_menu();
		category_menu.exec();

		clean();
		
		category_menu.pLine(0, "Search results: Category", "");

		String select = "SELECT bid_info_id,description,bid_num,cur_price"
		+",highest_bidder,posted_date,ending_date,buy_now_price from item " 
		+"inner join bid_info using(item_id) where category_id =" 
		+ Integer.toString(category_menu.index)
		+ " AND user_id <> "
		+ Integer.toString(id)
		+ " AND ending_date > CURRENT_TIMESTAMP()";

		Search_engine se = new Search_engine(1,8);
		
		int idx = -1;

		String q = "--- Which item do you want to bid? (Enter the number or 'B'"
			+" to go back to the previous menu):";
		
		while(idx == -1)
		{
			se.search(select, 1);			
			idx = category_menu.getTargetInt(q, "B", 1, se.info.size()/3);						
		}
		
		if(idx == 0) return true;

		q = "--- Bidding price? (Enter the price or 'buy' to pay for the buy-it-now price :";

		int mincost = Math.max(se.info.get((idx-1)*3 + 1),1);
		int buynowcost = se.info.get((idx-1)*3 + 2);

		int cost = -1;

		while(cost ==-1)
		{
			cost = category_menu.getTargetInt(q, "buy",mincost,buynowcost);			
		}

		if(cost == 0) cost = buynowcost;

		String attr = "user_id,bid_info_id,bid_price,bidder_name";
		String values = "'" + Integer.toString(id) + "', "
						+ "'" + Integer.toString(se.info.get((idx-1)*3)) + "', "
						+ "'" + Integer.toString(cost) + "', "
						+ "'" + name +"'";

		Driver.insert("bid",attr,values,"");

		return false;
	}
}
