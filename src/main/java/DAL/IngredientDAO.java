package DAL;

import java.sql.*;
import java.util.ArrayList;

import Entities.Ingredient;

public class IngredientDAO {
    public Ingredient getById(int id) {
    	Ingredient ingr = null;
        try (Statement st = DBConnector.getConnection().createStatement())  {
	        try (ResultSet rs = st.executeQuery("SELECT * FROM ingredient WHERE id="+id)) {
	            while(rs.next()) {
	            	ingr = new Ingredient(id, rs.getString("title"), rs.getInt("caloricity"));	            	
	            }
	        } catch (SQLException ex) {
	            System.out.println("Exception while executing query / getting result set");
	        }
	    } catch (SQLException ex) {
	        System.out.println("Exception while creating statement");
	    }
        System.out.println(ingr==null
        		? "Ingredient with id"+id+" is not found"
        		: "Ingredient found: "+ ingr.getId()+" "+ingr.getTitle()+" "+ingr.getCaloricity());
        return ingr;
    }

    public ArrayList<Ingredient> getAllIngredients() {
    	ArrayList<Ingredient> list = new ArrayList<Ingredient>();
    	try (Statement st = DBConnector.getConnection().createStatement())  {
	        try (ResultSet rs = st.executeQuery("SELECT * FROM ingredient")) {        	
	            while(rs.next()) {   
	                list.add(new Ingredient(
	                		rs.getInt("id"), 
	                		rs.getString("title"), 
	                		rs.getInt("caloricity")
	                ));               
	            }
	        } catch (SQLException ex) {
	            System.out.println("Exception while executing query / getting result set");
	        }
	    } catch (SQLException ex) {
	        System.out.println("Exception while creating statement");
	    }
    	System.out.println("All ingredients:");
    	for (Ingredient ingr : list) {
    		System.out.println(ingr.getId()+" "+ingr.getTitle()+" "+ingr.getCaloricity());
    	}
        return list;
    }

    public Ingredient getByTitle(String title) {
        Ingredient ingr = null;
        try (Statement st = DBConnector.getConnection().createStatement())  {
	        try (ResultSet rs = st.executeQuery("SELECT * FROM ingredient WHERE title=\'"+title+"\'")) {
	            while(rs.next()) {
	            	ingr = new Ingredient(rs.getInt("id"), title, rs.getInt("caloricity"));	            	
	            }
	        } catch (SQLException ex) {
	            System.out.println("Exception while executing query / getting result set");
	        }
	    } catch (SQLException ex) {
	        System.out.println("Exception while creating statement");
	    }
        System.out.println(ingr==null
        		? "Ingredient with title"+title+" is not found"
        		: "Ingredient found: "+ ingr.getId()+" "+ingr.getTitle()+" "+ingr.getCaloricity());
        return ingr;
    }
    
    public Ingredient create(String title, int calor) {
        Ingredient ingr = null;
    	try (PreparedStatement pst = DBConnector.getConnection().prepareStatement(
    	"INSERT INTO ingredient(title, caloricity) VALUES(?, ?) RETURNING id;")) {
	        pst.setString(1, title);
	        pst.setInt(2, calor);
    		try (ResultSet rs = pst.executeQuery()) {
	            while(rs.next()) {
	            	ingr = new Ingredient(rs.getInt("id"), title, calor);
	            }
	        } catch (SQLException ex) {
                System.out.println("Exception while executing query / getting result set");
            }
        } catch (SQLException ex) {
            System.out.println("Exception while creating statement");
        }
    	System.out.println("Ingredient successfully created with id="+ingr.getId());
        return ingr;
    }     
    
    public void remove(int id){
    	try (Statement st = DBConnector.getConnection().createStatement()) {
	    	st.execute("DELETE FROM ingredient WHERE id="+id);
	    	System.out.println("Ingredient with id="+id+" successfully removed");
        } catch (SQLException ex) {
            System.out.println("Exception while creating statement / getting the result");
        }
    }
}
