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
        rDao.create("Шарлотка яблочная", "Русская", "40 мин", "Десерт", 8, new AuthorDAO().getById(16), 410);
        
        DBConnector.exit();
    }
}
