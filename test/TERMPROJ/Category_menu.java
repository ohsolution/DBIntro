package auction;

public class Category_menu extends Menu
{
	int index = 0;

	public void exec()
	{
		this.pLine(0, "Search item by category", " : \n");
        this.pLine(1, "Electronics", "\n");
		this.pLine(2, "Books", "\n");
		this.pLine(3, "Home", "\n");
		this.pLine(4, "Clothing", "\n");
		this.pLine(5, "Sporting Goods", "\n");
		while((this.index = this.getInt(5))==-1);
	}	
}
