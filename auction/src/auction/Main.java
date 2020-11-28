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
		while(Manager.Home());
	}
}
