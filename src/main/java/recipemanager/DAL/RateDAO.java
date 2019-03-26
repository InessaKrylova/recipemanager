package recipemanager.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import recipemanager.Entities.Ingredient;
import recipemanager.Entities.Rate;

public class RateDAO {
	private static final String EXCEPTION_IN_RESULTSET = "Exception while executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
	private static final String GET_RATES_BY_RECIPE = "SELECT * FROM rate WHERE recipe_id=";
	private static final String GET_RATE_BY_ID = "SELECT * FROM rate where id=";
	private static final String GET_ALL_RATES = "SELECT * FROM rate";
	private static final String CREATE_RATE = "INSERT INTO rate(ingredient, recipe_id, count) VALUES(?, ?, ?) RETURNING id;";
	private static final String REMOVE_RATE = "DELETE FROM rate WHERE id=";
	
	public List<Rate> getRatesByRecId(int recipeId) {
        List<Rate> list = new ArrayList<>();
        try (Connection connection = DBConnector.openConnection();
    		 Statement statement = connection.createStatement();
    		 ResultSet resultSet = statement.executeQuery(GET_RATES_BY_RECIPE+recipeId)) {
            while(resultSet.next()) {
            	Ingredient ingredient = new IngredientDAO().getById(resultSet.getInt("ingredient"));
                list.add(new Rate(
                		resultSet.getInt("id"),
                		recipeId, 
                		ingredient, 
                		resultSet.getDouble("count")
                ));
            }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULTSET);
        }
        
        for (Rate rate : list) {
			System.out.println(rate.toString());
		}
        return list;
    }

    public Rate getById(int id) {
    	Rate rate = null;
    	try (Connection connection = DBConnector.openConnection();
    		 Statement statement = connection.createStatement();
    		 ResultSet resultSet = statement.executeQuery(GET_RATE_BY_ID+id)){
            while(resultSet.next()) {  
            	Ingredient ingredient = new IngredientDAO().getById(resultSet.getInt("ingredient"));           	
                rate = new Rate( 
            		id,
            		resultSet.getInt("recipe_id"),
            		ingredient, 
            		resultSet.getDouble("count")
                );
            }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULTSET);
        }
		System.out.println(rate == null
			? "Ingredient is not found"
			: rate.toString());
        return rate;
    }
    
    public List<Rate> getAllRates() {
        List<Rate> list = new ArrayList<>();
        try(Connection connection = DBConnector.openConnection();
        	Statement statement = connection.createStatement();
        	ResultSet resultSet = statement.executeQuery(GET_ALL_RATES)) {
            while(resultSet.next()) {
            	Ingredient ingredient = new IngredientDAO().getById(resultSet.getInt("ingredient"));
                list.add(new Rate(
            		resultSet.getInt("id"),
            		resultSet.getInt("recipe_id"), 
            		ingredient, 
            		resultSet.getDouble("count")
                ));
            }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULTSET);
        }      
        for (Rate rate : list) {
			System.out.println(rate.toString());
		}
        return list;
    }
    
    public Rate create(int recipeId, int ingredientId, double count) {
        Rate rate = null;
    	try (Connection connection = DBConnector.openConnection();
    		PreparedStatement statement = connection.prepareStatement(CREATE_RATE)) {
	        statement.setInt(1, ingredientId);
	        statement.setInt(2, recipeId);
	        statement.setDouble(3, count);
    		try (ResultSet resultSet = statement.executeQuery()){
	            while(resultSet.next()) {
	            	rate = new Rate(
	            			resultSet.getInt("id"),
	            			recipeId,
	            		new IngredientDAO().getById(ingredientId),
	            		count
	            	);
	            }
	        } catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (Exception ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    }
		System.out.println(rate == null
				? "Rate is not created"
				: rate.toString());
        return rate;
    }    
    
	public void remove(int id){
    	try (Connection connection = DBConnector.openConnection();
    		 Statement statement = connection.createStatement()) {
	    	statement.execute(REMOVE_RATE+id);
	    	System.out.println("Rate with id="+id+" successfully removed");
        } catch (Exception ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
        }
    }  
}
