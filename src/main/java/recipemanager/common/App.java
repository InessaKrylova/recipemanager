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
    	RecipeDAO rDao = new RecipeDAO();
    	//rDao.create("Новый рецепт", "Неизвестная", "12.5", "Вторые блюда", 4, new AuthorDAO().getById(15), 380);
    	rDao.getById(6).addRate(new RateDAO().create(5, 11, 2.5));
    }
}
