package auction;

public class Menu {
	
	public void pLine(int num,String str) {
		String string;
		if(num == 0) string = String.format("----< %s >", str);
		else string = String.format("----(%d) %s", num,str);			
		System.out.println(string);
	}
	
}

