package simulation;

import java.io.Serializable;

public abstract class Entity implements Serializable {

	protected transient MainSimulation simulation;
	int nutrients;
	int survivalRequirement;
	int growPlantRequirement;
	int growLeafRequirement;
	int growRootRequirement;
	protected String name;
	protected char symbol;
	protected int color;
	int depth;
	private Entity parent;
	protected int lifeSteps = 0; // Lifetime in "steps."

	// Each entity knows its location. #Brendon
	int row;
	int col;

	public Entity(MainSimulation sim, Entity parent, int depth, int row, int col) {
		this.simulation = sim;
		this.parent = parent;
		this.depth = depth;
		this.row = row;
		this.col = col;
	}

	public void setNutrients(int newNutrients) {
		nutrients = newNutrients;
	}

	public void addNutrients(int newNutrients) {
		setNutrients(newNutrients + getNutrients());
	}

	public void useNutrients() {
		nutrients -= survivalRequirement;
	}

	public int getNutrients() {
		return nutrients;
	}

	public char getSymbol() {
		return symbol;
	}

	public int getColor() {
		return color;
	}

	public int getDepth() {
		return depth;
	}

	public String toString() {
		return "TYPE: " + name + "\nID: " + "\nNutrients: " + nutrients + "\nDepth: " + depth
				+ "\nLifetime: " + lifeSteps + "\nRow: " + row + "\nColumn: " + col;
	}

	public Entity checkAdjacent(AdjacentEntities direction, int row, int col) {
		switch (direction) {
		case UP:
			return simulation.getEntity(row - 1, col);
		case DOWN:
			return simulation.getEntity(row + 1, col);
		case LEFT:
			return simulation.getEntity(row, col - 1);
		case RIGHT:
			return simulation.getEntity(row, col + 1);
		case HERE:
			return simulation.getEntity(row, col);
		}
		return null;
	}

	public void doStep() {
		lifeSteps++;
	}

	public Entity getParent() {
		return parent;
	}

	/** setParent can only be called once on an entity **/
	public void setParent(Entity p) {
		if (getParent() == null)
			parent = p;
	}

	public int getRow() { return row; }

	public int getColumn() { return col; }

	public void setSimulation(MainSimulation s) { simulation = s; }

	public boolean canLive() {

		return (nutrients >= 0);
	}

	public boolean canGrowPlant() {
		return (nutrients >= growPlantRequirement);

	}

	public boolean canGrowLeaf() {
		return (nutrients >= growLeafRequirement);

	}

	public boolean canGrowRoot() {
		return (nutrients >= growRootRequirement);

	}

}
