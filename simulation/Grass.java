package simulation;

import java.util.ArrayList;

/**
 *  The Grass-Entity is a specific "species" of the Plant-Entity. Within the
 *  entire system, the grass entity is responsible for much of the plant's
 *  entire behavior. Within the grid, the grass-entity is the "stem" of the
 *  plant.
 *
 *  The bottom grass-entity of "depth 0" is responsible for root growth and
 *  oversight.
 *
 *  The entire plant dies if the bottom grass-entity has zero nutrients.
 *
 *  Each plant-entity passes its nutrient value upward to the total nutrient
 *  value of the plant-entity above it. The total nutrient value is equal to
 *  the sum of the leaf-nutrients (left and right) feeding into it, as well as
 *  the root nutrients.
 *
 * 	@author Brendon Murthum & Parker ***
 */
public class Grass extends Plant {

    /** Number of nutrients used to live another step. Should be low. */
	private final int survivalREQ = 1;
	/** Number of nutrients used to grow another plant-stem. */
	private final int plantREQ = 20;
	/** Number of nutrients used to grow another root. */
	private final int rootREQ = 5;
	/** Number of nutrients used to create another leaf. */
	private final int leafREQ = 10;
	/** Maximum depth/height of plant blocks. **/
	private final int stemDEPTH = 10;
	/** Maximum number of roots. **/
	private final int maxROOTS = 8;
	/** Time in steps between root growth attempts. **/
	private final int rootINTERVAL = 4;
	/** Time in steps between plant growth attempts. **/
	private final int plantINTERVAL = 5;

	/** Plant-entity has the ability to make leaves.
	 * 	This may be used to disallow leaf-creation after the
	 * 	plant has grown to be taller. */
	private boolean canMakeLeaves = true;

	/**
	 * The contructor method of the grass entity. This sets GUI colors and
	 * letters. It also establishes max values and grass behavior variables.
	 * @param sim   The simulation object that contains the entire system.
	 * @param flow  A "copy" of the entity that this grass was born from.
	 * @param depth The distance of this entity from the original plant
	 *              entity.
	 * @param row   The "y" coordinate of this entity.
	 * @param col   The "x" coordinate of this entity.
	 */
	public Grass(final MainSimulation sim, final Entity flow,
			 final int depth, final int row, final int col) {
		super(sim, flow, depth, row, col);
		name = "GRASS";
		symbol = 'G';

		// These are currently used in MainSimulation() to
		// check for possible growth.
		survivalRequirement = survivalREQ;
		growPlantRequirement = plantREQ;
		growLeafRequirement = leafREQ;
		growRootRequirement = rootREQ;

		maxStemDepth = stemDEPTH;
		maxRoots = maxROOTS;
		rootGrowthInterval = rootINTERVAL;
		plantGrowthInterval = plantINTERVAL;

		this.setColor(0);
	}

	/**
	 * Allows for root growth if nutrients are available and roots
	 * aren't at max.
	 * @return	Returns TRUE if this entire plant is allowed to
	 * 			grow another root during this tick.
	 */
	public boolean canGrowRoot() {
		// Specifies the original-grass as responsible for this logic.
		if (this.getDepth() == 0
				&& nutrients >= growRootRequirement
				&& rootsGrown < maxRoots - 1
				&& rootGrowthTickCount >= rootGrowthInterval) {
			rootsGrown++;
			rootGrowthTickCount = 0;
			return true;
		} else {
			rootGrowthTickCount++;
			return false;
		}
	}

	/**
	 * Allows for leaf growth. This method limits leaf growth to when
	 * the called-on plant-entity has the required nutrients.
	 * @return	TRUE if this plant-entity will be allowed to spawn
	 * 			a leaf to either the right or left.
	 * @see 	MainSimulation.java growthManage()
	 */
	public boolean canGrowLeaf() {
		Entity left = checkAdjacent(AdjacentEntities.LEFT,
				this.getRow(), this.getColumn());
		Entity right = checkAdjacent(AdjacentEntities.RIGHT,
				this.getRow(), this.getColumn());
		return (nutrients >= growLeafRequirement
				&& (left instanceof Air
					&& left.getNutrients() > 0)
					|| right instanceof Air
					&& right.getNutrients() > 0);
	}

	@Override
	/**
	 * This handles the creation of a new plant-entity above the
	 * current plant-entity.
	 */
	public void growPlant() {
		if (checkAdjacent(AdjacentEntities.UP, this.getRow(),
				this.getColumn()) instanceof Air) {
			Grass g = new Grass(simulation,
					null, this.getDepth() + 1,
					this.getRow() - 1, this.getColumn());
			this.setFlowTo(g);
			simulation.setEntity(this.getRow() - 1,
					this.getColumn(), g);
			isTopStem = false;
		}
	}

	@Override
	/**
	 * This handles the creation of a leaf-entity to the right or
	 * left of a current plant-entity. It first tries to grow to the
	 * left--if taken, grows a leaf to the right of the current
	 * plant-entity.
	 */
	public void growLeaf() {
		if (checkAdjacent(AdjacentEntities.LEFT, this.getRow(), this.getColumn())
				instanceof Air) {
			Leaf l = new Leaf(simulation, this,
					this.getDepth() + 1, this.getRow(), this.getColumn() - 1);
			nutrientsFrom.add(l);
			simulation.setEntity(this.getRow(), this.getColumn() - 1, l);
		} else if (checkAdjacent(AdjacentEntities.RIGHT, this.getRow(), this.getColumn())
				instanceof Air) {
			Leaf l = new Leaf(simulation, this,
					this.getDepth() + 1, this.getRow(), this.getColumn() + 1);
			nutrientsFrom.add(l);
			simulation.setEntity(this.getRow(), this.getColumn() + 1, l);
		}
	}

	@Override
	/** Called on the "grass" entity at depth == 0. This grows a
	 *  root somewhere below this grass entity.
	 */
	public void growRoot() {
		// Setting this helper variable to the current-entity's row.
		int tempRow = this.getRow();

        // Check for dirt spots below the depth-zero grass to
        // add another root. Iterate down rows, below the main
        // grass-entity.
		while (tempRow < simulation.getRows() - 1) {
			// If there's no dirt, stop checking downwards
			if (checkAdjacent(AdjacentEntities.DOWN, tempRow,
					this.getColumn()) instanceof Air) {
				break;
			}
			// Check below the current space for dirt-entity
			if (checkAdjacent(AdjacentEntities.DOWN, tempRow,
					this.getColumn()) instanceof Dirt) {
				Root r = new Root(simulation,
                        simulation.getEntity(tempRow, this.getColumn()),
                        tempRow - this.getRow() + 1,
						tempRow + 1, this.getColumn());
				simulation.setEntity(tempRow + 1,
						this.getColumn(), r);
				nutrientsFrom.add(r);
				break;
			}
            // Check to the right of current space for dirt-entity
			if (checkAdjacent(AdjacentEntities.RIGHT,
					tempRow, this.getColumn())
                    instanceof Dirt && tempRow > this.getRow() + 1) {
				Root r = new Root(simulation,
                        simulation.getEntity(tempRow, this.getColumn()),
                        tempRow - this.getRow() + 1,
                        tempRow, this.getColumn() + 1);
				simulation.setEntity(r.getRow(),
                        r.getColumn(), r);
				nutrientsFrom.add(r);
				break;
			}
            // Check to the left of current space for dirt-entity
			if (checkAdjacent(AdjacentEntities.LEFT, tempRow, this.getColumn())
                    instanceof Dirt && tempRow > this.getRow() + 1) {
				Root r = new Root(simulation,
                        simulation.getEntity(tempRow, this.getColumn()),
                        tempRow - this.getRow() + 1, tempRow, this.getColumn() - 1);
				simulation.setEntity(tempRow, this.getColumn() - 1, r);
				nutrientsFrom.add(r);
				break;
			}
			tempRow++;
		}
	}

	/**
	 *  This method is called on a "stem" plant entity. This method is only
     *  called in the case that canGrowFruit() returns TRUE. This method
     *  destroys a left or right leaf then creates a fruit entity in its place.
	 */
	public void growFruit() {
		Entity e = checkAdjacent(AdjacentEntities.LEFT, this.getRow(), this.getColumn());
        if (e instanceof Leaf) {
            Fruit f = new Fruit(simulation, this,
                    this.getDepth() + 1, this.getRow(), this.getColumn() - 1);
            simulation.setEntity(this.getRow(), this.getColumn() - 1, f);
            nutrientsFrom.remove(e);
            fruitsProduced++;
			tickOfLastFruitMade = this.getLifeSteps();
        }
	}

    /**
     * Handles the complete death of a plant.
     * @return  Returns a list of the deleted plant-entities.
     * @see     MainSimulation.java growthManage()
     */
	public ArrayList<Entity> die() {
		ArrayList<Entity> deletedPlants = new ArrayList<>();

		if (getFlowTo() instanceof Plant) {
			deletedPlants.addAll(((Plant) getFlowTo()).die());
        }

        // Replace collector-entitites connected to the main
        // grass-entities with air or dirt.
	    for (Collector c : nutrientsFrom) {
	    	Entity replace = null;
	    	if (c instanceof Leaf) {
				replace = new Air(simulation, null,
                        0, c.getRow(), c.getColumn());
			} else if (c instanceof Root) {
				replace = new Dirt(simulation, null,
                        0, c.getRow(), c.getColumn());
			}
			simulation.setEntity(c.getRow(), c.getColumn(),
                    replace);
        }

        // Replace the current entity with air
        simulation.setEntity(getRow(), getColumn(),
			new Air(simulation, null, getDepth(),
                    getRow(), getColumn()));

	    deletedPlants.add(this);
	    return deletedPlants;
    }
}
