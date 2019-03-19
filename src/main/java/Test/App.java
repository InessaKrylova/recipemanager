package Test;

import java.util.Arrays;

import DAL.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        DBConnector.connect();
        
        RecipeDAO rDao = new RecipeDAO();
        rDao.remove(1);
        
        DBConnector.exit();
    }
}
