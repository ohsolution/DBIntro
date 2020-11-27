package auction;

import java.sql.*;

public class Login_menu extends Menu 
{
	public String email = "";
	public String password = "";
	public int id = 0;
	
	public void show_login_menu(int idx)
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
	
	public String encode(String pass)
	{
		String ret = "";
		for(int i=0 ; i<pass.length() ; ++i) ret += "*";
		return ret;
	}
	
	public boolean isvaild(String id,String pw,int idx)
	{
		try {
			String qu = "SELECT email,password FROM ";
			if(idx == 0) qu += "user_info";
			else qu += "admin";

			ResultSet ret = Driver.query(qu);
			while(ret.next())
			{
				String dbid = ret.getString("email");
				String dbpw = ret.getString("password");
				if((id.equals(dbid)) && (pw.equals(dbpw)))
				{
					this.id = ret.getInt("user_id");
					return true;
				}
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
