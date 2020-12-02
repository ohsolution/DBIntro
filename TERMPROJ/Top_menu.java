package auction;

public class Top_menu extends Menu{
	
	public int index=0;
	
	public void exec()
	{
		this.pLine(0, "Login menu","\n");
		this.pLine(1,"Login","\n");
		this.pLine(2, "Sign Up","\n");
		this.pLine(3, "Login as Administrator","\n");
		this.pLine(4, "Quit","\n");
		while((this.index = this.getInt(4))==-1);	
	}
	
}
