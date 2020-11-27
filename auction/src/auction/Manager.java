package auction;

public final class Manager {
	public static boolean root = false;
	public static int id = 0;
	
	public static void clean() 
	{
		 System.out.print("\033[H\033[2J");  
		 System.out.flush();  
	}
	
	public static boolean top_level() throws Exception
	{
		Top_menu top = new Top_menu();
		while(!top.exec());

		switch(top.index)
		{
		case 1: 
		case 3:
			while(Login(top.index-1)==false);
			root = (top.index == 3);
			break;
		case 2:
			Sign_up();
			Login(0);
			break;
		case 4:
			return false;	
		}

		return true;
	}
	
	public static void Sign_up() throws Exception
	{
		Sign_up_menu sign = new Sign_up_menu();
		sign.exec();
		
	}
	
	public static boolean Login(int idx)
	{
		Login_menu login = new Login_menu();
		boolean ret = login.exec(idx);
		id = login.id;		
		return ret;
	}

	public static void Home()
	{
		Home_menu home = new Home_menu(root);
		while(!home.exec());
		switch(home.index)
		{
		case 1:
			sell_item();
		case 2:		
		case 3:			
		case 4:
		case 5:
		case 6:
			;	
		}

		return;
	}

	public static void sell_item()
	{
		Sell_item_menu sell_menu = new Sell_item_menu();
	}
}
