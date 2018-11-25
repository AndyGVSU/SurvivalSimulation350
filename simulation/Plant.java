package simulation;

import java.util.ArrayList;

/**
 *  The plant-entity is initially created/set  by the system to the grid.
 *  The plant-entity is extended by a unique "species" like the grass-entity.
 */
public abstract class Plant extends Entity {
	/** A list of the collector-entities that feed to this entity. */
	protected ArrayList<Collector> nutrientsFrom;

	public void addToNutrientsFrom(final Collector c) {
		nutrientsFrom.add(c);
    }

    public void removeNutrientsFrom(final Entity c) {
		nutrientsFrom.remove(c);
	}

	// TODO - Make this private with accessors, says CheckStyle
	// To make these private conflicts with the way grass?..
    /** The nutrients required to grow another plant-entity. */
	public int growPlantRequirement;
	/** The nutrients required to grow a leaf-entity from this entity. */
    public int growLeafRequirement;
    /** The nutrients required to grow a root-entity from the plant. */
    public int growRootRequirement;
    /** The nutrients required to create a single fruit on the plant. */
	private final int fruitCreationThreshold = maxNutrients - 200;

	/** A plant stem may grow to this height. */
	protected int maxStemDepth;
    /** Number of roots grown. Keeps track for plant-unique max roots. **/
    protected int rootsGrown = 0;
    /** Max number of roots for this particular plant. **/
    protected int maxRoots;
    /** A root may grow once every X steps. **/
    protected int rootGrowthInterval;
    /** A plant may grow once every X steps. **/
    protected int plantGrowthInterval;
	/** A counter for limiting the root growth. */
    protected int rootGrowthTickCount = 0;

    /** Is this stem, the top one? Should fruit be grown from here?
     *  When creating a new stem, make the old one false and
     *  new one true. */
    protected boolean isTopStem = true; // Unimplemented - Brendon Nov 18
    /** The number of fruits a plant-entity can create in its lifetime. */
    protected final int maxFruitsProducable = 2;
    /** The number of fruits a plant has created. */
    protected int fruitsProduced = 0;
	/** The number of ticks to create a fruit & between fruits. */
    protected int fruitCreationDelayTime = 5;
    /** The number of ticks since the last fruit produced. */
    protected int tickOfLastFruitMade = 0;


	/**
	 * The contructor method of the plant-entity. This sets GUI colors and
	 * letters. To be extended by unique species' class, like the
	 * grass-entity.
	 * @param sim   The simulation object that contains the entire system.
	 * @param flow  A "copy" of the entity that this was born from.
	 * @param depth The distance of this entity from the original plant
	 *              entity.
	 * @param row   The "y" coordinate of this entity.
	 * @param col   The "x" coordinate of this entity.
	 */
	public Plant(final MainSimulation sim, final Entity flow,
		  final int depth, final int row, final int col) {
		super(sim, flow, depth, row, col);
		nutrientsFrom = new ArrayList<>();
		name = "PLANT";
		symbol = 'P';

		if (sim != null) {
			// A plant must be placed above dirt-entity
			Entity e = checkAdjacent(AdjacentEntities.DOWN, row, col);
			if (!(e instanceof Dirt)) {
				simulation.setEntity(row, col, new Air(sim, flow,
						0, row, col));
			} else {
				// Generate initial root
				int newRow = row + 1;
				Root r = new Root(sim, this, depth + 1, newRow, col);
				simulation.setEntity(newRow, col, r);
				nutrientsFrom.add(r);
			}
		}
	}

	/**
	 * Initiates the death of the plant AND returns the dead entities.
	 * @return	An ArrayList of the now-dead entities
	 */
	public abstract ArrayList<Entity> die();
	/** Grow a plant-entity off of the current entity. */
	public abstract void growPlant();
	/** Grow a leaf-entity off of the current entity. */
	public abstract void growLeaf();
	/** Gorw a root-entity for the plant. */
	public abstract void growRoot();
	/** Grow a fruit-entity off of the corrent entity. */
	public abstract void growFruit();

	/**
	 * This method controlls if a plant can grow a root. Each "species"
	 * of plant can have its own defined behaviors.
	 * @return	TRUE if the plant can grow another root
	 */
	public abstract boolean canGrowRoot();

	/**
	 * @return	returns TRUE if this plant-stem has the availability
	 * 			to grow a fruit to its left or right leaf.
	 *
	 * 			- Checks for the plant-entity's depth
	 * 			- Checks for the plant-entity's nutrient-level
	 * 			- Checks for a left leaf
     * 		    - Checks for the plant-entity as a top plant
     * 		    - Checks that the plant has grown less than max fruits
	 */
	public boolean canGrowFruit() {
		// return false;
        if (depth > 2 && nutrients >= fruitCreationThreshold
                &&  (checkAdjacent(AdjacentEntities.LEFT, this.getRow(), this.getColumn())
                instanceof Leaf)
                && fruitsProduced < maxFruitsProducable
                && isTopStem
				&& (simulation.getCurrentStep() - tickOfLastFruitMade) >= fruitCreationDelayTime
				&& lifeSteps > fruitCreationDelayTime) {

            System.out.println("Growing Fruit - r" + this.getRow() + " - c" + this.getColumn());
            System.out.println("  Fruit Produced: " + fruitsProduced);
            if (isTopStem) {
                System.out.println("  Is Top Stem!");
            } else {
                System.out.println("  Is Not Top Stem!");
            }
			System.out.println("  Lifetime: " + lifeSteps);
			System.out.println("  LastFruit Time: " + tickOfLastFruitMade);
        }

		return (depth > 2 && nutrients >= fruitCreationThreshold
				&&  (checkAdjacent(AdjacentEntities.LEFT, this.getRow(), this.getColumn())
				instanceof Leaf)
				&& fruitsProduced < maxFruitsProducable
				&& isTopStem
				&& (simulation.getCurrentStep() - tickOfLastFruitMade) >= fruitCreationDelayTime
				&& lifeSteps > fruitCreationDelayTime);
	}

	/**
	 * Allows for root growth if nutrients are available and
	 * stem isn't at max depth.
	 * @return	TRUE if the current plant-entity can grow another
	 */
	public boolean canGrowPlant() {
        return (getFlowTo() == null
				&& nutrients >= growPlantRequirement
				&& lifeSteps % plantGrowthInterval == 0
				&& depth < maxStemDepth - 1);
    }

	/**
	 * Allows for a current plant-entity to grow its own
	 * left and right leaves.
	 * @return	TRUE if the current plant-entity has the correct
	 * 			conditions to grow a leaf.
	 */
	public abstract boolean canGrowLeaf();
}
