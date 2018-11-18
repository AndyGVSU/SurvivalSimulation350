package simulation;

import java.util.ArrayList;

public abstract class Plant extends Entity {
	protected ArrayList<Collector> nutrientsFrom;

    int growPlantRequirement;
    int growLeafRequirement;
    int growRootRequirement;

    final private int fruitCreationThreshold = maxNutrients - 100;

    /** The furthest a stem is allowed to be from the "seed". This is "max-height". **/
    protected int maxStemDepth;
    /** Number of roots grown. Keeps track for plant-unique max roots. **/
    protected int rootsGrown = 0;
    /** Max number of roots for this particular plant. **/
    protected int maxRoots;
    /** A root may grow once every X steps. **/
    protected int rootGrowthInterval;
    /** A plant may grow once every X steps. **/
    protected int plantGrowthInterval;

    protected int rootGrowthTickCount = 0;

	public Plant(MainSimulation sim, Entity flow, int depth, int row, int col) {
		super(sim,flow,depth,row,col);
		nutrientsFrom = new ArrayList<>();
		name = "PLANT";
		symbol = 'P';

		//A plant must be placed above Dirt
		Entity e = checkAdjacent(AdjacentEntities.DOWN,row,col);
		if (!(e instanceof Dirt)) {
			simulation.setEntity(row, col, new Air(sim,flow,0,row,col));
			}
		else {
			//generate initial root
			int newRow = row + 1;
			Root r = new Root(sim,this, depth + 1, newRow, col);
			simulation.setEntity(newRow, col, r);
			nutrientsFrom.add(r);
		}

		/*
		Entity left = checkAdjacent(AdjacentEntities.LEFT,row,col);
		if (left instanceof Air) {
			int newCol = col - 1;
			simulation.setEntity(row, newCol, new Leaf(sim, this, depth + 1, row, newCol));
		}
		*/
	}

	public abstract ArrayList<Entity> die();
	public abstract void growPlant();
	public abstract void growLeaf();
	public abstract void growRoot();
	public abstract void growFruit();
	/** Each type of plant may have unique behavior of root growth.
	 *  This method returns to the MainSimulation true/false if can grow. */
	public abstract boolean canGrowRoot();

	/**
	 * @return	returns TRUE if this plant-stem has the availability to grow
	 * 			a fruit to its left or right leaf.
	 *
	 * 			- Checks for the plant-entity's depth
	 * 			- Checks for the plant-entity's nutrient-level
	 * 			- Checks for a left or right leaf
	 */
	public boolean canGrowFruit() {
		// return false;
		return (depth > 2 && nutrients >= fruitCreationThreshold
				&& /*((checkAdjacent(AdjacentEntities.RIGHT, row, col) instanceof Leaf) ||*/ (checkAdjacent(AdjacentEntities.LEFT, row, col) instanceof Leaf));
	}

    /** Allows for root growth if nutrients are available and stem isn't at max depth. */
    public boolean canGrowPlant() {
        return (getFlowTo() == null &&
                nutrients >= growPlantRequirement &&
                lifeSteps % plantGrowthInterval == 0 &&
                depth < maxStemDepth-1);
    }

    public abstract boolean canGrowLeaf();
}
