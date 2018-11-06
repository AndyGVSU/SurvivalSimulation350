package simulation;

public abstract class Entity {

	static int nextID = 0;
	protected MainSimulation simulation;
	int nutrients;
	int nutrientGrowthRequirement;
	protected String name;
	protected char symbol;
	int height;
	int width;
	int entityID;
	protected int lifeSteps = 0; // Lifetime in "steps."

	// Each entity knows its location. #Brendon
	int row;
	int col;

	public Entity(MainSimulation sim, int nutrients, int height, int width, int row, int col) {
		this.simulation = sim;
		this.nutrients = nutrients; 
		this.height = height;
		this.width = width;
		this.row = row;
		this.col = col;
		this.entityID = Entity.nextID++;
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
		return "TYPE: " + name + "\nID: " + entityID + "\nRow: " + row + "\nColumn: " + col;
	}

	public Entity checkAdjacent(AdjacentEntities direction, int row, int col) {
		switch(direction) {
			case UP: return simulation.getEntity(row - 1, col);
			case DOWN: return simulation.getEntity(row + 1, col);
			case LEFT: return simulation.getEntity(row, col - 1);
			case RIGHT: return simulation.getEntity(row, col + 1);
		}
		return null;
	}

	public void doStep() {
		lifeSteps = lifeSteps + 1;
	}
}
