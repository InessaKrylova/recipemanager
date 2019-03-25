package recipemanager.Entities;

public class Step extends Entity{  
    
    private String description;
    private int number;
    private int recipeId;

	public Step(int id, String description, int number, int recipeId) {
		super(id);
		this.description = description;
		this.number = number;
		this.recipeId = recipeId;
	}
	
	public Step() {
		super();
	}
       
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\nStep: {");
		sb.append("id=").append(this.getId())
		  .append(", number=").append(this.getNumber())
		  .append(", description=").append(this.getDescription())
		  .append(", recipeId=").append(this.getRecipeId())
		  .append("}\n");
		System.out.println(sb.toString());
		return sb.toString();
	}  
}
