package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;


public class SQL {
	
	public static void main(String[] args)  {
        try {
            
        	//
        	Class.forName("com.mysql.jdbc.Driver"); //dynamic loading of driver
        	System.out.println("dsaD");
            String database = "jdbc:mysql://ip:port/datbase";
            Connection con = DriverManager.getConnection( database ,"user","password"); 
            //
            Statement s = con.createStatement();
            s.execute("create table TEST12345 ( firstcolumn integer )"); 
            s.execute("insert into TEST12345 values(1)");  
//            s.execute("select * from TEST12345 where id>100");
//            ResultSet rs = s.getResultSet(); 
//            if (rs != null) // if rs == null, then there is no ResultSet to view
//            while ( rs.next() ) // this will step through our data row-by-row
//            {   /* the next line will get the first column in our current row's ResultSet 
//                as a String ( getString( columnNumber) ) and output it to the screen */ 
//                System.out.println("Data from column_name: " + rs.get("firstcolumn") );
//            }
            s.close(); // close Statement to let the database know we're done with it
            con.close(); //close connection
         }
        catch (Exception err) { System.out.println("ERROR: " + err);  }
      }


}