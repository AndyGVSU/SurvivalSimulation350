package simulation;

import java.io.Serializable;

/**
 *  An "entity" is a basic object that is extended by several other classes.
 *  It lays the foundation of functionality and relationships between it
 *  and the environment and other entities.
 */
public abstract class Entity implements Serializable {

    protected transient MainSimulation simulation;
	/** The number of nutrients this entity holds and may pass. */
	int nutrients;
	/** Max number of nutrients allowed to be held. */
	final int maxNutrients = 400;
	/** The number of requirements for this entity to survive. */
	int survivalRequirement;
	/** The "name" of the entity. Ex: "Dirt" */
	protected String name;
	/** The graphical symbol of the entity. Ex: "D" */
	protected char symbol;
	/** The int value of a color-value within GridPanel.java */
	protected int color;
	/** Depth is "distance" from initial plant-entity. "0" depth is main plant. */
	int depth;
	/** Identifies the entity that THIS entity would pass nutrients to. */
	private Entity flowTo;
	/** The number of ticks that this entity has lived. */
	protected int lifeSteps = 0;

	/** The row that the entity is placed in. */
	protected int row;
	/** The column that the entity is placed in. */
	protected int col;

	/**
	 * The contructor method of the fruit entity. This sets GUI colors and
	 * letters.
	 * @param sim   The simulation object that contains the entire system.
	 * @param flow  A "copy" of the entity that this fruit was born from.
	 * @param depth The distance of this entity from the original entity.
	 * @param row   The "y" coordinate of this entity.
	 * @param col   The "x" coordinate of this entity.
	 */
	public Entity(final MainSimulation sim, final Entity flow,
				  final int depth, final int row,
				  final int col) {
		this.simulation = sim;
		this.flowTo = flow;
		this.depth = depth;
		this.row = row;
		this.col = col;
	}

	/** Adds nutrients to entity up to its max number of holdable nutrients.
	 *  Roots and leaves hold certain nutrients. Stems are just stable
	 *  pathways for nutrients to flow through. Roots hold sugars (carbs),
	 *  leaves hold magnesium and other things (leaf color change is due
	 *  to tree moving nutrients before dropping them).
	 *  @param 	newNutrients The number of added nutrients for entity.
	 *  */
	public void setNutrients(final int newNutrients) {
		if (newNutrients > maxNutrients) {
			nutrients = maxNutrients;
		} else if (newNutrients < 0) {
			nutrients = 0;
		} else {
			nutrients = newNutrients;
		}
	}

	/**
	 * Adds nutrients.
	 * @param newNutrients	The number of new nutrients.
	 */
	public void addNutrients(final int newNutrients) {
		if (nutrients + newNutrients > maxNutrients) {
			nutrients = maxNutrients;
		} else {
			nutrients += newNutrients;
		}
	}

	/** Calls an entity to consume some nutrients. */
	public void useNutrients() {
		nutrients -= survivalRequirement;
	}

	/**
	 * Returns the number of nutrients that an entity holds.
	 * @return	The number of nutrients this entity holds.s
	 */
	public int getNutrients() {
		return nutrients;
	}

	/**
	 * Returns the symbol of the specified entity. Ex: "D"
	 * @return	The symbol of the entity.
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * Returns the integer version of the color of the entity.
	 * @return	The int of the color to be used for this entity.
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Returns the "depth" of the entity. This is the "distance" from
	 * the intial entity of a plant. It is used for identifying relations
	 * and limiting behaviors of certain entities at specified distances.
	 * @return	The number of spaces away from the initial plant-entity.
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Returns an data output of a particular entity to be read at the GUI.
	 * @return	A string of data of an entity.
	 */
	public String toString() {
		return "TYPE: " + name + "\nID: " + "\nNutrients: "
				+ nutrients + "\nDepth: " + depth
				+ "\nLifetime: " + lifeSteps + "\nRow: "
				+ row + "\nColumn: " + col;
	}

	/**
	 * This returns the entity of a space in a direction to the sides of
	 * the current entity as specified by the row/col.
	 * @param direction	UP, RIGHT, LEFT, DOWN, HERE
	 * @param row	The "y" coordinate of this entity.
	 * @param col	The "x" coordinate of this entity.
	 * @return
	 */
	public Entity checkAdjacent(final AdjacentEntities direction,
								int row, int col) {
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

	/** Tells an entity to update its lifesteps. This may help
	 * 	with funtions that are based on lifetime of entities. */
	public void doStep() {
		lifeSteps++;
	}

	/**
	 *  Helps with understanding where nutrients flow to, as well as
	 *  serving the die() function.
	 *  @return	Returns the entity this would send nutrients to
	 */
	public Entity getFlowTo() {
		return flowTo;
	}

	/**
	 * setParent can only be called once on an entity.
	 * @param p	The entity that this entity should pass nutrients to
	 */
	public void setFlowTo(final Entity p) {
		if (getFlowTo() == null) {
			flowTo = p;
		}
	}

	/**
	 * Tells of the entity's row within the overall grid.
	 * @return	The "y" value of the current entity
	 */
	public int getRow() {
	    return row;
	}

	/**
	 * Tells of the entity's column within the overall grid.
	 * @return	The "x" value of the current entity
	 */
	public int getColumn() {
	    return col;
	}

	/**
	 * Sets the simulation object for this entity.
	 * @param	s - Which simulation is this entity in?
	 */
	public void setSimulation(final MainSimulation s) {
	    simulation = s;
	}

	/**
	 * This is used to repeatedly test for an entity's death.
	 * @return	TRUE if the entity should keep living
	 */
	public boolean canLive() {
		return (nutrients > 0);
	}

	/**
	 * This returns the value of how long an entity has been alive.
	 * @return	The number of ticks since the entity has been created
	 */
	public int getLifeSteps() {
		return lifeSteps;
	}
}
