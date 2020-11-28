package auction;

public class Search_menu extends Menu
{
	int index = 0;

	public void show_search_menu()
	{
		Manager.clean();
		
		String choice = index>0 ? Integer.toString(index) : "";
        		
		this.pLine(0, "Search item", " : "+ choice);
		this.pLine(1, "Search items by category", "");
		this.pLine(2, "Search items by description keyword", "");
		this.pLine(3, "Search items by seller", "");
		this.pLine(4, "Search items by date posted", "");
		this.pLine(5, "Go Back", "");
		this.pLine(6, "Quit", "");				
	}
	
	public void exec()
	{
		show_search_menu();
		while((this.index = this.getInt(6))==-1) show_search_menu();
	}	
}
