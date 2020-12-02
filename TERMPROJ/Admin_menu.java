package auction;

public class Admin_menu extends Menu
{
    public int index = 0;

    public void exec()
    {        
        this.pLine(0, "Main menu(Admin)","\n");
        this.pLine(1, "List all item", "\n");
		this.pLine(2,"Summary by Category ","\n");
		this.pLine(3,"Top3 seller of the month","\n");
        this.pLine(4,"Top3 item of the month","\n");
        this.pLine(5,"Profit of this Year","\n");
		this.pLine(6,"Quit","\n");
		while((this.index = this.getInt(6))==-1);		
    }
    
}
