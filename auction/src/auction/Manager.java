package auction;

import java.util.Locale.Category;
import java.sql.*;

public final class Manager {
	public static boolean root = false;
	public static int id = 0;
	
	public static void clean() 
	{
		 //System.out.print("\033[H\033[2J");  
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

		switch(search_memu.index)
		{
			case 1:
				search_category();
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

		return true;
	}

	public static void search_category()
	{
		Category_menu category_menu = new Category_menu();
		category_menu.exec();

		//clean();
		
		category_menu.pLine(0, "Search results: Category", "");

		String select = "SELECT description,bid_num,cur_price"
		+",highest_bidder,posted_date,ending_date from item " 
		+"inner join bid_info using(item_id) where category_id =" 
		+ Integer.toString(category_menu.index);		

		try {
			ResultSet rs = Driver.query(select);	

			int num = 1;

			while(rs.next())
			{
				String arr[] = new String[7];

				arr[0] = '[' + Integer.toString(num++)+']';
				arr[1] = "    description: ";
				arr[2] = "    status: ";
				arr[3] = "    current bidding price: ";
				arr[4] = "    current highest bidder: ";
				arr[5] = "    date posted: ";
				arr[6] = "    bid ending date: ";
				
				for(int i=1;i<7;++i) arr[i] += rs.getString(i);
				arr[2] += " bids";

				for(int i=0;i<7;++i) System.out.println(arr[i]);				
			}
		} catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}

		
		

		
	}
}
