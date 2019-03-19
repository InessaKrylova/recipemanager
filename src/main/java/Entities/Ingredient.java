package Entities;

public class Ingredient extends Entity{        
   	private String title; 
    private int caloricity;
    
    public Ingredient(int id, String title, int caloricity) {
		super(id);
		this.title = title;
		this.caloricity = caloricity;
	}
    
    public Ingredient() {
    	super();
    }
    
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getCaloricity() {
		return caloricity;
	}
	
	public void setCaloricity(int caloricity) {
		this.caloricity = caloricity;
	}      
}