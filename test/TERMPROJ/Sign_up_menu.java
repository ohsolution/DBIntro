package auction;

public class Sign_up_menu extends Menu{
	
	public String first_name ="";
	public String last_name = "";
	public String email = "";
	public String password = "";
	
	public boolean exec() throws Exception
	{
		this.pLine(0, "Sign up","\n");
		first_name = this.pQuest("---- first name : ");
		last_name = this.pQuest("---- last name : ");
		email = this.pQuest("---- email : ");
		password = this.pQuest("---- password : ");
		
		String values = '\'' + password + '\'' + ','
						+'\'' + first_name + '\'' +','
						+'\'' + last_name + '\'' +','
						+'\'' + email + '\'';
					
		
		if(Driver.insert("user_info","password,first_name,last_name,email",values,"")==-1)
		{
			System.out.println("email or password is already exist");
			return false;		
		}
		
		return true;
	}
	
}