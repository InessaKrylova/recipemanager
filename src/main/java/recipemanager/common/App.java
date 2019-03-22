package recipemanager.common;

import recipemanager.DAL.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RateDAO rDao = new RateDAO();
        rDao.remove(9);
    }
}
