package auction;

import auction.Menu;

public class Main{
	public static void main(String[] args) throws Exception
	{
		Login_menu login = new Login_menu();
		login.show_Login_menu();
		login.exec();
		
		if(login.index == '2') System.out.println("success");
	}
}
