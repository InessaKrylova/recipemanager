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
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\nRate: {");
		sb.append("id=").append(this.getId())
		  .append(", recipeId=").append(this.getRecipeId())
		  .append(", ingredient=").append(this.getIngredient().getTitle())
		  .append(", count=").append(this.getCount())
		  .append("}\n");
		return sb.toString();
	}	
}
