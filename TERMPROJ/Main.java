package auction;

public class Main{
	public static void main(String[] args) throws Exception
	{
		Driver.connect();
		if(!Manager.top_level()) 
		{
			Driver.close();
			return;
		}
		if(!Manager.root) while(Manager.Home());
		else while(Manager.admin());
	}
}
