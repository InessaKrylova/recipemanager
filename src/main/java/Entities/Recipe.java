package Entities;

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
   
	/*public String createImagePathFrom(String sourcePath) {
	    String imagePath = null;
		try {
	        if (sourcePath.equals("0")) {
	            imagePath = "0";    
	        } else { 
	            int i = sourcePath.lastIndexOf('.');
	            String extension = (i>0) 
	                 ? ("."+sourcePath.substring(i+1)) : "";  
	            imagePath = "/recipes/"+this.getId()+extension; 
	
	            System.out.println(sourcePath+" ===> "+imagePath);
	            
	            OutputStream os = new FileOutputStream(new File(imagePath));
	            InputStream is = new FileInputStream(new File(sourcePath));       
	            byte[] buffer = new byte[1024];
	            int length;
	            while ((length = is.read(buffer)) > 0) {
	                os.write(buffer, 0, length);
	            }
	            System.out.println("success");
	            is.close();
	            os.close();
	        }   
	    } catch (Exception e) {
	    	System.out.println("! Can`t get image path for recipe with id="+this.getId());
	    }
		return imagePath;
	}*/
    
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
}
