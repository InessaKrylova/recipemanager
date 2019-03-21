package recipemanager.DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import recipemanager.Entities.Step;

public class StepDAO {
	
	private static final String EXCEPTION_IN_RESULTSET = "Exception while executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
	private static final String GET_STEP_BY_ID = "SELECT * FROM step where recipe_id=";
	private static final String CREATE_STEP = "INSERT INTO step(description, number, recipe_id) VALUES(?, ?, ?) RETURNING id;";
	private static final String REMOVE_STEP = "DELETE FROM step WHERE id=";
	private static final String GET_RECIPE_STEPS = "SELECT * FROM step where recipe_id=";
	
	public TreeMap<Integer,Step> getStepsMapByRecId(int recipeId) {
        TreeMap<Integer, Step> map = new TreeMap<Integer, Step>();
        try (Statement statement = DBConnector.getConnection().createStatement()){
        	try(ResultSet resultSet = statement.executeQuery(GET_STEP_BY_ID+recipeId)) {       
	            while(resultSet.next()) {
	                Step step = new Step(
	                	resultSet.getInt("id"),
	                	resultSet.getString("description"),
	                	resultSet.getInt("number"),
	                	recipeId
	                );
	                map.put(step.getNumber(), step);
	            }  
        	} catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    } 
        System.out.println(map.size()+" steps found");
        return map;
    }
    
    public List<Step> getStepsListByRecId(int recipeId) {
        List<Step> list = new ArrayList<>();
        try (Statement statement = DBConnector.getConnection().createStatement()){
        	try(ResultSet resultSet = statement.executeQuery(GET_RECIPE_STEPS+recipeId)) {       
	            while(resultSet.next()) {
	                Step step = new Step(
	                	resultSet.getInt("id"),
	                	resultSet.getString("description"),
	                	resultSet.getInt("number"),
	                	recipeId
	                );
	               list.add(step);
	            }  
        	} catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    } 
        System.out.println(list.size()+" steps found");
        return list;
    }     
    
    protected Step create(int recipeId, int number, String description) {        
    	Step step = null;
    	try(PreparedStatement statement = DBConnector.getConnection().prepareStatement(CREATE_STEP)){
    		statement.setString(1, description);
    		statement.setInt(2, number);
    		statement.setInt(3, recipeId);
            try(ResultSet rs = statement.executeQuery()) {
	            while(rs.next()) {
	            	step = new Step(rs.getInt("id"), description, number, recipeId);
	            }
            } catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    } 
        return step;
    }
    
    public void remove(int id){
    	try (Statement statement = DBConnector.getConnection().createStatement()) {
	    	statement.execute(REMOVE_STEP+id);
	    	System.out.println("Step with id="+id+" successfully removed");
        } catch (SQLException ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
        }
    }       
}
