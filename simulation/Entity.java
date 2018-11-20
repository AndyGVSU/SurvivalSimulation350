package simulation;

import java.io.Serializable;

public abstract class Entity implements Serializable {

	protected transient MainSimulation simulation;
	int nutrients;
	/** Max number of nutrients allowed to be held. */
	int maxNutrients = 400;
	int survivalRequirement;
	protected String name;
	protected char symbol;
	protected int color;
	/** Depth is "distance" from initial plant-entity. "0" depth is main plant. */
	int depth;
	private Entity flowTo;
	protected int lifeSteps = 0; // Lifetime in "steps."

	// Each entity knows its location. #Brendon
	int row;
	int col;

	public Entity(MainSimulation sim, Entity flow, int depth, int row, int col) {
		this.simulation = sim;
		this.flowTo = flow;
		this.depth = depth;
		this.row = row;
		this.col = col;
	}

	/** Adds nutrients to entity up to its max number of holdable nutrients.
	 *  Roots and leaves hold certain nutrients. Stems are just stable pathways
	 *  for nutrients to flow through. Roots hold sugars (carbs), leaves hold
	 *  magnesium and other things (leaf color change is due to tree moving
	 *  nutrients before dropping them). */
	public void setNutrients(int newNutrients) {
		// Brendon would like to see roots, stems, and leaves having their
		// own max values of nutrients.
		if (newNutrients > maxNutrients)
			nutrients = maxNutrients;
		else if (newNutrients < 0)
			nutrients = 0;
		else
			nutrients = newNutrients;
	}

	public void addNutrients(int newNutrients) {
		if (nutrients + newNutrients > maxNutrients) {
			nutrients = maxNutrients;
		} else {
			nutrients += newNutrients;
		}
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

	public Entity getFlowTo() {
		return flowTo;
	}

	/** setParent can only be called once on an entity **/
	public void setFlowTo(Entity p) {
		if (getFlowTo() == null)
			flowTo = p;
	}

	public int getRow() { return row; }

	public int getColumn() { return col; }

	public void setSimulation(MainSimulation s) { simulation = s; }

	public boolean canLive() {
		return (nutrients > 0);
	}

	public int getLifeSteps() {
		return lifeSteps;
	}
}
