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
}
