package auction;

public class Login_menu extends Menu{
	
	public int index=0;
	
	public void show_Login_menu() {
		this.pLine(0, "Login menu");
		this.pLine(1,"Login");
		this.pLine(2, "Sign Up");
		this.pLine(3, "Login as Administrator");
		this.pLine(4, "Quit");
	}
	
	public void exec() throws Exception
	{
		index = System.in.read();
	}

}
