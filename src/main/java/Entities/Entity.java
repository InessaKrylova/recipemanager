package Entities;

public class Entity {
	private int id;
	
	public Entity(int id) {
		super();
		this.id = id;
	}
	
	public Entity() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
}