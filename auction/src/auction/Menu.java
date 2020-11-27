package auction;

import java.util.Scanner;

public class Menu {
	Scanner sc = new Scanner(System.in);
	
	public void pLine(int num,String str,String str2) {
		String string;
		if(num < 0) string = String.format("---- %s : ", str);
		else if(num == 0) string = String.format("----< %s >", str);
		else string = String.format("----(%d) %s", num,str);			
		System.out.println(string+" "+str2);		
	}
	
	public String pQuest(String str) {		
		System.out.print(str + " ");
		return sc.nextLine();
	}

	public int getInt(int upper_bound)
	{
		int ret = -1;
		String tmp = this.pQuest(">");

		try {
			if(!tmp.equals(null) && !tmp.equals("")) ret = Integer.parseInt(tmp);	
		} catch (Exception e) {
			return -1;
		}
		

		if(upper_bound == -1 && ret !=-1) return ret;

		if(ret < 1 || ret > upper_bound) return -1;

		return ret;				
	}
}

