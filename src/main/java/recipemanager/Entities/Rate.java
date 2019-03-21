package recipemanager.Entities;

public class Rate extends Entity {	

	private int recipeId;  
    private Ingredient ingredient;   
    private double count;
    
    public Rate(int id, int recipeId, Ingredient ingredient, double count) {
		super(id);
		this.recipeId = recipeId;
		this.ingredient = ingredient;
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
		return ingredient;
	}
	public void setIngr(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}   
}