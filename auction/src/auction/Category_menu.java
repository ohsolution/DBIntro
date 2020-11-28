package auction;

public class Category_menu extends Menu
{
	int index = 0;

	public void show_category_menu()
	{
		Manager.clean();
		
		String choice = index>0 ? Integer.toString(index) : "";
        		
		this.pLine(0, "Search item by category", " : "+ choice);
        this.pLine(1, "Electronics", "");
		this.pLine(2, "Books", "");
		this.pLine(3, "Home", "");
		this.pLine(4, "Clothing", "");
		this.pLine(5, "Sporting Goods", "");						
	}
	
	public void exec()
	{
		show_category_menu();
		while((this.index = this.getInt(5))==-1) show_category_menu();
	}	
}
