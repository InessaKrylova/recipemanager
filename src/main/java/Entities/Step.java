package Entities;

public class Step extends Entity{  
    
    private String descr;
    private int number;
    private int recipeId;

	public Step(int id, String descr, int number, int recipeId) {
		super(id);
		this.descr = descr;
		this.number = number;
		this.recipeId = recipeId;
	}
	
	public Step() {
		super();
	}
       
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}  
}
