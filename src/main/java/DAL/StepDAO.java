package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import Entities.Step;

public class StepDAO {
	public TreeMap<Integer,Step> getStepsMapByRecId(int recipeId) {
        TreeMap<Integer, Step> map = new TreeMap<Integer, Step>();
        try {
        ResultSet rs = DBConnector.statementWithRS("SELECT * FROM step where recipe_id="+recipeId);
        
            while(rs.next()) {
                Step step = new Step(
                	rs.getInt("id"),
                	rs.getString("description"),
                	rs.getInt("number"),
                	recipeId
                );
                map.put(step.getNumber(), step);
            }         
        } catch (SQLException ex) {
            
        	System.out.println("FAIL");
        }
        return map;
    }
    
    public ArrayList<Step> getStepsListByRecId(int recipeId) {
        ArrayList<Step> list = new ArrayList<Step>();
        ResultSet rs = DBConnector.statementWithRS("SELECT * FROM step where recipe_id="+recipeId);
        try {
            while(rs.next()) {             
                list.add(new Step(
            		rs.getInt("id"),
                    rs.getString("description"),
                    rs.getInt("number"),
                    recipeId
                ));
            }
        } catch (SQLException ex) {
            System.out.println("FAIL");
        }
        return list;
    }
    
    public void setDescr(Step step, String descr) {    
        step.setDescr(descr);  
        DBConnector.statementWithRS("UPDATE step SET description=\'"+descr+"\' WHERE id="+step.getId());
    }
    
    public void setNumber(Step step, int number) {  
        step.setNumber(number);   
        DBConnector.statementWithRS("UPDATE step SET number="+number+" WHERE id="+step.getId());
    }  
    
    public Step create(int recipeId, int number, String descr) {        
        String query = "INSERT INTO step(description, number, recipe_id) "
            + "VALUES(\'" + descr + "\', " + number + ", " + recipeId + ") RETURNING id;";
        Step step = null;
        ResultSet rs = DBConnector.statementWithRS(query);
        try {
            while(rs.next()) {
            	step = new Step(
        			rs.getInt("id"),
        			descr,
        			number,
        			recipeId
            	);
            }
        } catch (SQLException ex) {
            System.out.println("FAIL");
        }
        return step;
    }
    
    public void remove(int id) {
        DBConnector.statementWithRS("DELETE FROM step WHERE id="+id);
    }
}
