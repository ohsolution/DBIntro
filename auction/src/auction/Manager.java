package auction;

import java.util.ArrayList;
import java.util.Locale.Category;

import java.sql.*;

@SuppressWarnings("deprecation")
public final class Manager {
	public static boolean root = false;
	public static int id = 0;
	public static String name;

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
		if(idx == 0) 
		{
			id = login.id;
			name = login.fn;		
		}
		return ret;
	}

	public static boolean admin()
	{
		Admin_menu admin_menu = new Admin_menu();
		admin_menu.exec();
		boolean state = true;

		switch(admin_menu.index)
		{
			case 1:
				check_item(true);
				break;
			case 2:
				summary_cate();
				break;
			case 3:
				top_seller();
				break;
			case 4:
				top_item();
				break;
			case 5:
				summary_profit();
				break;
			case 6:
				state = false;
				break;
		}
		return state;
	}

	public static void summary_profit()
	{
		Search_engine se = new Search_engine(10,5);

		String select = "select seller_id,successful_bid_price,transaction_date from invoice order by seller_id";

		se.search(select, 1);

		int sum = 0;

		for(int i=1; i<13;++i)
		{
			System.out.println("Month "+Integer.toString(i) + ": "+ Integer.toString(se.month[i]));
			sum += se.month[i];
		} 

		System.out.println("Total profit : " + Integer.toString(sum));
	}

	public static void top_item()
	{
		Search_engine se = new Search_engine(9,5);

		String select = "select description,highest_bidder,bid_num,successful_bid_price"
		+" from item inner join bid_info using(item_id) inner join invoice"
		+" using(bid_info_id) order by successful_bid_price DESC limit 3";

		se.search(select, 1);		 
	}

	public static void top_seller()
	{
		Search_engine se = new Search_engine(8, 5);

		String select = "select sum(successful_bid_price) as S,count(*),email,first_name from invoice inner join user_info on "
		+"seller_id = user_id group by user_id order by S DESC limit 3";

		se.search(select, 1);		
	}

	public static void summary_cate()
	{	
		Search_engine se = new Search_engine(7, 3);
		
		String select = "select sum(successful_bid_price)"
		+",count(*),item_category from bid_info inner join invoice"
		+" using(bid_info_id) inner join item using(item_id) inner join category using(category_id) group by category_id";

		se.search(select, 3);
	}

	public static boolean Home()
	{
		Home_menu home = new Home_menu();
		home.exec();
		boolean state = true;

		switch(home.index)
		{
		case 1:
			sell_item();
			break;
		case 2:
			check_item(false);
			break;
		case 3:
			state = search_item();			
			break;
		case 4:
			check_bid();
			break;
		case 5:
			check_account();
			break;
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
		boolean loop = false;
		do
		{
			Search_menu search_memu = new Search_menu();
			search_memu.exec();
		
			switch(search_memu.index)
			{
				case 1:
					loop = search_category();
					break;
				case 2:
					loop = search_keyword();
					break;
				case 3:
					loop = search_seller();
					break;
				case 4:
					loop = search_date();
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
		
		category_menu.pLine(0, "Search results: Category", "\n");

		String select = "SELECT bid_info_id,description,bid_num,cur_price"
		+",highest_bidder,posted_date,ending_date,buy_now_price from item " 
		+"inner join bid_info using(item_id) where category_id =" 
		+ Integer.toString(category_menu.index)
		+ " AND user_id <> "
		+ Integer.toString(id)
		+ " AND ending_date > CURRENT_TIMESTAMP()";

		return ins_bid(new Search_engine(1,8),category_menu,select);
	}

	public static boolean search_keyword()
	{
		Menu menu = new Menu();
		menu.pLine(0, "Search item by description keyword", "\n");
		String pat = menu.pQuest("Search keyword : ");

		String select = "SELECT bid_info_id,description,bid_num,cur_price"
		+",highest_bidder,posted_date,ending_date,buy_now_price from item " 
		+"inner join bid_info using(item_id) where description like '%" 
		+ pat + "%'"
		+ " AND user_id <> "
		+ Integer.toString(id)
		+ " AND ending_date > CURRENT_TIMESTAMP()";

		return ins_bid(new Search_engine(1,8),menu,select);
	}

	public static boolean search_seller()
	{
		Menu menu = new Menu();
		menu.pLine(0, "Search items by seller name", "\n");
		String sn = menu.pQuest("Search seller name : ");

		String select = "SELECT bid_info_id,description,bid_num,cur_price"
		+",highest_bidder,posted_date,ending_date,buy_now_price from item " 
		+"inner join bid_info using(item_id) inner join user_info using(user_id) where first_name = '" 
		+ sn +"'"
		+ " AND user_id <> "
		+ Integer.toString(id)
		+ " AND ending_date > CURRENT_TIMESTAMP()";

		return ins_bid(new Search_engine(1,8),menu,select);
	}

	public static boolean search_date()
	{
		Menu menu = new Menu();
		menu.pLine(0, "Search items by date", "\n");
		String dn = menu.pQuest("Search by date(yyyy-mm-dd) : ");

		String select = "SELECT bid_info_id,description,bid_num,cur_price"
		+",highest_bidder,posted_date,ending_date,buy_now_price from item " 
		+"inner join bid_info using(item_id) where posted_date like '" 
		+ dn +"%'"
		+ " AND user_id <> "
		+ Integer.toString(id)
		+ " AND ending_date > CURRENT_TIMESTAMP()";
		
		return ins_bid(new Search_engine(1, 8),menu,select);
	}

	public static boolean ins_bid(Search_engine se,Menu menu,String select)
	{
		int idx = -1;
		String q = "--- Which item do you want to bid? (Enter the number or 'B'"
			+" to go back to the previous menu): ";
		
		while(idx == -1)
		{
			se.search(select, 1);			
			idx = menu.getTargetInt(q, "B", 1, se.info.size()/3);						
		}
		
		if(idx == 0) return true;

		q = "--- Bidding price? (Enter the price or 'buy' to pay for the buy-it-now price : ";

		int mincost = Math.max(se.info.get((idx-1)*3 + 1)+1,1);
		int buynowcost = se.info.get((idx-1)*3 + 2);

		int cost = -1;

		while(cost ==-1)
		{
			cost = menu.getTargetInt(q, "buy",mincost,buynowcost);			
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

	public static void check_item(boolean tp)
	{		
		if(!tp) System.out.println("----< Status of Your Item Listed on Auction >");
		else System.out.println("----< Status of all Item Listed on Auction >");

		Search_engine unsold_search = new Search_engine(1, 8);
		Search_engine sold_search = new Search_engine(2, 6);

		String unsold_select = "SELECT bid_info_id,description,bid_num,cur_price"
		+",highest_bidder,posted_date,ending_date,buy_now_price from item " 
		+"inner join bid_info using(item_id) where issell = ";

		String sold_select = unsold_select;
		unsold_select += "false";
		sold_select += "true";

		if(!tp)
		{
			unsold_select += " AND user_id = "+Integer.toString(id);
			sold_select += " AND user_id = "+Integer.toString(id);
		}
		
		unsold_search.search(unsold_select, 1);
		sold_search.search(sold_select, (unsold_search.info.size())/3+1);
	}

	public static void check_bid()
	{
		System.out.println("----< Check Status of your Bid >");

		Search_engine unsold_search = new Search_engine(3,6);
		Search_engine sold_search = new Search_engine(4,5);

		String unsold_select = "SELECT description,max(bid_price),cur_price,ending_date "
		+"FROM bid as B INNER JOIN "
		+"(SELECT * from item INNER JOIN bid_info USING(item_id)) as S "
		+"USING(bid_info_id)"
		+"WHERE B.user_id = " + Integer.toString(id)
		+" AND issell = ";

		String sold_select = unsold_select;

		unsold_select += "false GROUP BY bid_info_id";
		sold_select += "true GROUP BY bid_info_id";

		unsold_search.search(unsold_select, 1);
		sold_search.search(sold_select,unsold_search.info.size()+1);
		
	}

	public static void check_account()
	{
		System.out.println("----< Check your Account >");
		Search_engine sold_search = new Search_engine(5,3);
		Search_engine puchase_search = new Search_engine(6,3);

		String sold_select = "SELECT description,successful_bid_price,transaction_date "
		+"FROM invoice INNER JOIN bid_info USING(bid_info_id) "
		+"INNER JOIN item USING(item_id) WHERE transaction_date like '%-"
		+ Integer.toString((new Timestamp(System.currentTimeMillis())).getMonth()+1) +"-%' AND ";
		String puchase_select = sold_select;

		sold_select += "seller_id = ";
		puchase_select += "buyer_id = ";
		sold_select += Integer.toString(id);
		puchase_select += Integer.toString(id);

		sold_select += " order by transaction_date";
		
		sold_search.search(sold_select, 1);
		System.out.print("\n");

		puchase_search.search(puchase_select, 1);
		System.out.print("\n");
		
		int total = sold_search.sum + sold_search.fee - puchase_search.sum;

		System.out.println("[Your Balance Summary]");
		System.out.println("    sold: " + Integer.toString(sold_search.sum) + " won");
		System.out.println("    commission: "+ Integer.toString(sold_search.fee) + " won");
		System.out.println("    purchased: " + Integer.toString(puchase_search.sum * -1) + " won");
		System.out.println("    Total balance: " + Integer.toString(total) + " won");

	}

}
