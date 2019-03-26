package recipemanager.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import recipemanager.Entities.Author;
import recipemanager.Entities.Rate;
import recipemanager.Entities.Recipe;
import recipemanager.Entities.Step;

public class RecipeDAO {
	
	private static final String EXCEPTION_IN_RESULTSET = "Exception while executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
	private static final String CREATE_RECIPE = "INSERT INTO recipe(title, kitchen, cook_time, section, serving_count, author, caloricity) "
    + "VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING id;";
	private static final String GET_RECIPE_BY_ID = "SELECT * FROM recipe WHERE id=";
	private static final String REMOVE_RECIPE = "DELETE FROM recipe WHERE id=";
	
    public Recipe create(String title, String kitchen, String cookTime, String section, int servingCount, Author author, int caloricity) {
    	Recipe recipe =  null;   
    	try (Connection connection = DBConnector.openConnection();
    		 PreparedStatement statement = connection.prepareStatement(CREATE_RECIPE)) {
    		statement.setString(1, title);
    		statement.setString(2, kitchen);
    		statement.setString(3, cookTime);
    		statement.setString(4, section);
    		statement.setInt(5, servingCount);
    		statement.setInt(6, author.getId());
    		statement.setInt(7, caloricity);
    		try (ResultSet rs = statement.executeQuery()) {
	            while(rs.next()) {
	            	 recipe = new Recipe(rs.getInt("id"), title, kitchen, cookTime, section, author, caloricity, servingCount);
	            }
	    	} catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (Exception ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    }
		System.out.println(recipe == null
			? "Recipe is not created"
			: recipe.toString());
    	return recipe;
    }
	
	public void remove(int id){
    	try (Connection connection = DBConnector.openConnection();
    		 Statement statement = connection.createStatement()) {
	    	statement.execute(REMOVE_RECIPE+id);
	    	System.out.println("Recipe with id="+id+" successfully removed");
        } catch (Exception ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
        }
    }               
    
    public Recipe getById(int id) {
        Recipe recipe = null;
        try(Connection connection = DBConnector.openConnection();
        	Statement statement = connection.createStatement();
        	ResultSet resultSet = statement.executeQuery(GET_RECIPE_BY_ID+id)){
            while (resultSet.next()){                    
            	recipe = new Recipe(
            		id, 
            		new ArrayList<Step>(),//new StepDAO().getStepsListByRecId(id),
            		new ArrayList<Rate>(),//new RateDAO().getRatesByRecId(id),
                    resultSet.getString("title"),
                    resultSet.getString("kitchen"),
                    resultSet.getString("cook_time"),
                    resultSet.getString("section"),
                    new AuthorDAO().getById(resultSet.getInt("author")),
            		resultSet.getInt("caloricity"),
                    resultSet.getInt("serving_count")            
            	); 
            }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULTSET);
        }

		System.out.println(recipe == null
			? "Recipe is not found"
			: recipe.toString());
        return recipe;
    }
    
    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        try(Connection connection = DBConnector.openConnection();
        	Statement statement = connection.createStatement();
        	ResultSet resultSet = statement.executeQuery("SELECT * FROM recipe")){
            while (resultSet.next()){                    
            	list.add(new Recipe(
            		resultSet.getInt("id"), 
            		new ArrayList<Step>(),//new StepDAO().getStepsListByRecId(id),
            		new ArrayList<Rate>(),//new RateDAO().getRatesByRecId(id),
                    resultSet.getString("title"),
                    resultSet.getString("kitchen"),
                    resultSet.getString("cook_time"),
                    resultSet.getString("section"),
                    new AuthorDAO().getById(resultSet.getInt("author")),
            		resultSet.getInt("caloricity"),
                    resultSet.getInt("serving_count")            
            	)); 	            	
            }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULTSET);
        }
        System.out.println(list.size()+" recipes found");
        return list;
    }

}
