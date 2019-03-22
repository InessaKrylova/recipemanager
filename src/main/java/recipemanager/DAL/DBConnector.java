package recipemanager.DAL;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnector {

    private static Connection con = null;

    public static void closeConnection() throws Exception {
        con.close();
    }
    
    public static Connection openConnection() throws Exception {
        Properties property = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        property.load(fis);

        String url = property.getProperty("db.url");
        String login = property.getProperty("db.login");
        String password = property.getProperty("db.password");

        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection(url, login, password);
        return con;
    }   
    
    public static void connect(){
        try { 
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/recipeDB","postgres", "123456");
            System.out.println("Enabled connection with recipeDB.\n");
        }  catch (Exception e) {
            System.out.println("Error of connection! "+ e.getMessage());
        }
    }
    
     
    public static Connection getConnection() {
    	return con;
    }
    
    public static void exit() {
        try { 
        	con.close(); 
        	System.out.println("\nDisabled connection with recipeDB.");
        } catch (Exception e) {
        	System.out.println("Error of connection! "+e.getMessage());
        }
    }
}
