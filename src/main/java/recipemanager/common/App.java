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
        IngredientDAO iDao = new IngredientDAO();
        iDao.remove(14);
    }
}
