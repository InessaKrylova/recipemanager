package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entities.Author;

/**
 * Recipe author (user)
 */
public class AuthorDAO {
	public List<Author> getAllAuthors() {
        List<Author> list = new ArrayList<Author>();
        
        try (Statement st = DBConnector.getConnection().createStatement()){
        	try (ResultSet rs = st.executeQuery("SELECT * FROM author")) {
	            while(rs.next()) {
	                Author a = new Author(rs.getInt("id"), rs.getString("fio"));
	                list.add(a);
	            }
        	} catch (SQLException ex) {
                System.out.println("Exception while executing query / getting result set");
            }
        } catch (SQLException ex) {
            System.out.println("Exception while creating statement");
        }
        System.out.println("Authors list:");
        for (Author author : list) {
        	System.out.println(author.getId()+". "+author.getFio());
        }
        return list;            
    }
    
	
    public Author getById(int id) {
        Author author = null;
        try (Statement st = DBConnector.getConnection().createStatement()){	        
	        try (ResultSet rs = st.executeQuery("SELECT fio FROM author WHERE id="+id)){	            
	        	while(rs.next()) {
	            	author = new Author(id, rs.getString("fio"));
	            	System.out.println("Author is found: "+author.getId()+". "+author.getFio());
	            }
	        } catch (SQLException | NullPointerException ex) {
	        	System.out.println("Exception while executing query / getting result set");
	        }
        } catch (SQLException ex) {
        	System.out.println("Exception while creating statement");
        } 
        if (author == null) System.out.println("Author is not found");
        return author;
    }
    
    public Author create(String fio) {
    	 Author author = null;
    	 try (Statement st = DBConnector.getConnection().createStatement()){	        
 	        try (ResultSet rs = st.executeQuery("INSERT INTO author (fio) VALUES (\'"+fio+"\') RETURNING id;")) {
	            while(rs.next()) {
	            	author = new Author(Integer.valueOf(rs.getString("id")), fio);
	            }
 	        } catch (SQLException ex) {
 	        	System.out.println("Exception while executing query / getting result set");
 	        }
    	 } catch (SQLException ex) {
    		 System.out.println("Exception while creating statement");
    	 } 
    	 System.out.println(author == null 
    			 ? "Author is not created"
    			 : "Author "+fio+" is successfully created with id="+author.getId());
         return author;
    }
}
