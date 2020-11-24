package auction;

public class Sign_up_menu extends Menu{
	
	public String first_name ="";
	public String last_name = "";
	public String email = "";
	public String password = "";
	
	public void show_sign_menu() 
	{
		Manager.clean();
		this.pLine(0, "Sign up","");
		this.pLine(-1,"first name" , first_name);
		this.pLine(-1, "last name" , last_name);
		this.pLine(-1, "email" , email);
		this.pLine(-1, "password" , password);
	}
	
	public void exec() throws Exception
	{
		show_sign_menu();
		first_name = this.pQuest("Enter the first name : ");
		show_sign_menu();
		last_name = this.pQuest("Enter the last name : ");
		show_sign_menu();
		email = this.pQuest("Enter the email : ");
		show_sign_menu();
		password = this.pQuest("Enter the password : ");
		show_sign_menu();
		
		String values = '\'' + password + '\'' + ','
						+'\'' + first_name + '\'' +','
						+'\'' + last_name + '\'' +','
						+'\'' + email + '\'';
					
		
		if(Driver.insert("user_info","password,first_name,last_name,email",values)==false)
		{
			System.out.println("email or password is already exist");		
		}
		
		return;
	}
	
}