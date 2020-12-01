package auction;

public class Search_menu extends Menu
{
	int index = 0;

	public void exec()
	{
		this.pLine(0, "Search item", " : \n");
		this.pLine(1, "Search items by category", "\n");
		this.pLine(2, "Search items by description keyword", "\n");
		this.pLine(3, "Search items by seller", "\n");
		this.pLine(4, "Search items by date posted", "\n");
		this.pLine(5, "Go Back", "\n");
		this.pLine(6, "Quit", "\n");
		while((this.index = this.getInt(6))==-1);
	}	
}
