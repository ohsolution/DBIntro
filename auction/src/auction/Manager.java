package auction;

public final class Manager {
	
	public static void clean() 
	{
		 System.out.print("\033[H\033[2J");  
		 System.out.flush();  
	}
	
	public static void top_level() throws Exception
	{
		Top_menu top = new Top_menu();
		top.show_top_menu();
		while(top.exec()==false);
		
		switch(top.index)
		{
		case 1: 
			while(Login()==false);
			break;
		case 2:
			Sign_up();
			Login();
			break;
		case 3:
			
			break;
		case 4:
			break;
			
		}
	}
	
	public static void Sign_up() throws Exception
	{
		Sign_up_menu sign = new Sign_up_menu();
		sign.exec();
		
	}
	
	public static boolean Login()
	{
		Login_menu login = new Login_menu();
		return login.exec();
	}
}
