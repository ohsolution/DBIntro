package auction;

import java.sql.*;

public class Login_menu extends Menu 
{
	public String email = "";
	public String password = "";
	
	public void show_login_menu()
	{
		Manager.clean();
		this.pLine(0, "Login","");
		this.pLine(-1,"email",email);
		this.pLine(-1, "password",encode(password));
		
	}
	
	public boolean exec()
	{
		show_login_menu();
		email = this.pQuest("Enter the email : ");
		show_login_menu();
		password = this.pQuest("Enter the password : ");
		show_login_menu();
		return isvaild(email,password);	
		
	}
	
	public String encode(String pass)
	{
		String ret = "";
		for(int i=0 ; i<pass.length() ; ++i) ret += "*";
		return ret;
	}
	
	public boolean isvaild(String id,String pw)
	{
		try {
			String qu = "SELECT email,password FROM user_info";
			ResultSet ret = Driver.query(qu);
			while(ret.next())
			{
				String dbid = ret.getString("email");
				String dbpw = ret.getString("password");
				if((id.equals(dbid)) && (pw.equals(dbpw))) return true;				
			}
			ret.close();			
		}
		catch (SQLException sql)
		{
			System.out.println(sql);
		}
		return false;
	}
}
