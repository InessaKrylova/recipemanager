package Entities;

public class Rate extends Entity {	

	private int recipeId;  
    private Ingredient ingr;   
    private double count;
    
    public Rate(int id, int recipeId, Ingredient ingr, double count) {
		super(id);
		this.recipeId = recipeId;
		this.ingr = ingr;
		this.count = count;;
	}
    
    public Rate() {
    	super();
    }
       
	public int getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	public Ingredient getIngr() {
		return ingr;
	}
	public void setIngr(Ingredient ingr) {
		this.ingr = ingr;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}   
}
