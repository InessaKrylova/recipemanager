package DAL;

import java.sql.*;

public class DBConnector {
    private static Connection con = null;
   
    public static void connect(){
        try { 
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/recipeDB","postgres", "123456");
            System.out.println("Enabled connection with recipeDB.\n");
        }  catch (Exception e) {
            System.out.println("Error of connection! "+ e.getMessage());
        }
    }
    
    public static void exit() {
        try { 
        	con.close(); 
        	System.out.println("\nDisabled connection with recipeDB.");
        } catch (Exception e) {
        	System.out.println("Error of connection! "+e.getMessage());
        }
    }
    
    public static ResultSet statementWithRS(String query) {
        ResultSet rs = null;
        try {
            Statement st = con.createStatement();           
            rs = st.executeQuery(query);
        }
        catch (Exception e) {
            System.out.println("Запрос не выполнен!");          
        }
        return rs;
    }
    
    public static boolean statement(String query) {
        boolean result = false;
        try {
            Statement st = con.createStatement();
            result = st.execute(query);
        }
        catch (Exception e) {
            System.out.println("Запрос не выполнен!");          
        }
        return result;
    }
    
    public static Connection getConnection() {
    	return con;
    }
}
