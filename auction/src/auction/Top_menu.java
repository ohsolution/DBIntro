package auction;

public class Top_menu extends Menu{
	
	public int index=0;
	
	public void show_top_menu()
	{
		Manager.clean();
		this.pLine(0, "Login menu","");
		this.pLine(1,"Login","");
		this.pLine(2, "Sign Up","");
		this.pLine(3, "Login as Administrator","");
		this.pLine(4, "Quit","");
	}
	
	public boolean exec() throws Exception
	{
		show_top_menu();
		String tmp = this.pQuest(">");
		if(!tmp.equals(null) && !tmp.equals("")) index = Integer.parseInt(tmp);		
		if(1 <= this.index && 4>=this.index) return true;
		return false;
	}
	
}
