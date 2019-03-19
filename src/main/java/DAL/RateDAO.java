package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entities.Ingredient;
import Entities.Rate;
import Entities.Recipe;

public class RateDAO {
	public ArrayList<Rate> getRatesByRecId(int recipeId) {
        ArrayList<Rate> list = new ArrayList<Rate>();
        ResultSet rs = DBConnector.statementWithRS("SELECT * FROM rate where recipe_id="+recipeId);
        try {
            while(rs.next()) {
            	Ingredient ingr = new IngredientDAO().getById(rs.getInt("ingredient_id"));
                list.add(new Rate(
                		rs.getInt("id"),
                		recipeId, 
                		ingr, 
                		rs.getDouble("count"),
                		rs.getString("unit")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("FAIL");
        }
        return list;
    }

    public Rate getById(int id) {
        ResultSet rs = DBConnector.statementWithRS("SELECT * FROM rate where id="+id);
        Rate rate = null;
        try {
            while(rs.next()) {  
            	Ingredient ingr = new IngredientDAO().getById(rs.getInt("ingredient_id"));           	
                rate = new Rate( 
                		id,
                		rs.getInt("recipe_id"),
                		ingr, 
                		rs.getDouble("count"),
                		rs.getString("unit")
                );
            }
        } catch (SQLException ex) {
            System.out.println("FAIL");
        }
        return rate;
    }
    
    public void setIngr(Rate rate, Ingredient ingr) { 
        rate.setIngr(ingr);    
        DBConnector.statementWithRS("UPDATE rate SET ingredient_id="+ingr.getId()+" WHERE id="+rate.getId());        
    }
    
    public void setCount(Rate rate, double count) {  
        rate.setCount(count);
        DBConnector.statementWithRS("UPDATE rate SET count="+count+" WHERE id="+rate.getId());
    }
   
    public void setUnit(Rate rate, String unit) {    
        rate.setUnit(unit);
        DBConnector.statementWithRS("UPDATE rate SET unit="+unit+" WHERE id="+rate.getId());
    }
    
    public List<Rate> getAllRecipeRates(Recipe recipe) {
        List<Rate> list = new ArrayList<Rate>();
        ResultSet rs = DBConnector.statementWithRS(
            "SELECT ingredient_id, count, units_id FROM rate WHERE recipe_id="+recipe.getId()
        );
        try {
            while(rs.next()) {
            	Ingredient ingr = new IngredientDAO().getById(rs.getInt("ingredient_id"));
                list.add(new Rate(
                		rs.getInt("id"),
                		recipe.getId(), 
                		ingr, 
                		rs.getDouble("count"),
                		rs.getString("unit")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("FAIL");
        }        
        return list;
    }
    
    public Rate create(Recipe recipe, Ingredient ingr, double count, String unit) {
        ResultSet rs = DBConnector.statementWithRS(
            "INSERT INTO rate(ingredient_id, recipe_id, count, unit) "+
            "VALUES("+ingr.getId()+", "+recipe.getId()+", "+count+", "+unit+") RETURNING id;"
        );
        Rate rate = null;
        try {
            while(rs.next()) {
            	rate = new Rate(
            		rs.getInt("id"),
            		recipe.getId(),
            		ingr,
            		count,
            		unit
            	);
            }
        } catch (SQLException ex) {
            System.out.println("FAIL");
        } 
        return rate;
    }
    
    public void remove(int id){
        DBConnector.statementWithRS("DELETE FROM rate WHERE id="+id);
    }
}
