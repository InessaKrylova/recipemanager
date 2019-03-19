package Entities;

public class Rate extends Entity {	

	private int recipeId;  
    private Ingredient ingr;   
    private double count;
    private String unit;
    
    public Rate(int id, int recipeId, Ingredient ingr, double count, String unit) {
		super(id);
		this.recipeId = recipeId;
		this.ingr = ingr;
		this.count = count;
		this.unit = unit;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
    
    
}
