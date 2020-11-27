package auction;

import java.sql.*;
import java.util.Properties;

/*
 * Java Program to to connect to MySQL database and
 * fix java.sql.SQLException: No suitable driver found for jdbc:mysql://localhost:3306
 * error which occur if JAR is missing or you fail to register driver.
 */

public final class Driver 
{
	static Connection dbco;
	static Statement stmt; 
	static ResultSet ret;
  public static void connect() {
	  
    try {
      String url = "jdbc:mysql://localhost:3306/auction_18314788?characterEncoding=UTF-8&serverTimezone=UTC";
      Properties info = new Properties();
      
      info.put("user", "root");
      info.put("password", "ohyg635607%");

      dbco = DriverManager.getConnection(url, info);


      if (dbco != null) {  
          stmt = dbco.createStatement();
      }

    } catch (SQLException ex) {
      System.out.println("An error occurred while connecting MySQL databse");
      ex.printStackTrace();
    }
  }
  
  public static boolean insert(String table,String attr, String values)
  {
	  try 
	  {
		  String ins_query = "INSERT INTO " + table + " ("+attr+") "+"values ("+values+")";
		  stmt.executeUpdate(ins_query);
	  }
	  catch (Exception exc)
	  {
		  return false;
	  }
	  return true;
  }
  
  public static ResultSet query(String str)
  {
	  try {
	  ret = stmt.executeQuery(str);
	  }
	  catch (SQLException sql)
	  {
		  System.out.println(sql);
	  }
	  
	  return ret;
  
  }

  public static void close()
  {
      ;
  }
}