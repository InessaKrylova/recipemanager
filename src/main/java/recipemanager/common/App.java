package recipemanager.common;

import recipemanager.DAL.AuthorDAO;
import recipemanager.DAL.DBConnector;
import recipemanager.DAL.RateDAO;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AuthorDAO aDao = new AuthorDAO();
        aDao.create("Test");
    }
}
