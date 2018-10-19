package simulation;

public abstract class Entity {
	
	int nutrients;
	protected String name;
	protected char symbol;
	int height;
	int width;
	int entityID;

	// Each entity knows its location. #Brendon
	int row;
	int col;

	public Entity(int nutrients, int height, int width, int row, int col, int entityID) {
		// System.out.println("Enity-----------------------------------------");
		this.nutrients = nutrients; 
		this.height = height;
		this.width = width;
		this.row = row;
		this.col = col;
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
		return "TYPE: " + name + " - " + entityID + " - R" + row + " - C" + col;
	}
}
