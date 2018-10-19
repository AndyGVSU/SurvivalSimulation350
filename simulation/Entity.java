package simulation;

public abstract class Entity {
	
	private int nutrients;
	protected String name;
	protected char symbol;
	private int height;
	private int width;


	public Entity(int nutrients, int height, int width) {
		// System.out.println("Enity-----------------------------------------");
		this.nutrients = nutrients; 
		this.height = height;
		this.width = width;
		
	}
	
	public int setNutrients(int newNutrients) {
		nutrients = newNutrients;
		return nutrients;
		
	}
	public int getNutrients() {
		return nutrients;
	}
	
	public int setHeight(int newheight) {
		height = newheight;
		return height;		
	}
	
	public int getHeight() {
		return height;
	}
	
	public int setWidth(int newwidth) {
		width = newwidth;
		return width;
			
	}
	
	public int getWidth() {
		return width;
	}

	public char getSymbol() {return symbol; }

	public String toString() {
		return "ENTITY TYPE: " + name;
	}
}
