package recipemanager.DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import recipemanager.Entities.Author;

/**
 * Recipe author (user)
 */
public class AuthorDAO {
	
	private static final String GET_ALL_AUTHORS = "SELECT * FROM author";
	private static final String GET_AUTHOR_BY_ID ="SELECT fio FROM author WHERE id=";
	private static final String CREATE_AUTHOR = "INSERT INTO author (fio) VALUES (?) RETURNING id;";
	private static final String EXCEPTION_IN_RESULTSET = "Exception while executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
			
	public List<Author> getAllAuthors() {
        List<Author> list = new ArrayList<Author>();
			try (Statement statement = DBConnector.openConnection().createStatement()) {
				try (ResultSet resultSet = statement.executeQuery(GET_ALL_AUTHORS)) {
					while (resultSet.next()) {
						Author author = new Author(resultSet.getInt("id"), resultSet.getString("fio"));
						list.add(author);
					}
				} catch (SQLException ex) {
					System.out.println(EXCEPTION_IN_RESULTSET);
				}
			} catch (SQLException ex) {
				System.out.println(EXCEPTION_IN_STATEMENT);
			}
        System.out.println("Authors list:");
        for (Author author : list) {
        	System.out.println(author.getId()+". "+author.getFio());
        }
        return list;            
    }
    
	
    public Author getById(int id) {
        Author author = null;
        try (Statement statement = DBConnector.openConnection().createStatement()){
	        try (ResultSet resultSet = statement.executeQuery(GET_AUTHOR_BY_ID+id)){	            
	        	while(resultSet.next()) {
	            	author = new Author(id, resultSet.getString("fio"));
	            	System.out.println("Author is found: "+author.getId()+". "+author.getFio());
	            }
	        } catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
        } catch (SQLException ex) {
        	System.out.println(EXCEPTION_IN_STATEMENT);
        } 
        if (author == null) System.out.println("Author is not found");
        return author;
    }
    
    public Author create(String fio) {
    	 Author author = null;
    	 try (PreparedStatement statement = DBConnector.openConnection().prepareStatement(CREATE_AUTHOR)){
 	         statement.setString(1, fio);
    		 try (ResultSet resultSet = statement.executeQuery()) {
	            while(resultSet.next()) {
	            	author = new Author(Integer.valueOf(resultSet.getString("id")), fio);
	            }
 	        } catch (SQLException ex) {
 	        	System.out.println(EXCEPTION_IN_RESULTSET);
 	        }
    	 } catch (SQLException ex) {
    		 System.out.println(EXCEPTION_IN_STATEMENT);
    	 } 
    	 System.out.println(author == null 
    			 ? "Author is not created"
    			 : "Author "+fio+" is successfully created with id="+author.getId());
         return author;
    }
}
