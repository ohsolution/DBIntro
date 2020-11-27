
package auction;

import java.sql.*;

public class Sell_item_menu extends Menu 
{
    public int category;
    public int condition;
    public String descrpition;

	public void show_sell_item_menu(int idx)
	{
		Manager.clean();
		if(idx==0) this.pLine(0, "Login","");
		else this.pLine(0, "Login as Administrator","");
		this.pLine(-1,"email",email);
		this.pLine(-1, "password",encode(password));
		
	}
	
	public boolean exec(int idx)
	{
		show_login_menu(idx);
		email = this.pQuest("Enter the email : ");
		show_login_menu(idx);
		password = this.pQuest("Enter the password : ");
		show_login_menu(idx);

		return isvaild(email,password,idx);	
		
	}
}
