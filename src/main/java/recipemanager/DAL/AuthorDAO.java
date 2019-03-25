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
	private static final String EXCEPTION_IN_RESULTSET = "Exception while opening connection / executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
			
	public List<Author> getAllAuthors() {
        List<Author> list = new ArrayList<Author>();
		try (Connection connection = DBConnector.openConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(GET_ALL_AUTHORS)) {
			while (resultSet.next()) {
				Author author = new Author(resultSet.getInt("id"), resultSet.getString("fio"));
				list.add(author);
			}
		} catch (Exception e) {
			System.out.println(EXCEPTION_IN_RESULTSET);
		}
        System.out.println("Authors list:");
        for (Author author : list) {
        	author.toString();
        }
        return list;            
    }
 	
    public Author getById(int id) {
        Author author = null;
        try (Connection connection = DBConnector.openConnection();
   			 Statement statement = connection.createStatement();
        	 ResultSet resultSet = statement.executeQuery(GET_AUTHOR_BY_ID+id)){	            
        	while(resultSet.next()) {
            	author = new Author(id, resultSet.getString("fio"));
            }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULTSET);
        }
        if (author == null) {
			System.out.println("Author is not found");
		} else {
			author.toString();
		}
        return author;
    }
    
    public Author create(String fio) {
    	 Author author = null;
    	 try (Connection connection = DBConnector.openConnection(); 
    		  PreparedStatement statement = connection.prepareStatement(CREATE_AUTHOR)){
 	         statement.setString(1, fio);
    		 try (ResultSet resultSet = statement.executeQuery()) {
	            while(resultSet.next()) {
	            	author = new Author(Integer.valueOf(resultSet.getString("id")), fio);
	            }
 	        } catch (SQLException ex) {
 	        	System.out.println(EXCEPTION_IN_RESULTSET);
 	        }
    	 } catch (Exception ex) {
    		 System.out.println(EXCEPTION_IN_STATEMENT);
    	 } 
    	 if (author == null) {
			 System.out.println("Author is not created");
		 } else {
			 author.toString();
		 }
         return author;
    }
}
