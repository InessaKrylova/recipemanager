package recipemanager.common;

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
        DBConnector.connect();
        RateDAO rDao = new RateDAO();
        rDao.create(2, 11, 4.0);
        DBConnector.exit();
    }
}
