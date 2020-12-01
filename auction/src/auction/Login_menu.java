package auction;

import java.sql.*;

public class Login_menu extends Menu 
{
	public String email = "";
	public String password = "";
	public int id = 0;
	public String fn;
	
	public boolean exec(int idx)
	{
		if(idx==0) this.pLine(0, "Login","\n");
		else this.pLine(0, "Login as Administrator","\n");
		email = this.pQuest("---- email: ");
		password = this.pQuest("---- password: ");		

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
			String qu = "SELECT * FROM ";
			if(idx == 0) qu += "user_info";
			else qu += "admin";

			ResultSet ret = Driver.query(qu);

			while(ret.next())				
			{				
				String dbid = ret.getString("email");
				String dbpw = ret.getString("password");

				if((id.equals(dbid)) && (pw.equals(dbpw)))
				{
					if(idx == 0)
					{
						this.id = ret.getInt("user_id");
						this.fn = ret.getString("first_name");
					}
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
