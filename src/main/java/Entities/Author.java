package Entities;

/**
 * Recipe author (user)
 */
public class Author extends Entity{
    private String fio;
    
	public String getFio() {
		return fio;
	}
	
	public void setFio(String fio) {
		this.fio = fio;
	}
	
	public Author(int id, String fio) {
		super(id);
		this.fio = fio;
	}

	public Author() {
		super();
	}	
}