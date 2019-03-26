package recipemanager.Entities;

import java.util.ArrayList;
import java.util.List;

public class Recipe extends Entity {   
       
	private List<Step> steps; 
    private List<Rate> rates;   
    private String title;    
    private String kitchen; 
    private String cookTime;
    private String section;  
    private Author author;
    private int caloricity; 
    private int servingCount;     
    
    public Recipe(int id, List<Step> steps, List<Rate> rates, String title, String kitchen, String cookTime,
			String section, Author author, int caloricity, int servingCount) {
		super(id);
		this.steps = steps;
		this.rates = rates;
		this.title = title;
		this.kitchen = kitchen;
		this.cookTime = cookTime;
		this.section = section;
		this.author = author;
		this.caloricity = caloricity;
		this.servingCount = servingCount;
	}
    
    
    public Recipe(int id, String title, String kitchen, String cookTime,
			String section, Author author, int caloricity, int servingCount) {
		super(id);
		this.title = title;
		this.kitchen = kitchen;
		this.cookTime = cookTime;
		this.section = section;
		this.author = author;
		this.caloricity = caloricity;
		this.servingCount = servingCount;
		this.steps = new ArrayList<Step>();
		this.rates = new ArrayList<Rate>();
		
	}

    public Recipe() {
    	super();
    }
    
    public void addStep(Step step) {
    	this.steps.add(step);
    }
    
    public void removeStep(int stepId) {
    	for (int i = 0; i < this.steps.size(); i++) {
            if (this.steps.get(i).getId() == stepId) {
            	this.steps.remove(i); 
            	break;
            }
        }
    }
    
    public void addRate(Rate rate) {
    	this.rates.add(rate);
    }
    
    public void removeRate(int rateId) {
    	 for (int i = 0; i < this.rates.size(); i++) {
             if (this.rates.get(i).getId()==rateId) {
            	 this.rates.remove(i); break;
             }
         }
    }
    
	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKitchen() {
		return kitchen;
	}

	public void setKitchen(String kitchen) {
		this.kitchen = kitchen;
	}

	public String getCookTime() {
		return cookTime;
	}

	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public int getCaloricity() {
		return caloricity;
	}

	public void setCaloricity(int caloricity) {
		this.caloricity = caloricity;
	}

	public int getServingCount() {
		return servingCount;
	}

	public void setServingCount(int servingCount) {
		this.servingCount = servingCount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\nRecipe: {");
		sb.append("id=").append(this.getId())
		  .append(", title=").append(this.getTitle())
		  .append(", cookTime=").append(this.getCookTime())
		  .append(", caloricity=").append(this.getCaloricity())
		  .append(", servingCount=").append(this.getServingCount())
		  .append(", section=").append(this.getSection())
		  .append(", author=").append(this.getAuthor().getFio())
		  .append(", kitchen=").append(this.getKitchen())
		  .append(", stepsCount=").append(this.getSteps().size())
		  .append(", ratesCount=").append(this.getRates().size())
		  .append("}\n");
		return sb.toString();
	}  
}
