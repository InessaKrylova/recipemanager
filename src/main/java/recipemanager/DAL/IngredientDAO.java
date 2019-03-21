package recipemanager.DAL;

import java.sql.*;
import java.util.*;

import recipemanager.Entities.Ingredient;

public class IngredientDAO {
   
	private static final String GET_INGREDIENT_BY_ID = "SELECT * FROM ingredient WHERE id=";
	private static final String EXCEPTION_IN_RESULTSET = "Exception while executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
	private static final String GET_ALL_INGREDIENTS = "SELECT * FROM ingredient";
	private static final String CREATE_INGREDIENT = "INSERT INTO ingredient(title, caloricity) VALUES(?, ?) RETURNING id;";
	private static final String REMOVE_INGREDIENT = "DELETE FROM ingredient WHERE id=";
	private static final String GET_BY_TITLE = "SELECT * FROM ingredient WHERE title=?;";
	
	public Ingredient getById(int id) {
    	Ingredient ingredient = null;
        try (Statement statement = DBConnector.getConnection().createStatement())  {
	        try (ResultSet resultSet = statement.executeQuery(GET_INGREDIENT_BY_ID+id)) {
	            while(resultSet.next()) {
	            	ingredient = new Ingredient(id, resultSet.getString("title"), resultSet.getInt("caloricity"));	            	
	            }
	        } catch (SQLException ex) {
	            System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	        System.out.println(EXCEPTION_IN_STATEMENT);
	    }
        System.out.println(ingredient==null
        		? "Ingredient with id"+id+" is not found"
        		: "Ingredient found: "+ ingredient.getId()+" "+ingredient.getTitle()+" "+ingredient.getCaloricity());
        return ingredient;
    }

    public List<Ingredient> getAllIngredients() {
    	List<Ingredient> list = new ArrayList<>();
    	try (Statement statement = DBConnector.getConnection().createStatement())  {
	        try (ResultSet resultSet = statement.executeQuery(GET_ALL_INGREDIENTS)) {        	
	            while(resultSet.next()) {   
	                list.add(new Ingredient(
	                		resultSet.getInt("id"), 
	                		resultSet.getString("title"), 
	                		resultSet.getInt("caloricity")
	                ));               
	            }
	        } catch (SQLException ex) {
	            System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	        System.out.println(EXCEPTION_IN_STATEMENT);
	    }
    	System.out.println("All ingredients:");
    	for (Ingredient ingredient : list) {
    		System.out.println(ingredient.getId()+" "+ingredient.getTitle()+" "+ingredient.getCaloricity());
    	}
        return list;
    }

    public Ingredient getByTitle(String title) {
        Ingredient ingredient = null;
        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(GET_BY_TITLE))  {
	        statement.setString(1, title);
        	try (ResultSet resultSet = statement.executeQuery()) {
	            while(resultSet.next()) {
	            	ingredient = new Ingredient(resultSet.getInt("id"), title, resultSet.getInt("caloricity"));	            	
	            }
	        } catch (SQLException ex) {
	            System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	        System.out.println(EXCEPTION_IN_STATEMENT);
	    }
        System.out.println(ingredient==null
        		? "Ingredient with title"+title+" is not found"
        		: "Ingredient found: "+ ingredient.getId()+" "+ingredient.getTitle()+" "+ingredient.getCaloricity());
        return ingredient;
    }
    
    public Ingredient create(String title, int caloricity) {
        Ingredient ingredient = null;
    	try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(CREATE_INGREDIENT)) {
	        statement.setString(1, title);
	        statement.setInt(2, caloricity);
    		try (ResultSet resultSet = statement.executeQuery()) {
	            while(resultSet.next()) {
	            	ingredient = new Ingredient(resultSet.getInt("id"), title, caloricity);
	            }
	        } catch (SQLException ex) {
                System.out.println(EXCEPTION_IN_RESULTSET);
            }
        } catch (SQLException ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
        }
    	System.out.println("Ingredient successfully created with id="+ingredient.getId());
        return ingredient;
    }     
    
    public void remove(int id){
    	try (Statement statement = DBConnector.getConnection().createStatement()) {
    		statement.execute(REMOVE_INGREDIENT+id);
	    	System.out.println("Ingredient with id="+id+" successfully removed");
        } catch (SQLException ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
        }
    }
}
