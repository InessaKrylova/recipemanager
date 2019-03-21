package recipemanager.DAL;

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
	
	public void remove(int id){
    	try (Statement statement = DBConnector.openConnection().createStatement()) {
	    	statement.execute(REMOVE_RECIPE+id);
	    	System.out.println("Recipe with id="+id+" successfully removed");
        } catch (SQLException ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
        }
    }               
    
    public Recipe create(String title, String kitchen, String cookTime, String section, int servingCount, Author author, int caloricity) {
    	Recipe recipe =  null;   
    	try (PreparedStatement statement = DBConnector.openConnection().prepareStatement(CREATE_RECIPE)) {
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
	            System.out.println(recipe != null 
	            		? "Recipe successfully created"
	            		: "Recipe is not created");
	    	} catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    }       
        return recipe;
    }
    
    public Recipe getById(int id) {
        Recipe r = null;
        try(Statement st = DBConnector.openConnection().createStatement()){
	        try (ResultSet rs = st.executeQuery(GET_RECIPE_BY_ID+id)){
	            while (rs.next()){                    
	            	r = new Recipe(
	            		id, 
	            		new ArrayList<Step>(),//new StepDAO().getStepsListByRecId(id),
	            		new ArrayList<Rate>(),//new RateDAO().getRatesByRecId(id),
	                    rs.getString("title"),
	                    rs.getString("kitchen"),
	                    rs.getString("cook_time"),
	                    rs.getString("section"),
	                    new AuthorDAO().getById(rs.getInt("author")),
	            		rs.getInt("caloricity"),
	                    rs.getInt("serving_count")            
	            	); 
	            	System.out.println("Recipe with id="+id+" is found");
	            }
	        } catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    } 
        return r;
    }
    
    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        try(Statement statement = DBConnector.openConnection().createStatement()){
	        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM recipe")){
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
	        } catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    } 
        System.out.println(list.size()+" recipes found");
        return list;
    }

    public void addStep(Recipe recipe, int number, String description) {
	    Step step = new StepDAO().create(recipe.getId(), number, description);
    	recipe.addStep(step); 
	}

	public void removeStep(Recipe recipe, int stepId) {	
	    new StepDAO().remove(stepId);
	    recipe.removeStep(stepId);
	}

	public void addRate(Recipe recipe, int ingredientId, double count) {
	    Rate rate = new RateDAO().create(recipe.getId(), ingredientId, count);
		recipe.addRate(rate);	    
	}
	
	public void removeRate(Recipe recipe, int rateId) {
	    new RateDAO().remove(rateId);
	    recipe.removeRate(rateId);
	} 

}
	/*public void setTitle(Recipe recipe, String title) {    
		recipe.setTitle(title);
		DBConnector.statementWithRS("UPDATE recipe SET title=\'"+title+"\' WHERE id="+recipe.getId());
	}

	public void setKitchen(Recipe recipe, String kitchen) {  
		recipe.setKitchen(kitchen);  
		DBConnector.statementWithRS("UPDATE recipe SET kitchen="+kitchen+" WHERE id="+recipe.getId());
	}

	public void setCookTime(Recipe recipe, String time) {    
		recipe.setCookTime(time);
		DBConnector.statementWithRS("UPDATE recipe SET cook_time=\'"+time+"\' WHERE id="+recipe.getId());
	}

	public void setSection(Recipe recipe, String section) {    
		recipe.setSection(section);    
		DBConnector.statementWithRS("UPDATE recipe SET section="+section+" WHERE id="+recipe.getId());
	}

	public void setCalor(Recipe recipe, int caloricity) {   
		recipe.setCaloricity(caloricity);    
		DBConnector.statementWithRS("UPDATE recipe SET caloricity="+caloricity+" WHERE id="+recipe.getId());
	} 

	public void setServingCount(Recipe recipe, int servCount) {   
		recipe.setServingCount(servCount);
		DBConnector.statementWithRS("UPDATE recipe SET serving_count="+servCount+" WHERE id="+recipe.getId());
	}
  
    public ArrayList<Recipe> searchBy(ArrayList<String> keywords, ArrayList<String> antiwords) {
        ArrayList<Recipe> result1 = new ArrayList<Recipe>();
        ArrayList<Recipe> result2 = new ArrayList<Recipe>();
        ArrayList<Recipe> all = getAllRecipes();
         
        if (keywords.isEmpty()) {
            result1 = all;
        }
        else {
            for (int i = 0; i < all.size(); i++) {
                boolean contains = false;
                Recipe recipe = all.get(i);
                String title = recipe.getTitle().toLowerCase();
                String kitchen = recipe.getKitchen().toLowerCase();
                String author = recipe.getAuthor().getFio().toLowerCase();
                String section = recipe.getSection().toLowerCase();
                List<Rate> rates = recipe.getRates();

                for (String keyword : keywords) {
                    String kw = keyword.toLowerCase();

                    contains = title.contains(kw) || kitchen.contains(kw) || author.contains(kw) || section.contains(kw);
                    if (!contains) {  //если еще не нашли, проверяем в пропорциях
                        for (int k = 0; k < rates.size(); k++) { 
                            String product = rates.get(k).getIngr().getTitle().toLowerCase();
                            if (product.contains(kw)) {
                                contains = true; break;
                            }
                        } 
                    }
                    if (contains)  { 
                        result1.add(recipe); break;
                    }
                }
            }  
        }
        if (antiwords.isEmpty()) {
            result2 = result1;
        } 
        else {
            for (int i = 0; i < result1.size(); i++) {
                boolean contains = false;
                Recipe recipe = result1.get(i);
                String title = recipe.getTitle().toLowerCase();
                String kitchen = recipe.getKitchen().toLowerCase();
                String author = recipe.getAuthor().getFio().toLowerCase();
                String section = recipe.getSection().toLowerCase();
                List<Rate> rates = recipe.getRates();     

                for (String anti : antiwords) {
                    String aw = anti.toLowerCase();

                    contains = title.contains(aw) || kitchen.contains(aw) || author.contains(aw) || section.contains(aw);
                    if (!contains) { //если еще не нашли, проверяем в пропорциях
                        for (int k = 0; k < rates.size(); k++) { 
                            String product = rates.get(k).getIngr().getTitle().toLowerCase();
                            if (product.contains(aw))  {
                                contains = true; break;
                            }
                        }
                    }
                    if (!contains) { 
                        result2.add(recipe);
                    } else break;              
                }                     
            }  
        }
        
        return result2;
    }  
*/

