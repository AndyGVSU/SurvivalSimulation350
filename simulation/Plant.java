package simulation;

import java.util.ArrayList;

public abstract class Plant extends Entity {
	protected ArrayList<Collector> nutrientsFrom;

    int growPlantRequirement;
    int growLeafRequirement;
    int growRootRequirement;

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
	/** Each type of plant may have unique behavior of root growth.
	 *  This method returns to the MainSimulation true/false if can grow. */
	public abstract boolean canGrowRoot();

    /** Allows for root growth if nutrients are available and stem isn't at max depth. */
    public boolean canGrowPlant() {
        return (getFlowTo() == null &&
                nutrients >= growPlantRequirement &&
                lifeSteps % plantGrowthInterval == 0 &&
                depth < maxStemDepth-1);
    }

    public boolean canGrowLeaf() {
        return (nutrients >= growLeafRequirement);

    }
}
