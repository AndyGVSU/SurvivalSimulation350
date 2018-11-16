package simulation;

public class Grass extends Plant {

	/** Number of nutrients used to live another step. Should be low. */
	private final int SURVIVAL_REQ = 1;
	/** Number of nutrients used to grow another plant-stem. */
	private final int PLANT_REQ = 20;
	/** Number of nutrients used to grow another root. */
	private final int ROOT_REQ = 5;
	/** Number of nutrients used to create another leaf. */
	private final int LEAF_REQ = 10;

	/** The furthest a stem is allowed to be from the "seed". This is "max-height". */
	private final int maxStemDepth = 5;

	/** Number of roots grown. Keeps track for plant-unique max roots. */
	private int rootsGrown = 0;
	/** Max number of roots for this particular plant. */
	private final int maxRoots = 3;
	/** A root may be grown only within every X ticks. */
	private final int rootGrowthTimer = 5;
	private int rootGrowthTickCount = 0;

	/** Is this stem, the top one? Should grown be applied from here?
	 *  When creating a new stem, make the old one false and new one true. */
	private boolean isTopStem = true;

	/** Number of lifesteps needed before growing further. */
	private final int lifestepsNeededToGrow = 5;
	/** Has this stem grown another stem? */
	private boolean hasGrownPlant = false;

	public Grass(MainSimulation sim, Entity parent, int depth, int row, int col) {
		super(sim, parent, depth, row, col);
		name = "GRASS";
		symbol = 'G';

		// These are currently used in MainSimulation() to check for possible growth.
		survivalRequirement = SURVIVAL_REQ;
		growPlantRequirement = PLANT_REQ;
		growLeafRequirement = LEAF_REQ;
		growRootRequirement = ROOT_REQ;

		color = 0;
	}

	/** Allows for root growth if nutrients are available and stem isn't at max depth. */
	@Override
	public boolean canGrowPlant() {
		if (nutrients >= growPlantRequirement && lifeSteps >= lifestepsNeededToGrow && hasGrownPlant == false && depth < maxStemDepth-1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void growPlant() {
		hasGrownPlant = true;

		// Which "grass" is this being called on? Debugging.
		System.out.println("Grass");
		System.out.println("  Row: " + row);
		System.out.println("  Col: " + col);
		System.out.println("  Depth: " + depth);

		if (checkAdjacent(AdjacentEntities.UP, row, col) instanceof Air) {
			Grass g = new Grass(simulation, null, depth + 1, row - 1, col);
			this.setParent(g);
			// Leaf l = new Leaf(simulation, this, depth + 1, row, col - 1);
			simulation.setEntity(row - 1, col, g);
		}
	}
	//Leafs can't grow leaves
	@Override
	public void growLeaf() {
		
		if (checkAdjacent(AdjacentEntities.LEFT, row, col) instanceof Air) {

			Leaf l = new Leaf(simulation, this, depth+1, row, col - 1);
			simulation.setEntity(row, col - 1, l);
		}

		else if (checkAdjacent(AdjacentEntities.RIGHT, row, col) instanceof Air) {

			Leaf l = new Leaf(simulation, this, depth+1, row, col + 1);
			simulation.setEntity(row, col + 1, l);

		}
	}

	@Override
	// Brendon - Root growth is largely at the surface and wide.
	// 			 Root growth seems largely blasted at first then slowly expands.
	//             faster than stem at first, then stem uses root's nutrients to
	//             grow.
	// https://www.youtube.com/watch?v=w77zPAtVTuI
	// https://content.ces.ncsu.edu/print_image/6419
	// https://content.ces.ncsu.edu/extension-gardener-handbook/11-woody-ornamentals
	/** Called on the "grass" entity at depth == 0. This grows a root somewhere
	 *  below this grass entity. Brendon. */
	public void growRoot() {
		int tempRow = row;

		// Which "grass" is this being called on? Debugging.
		// System.out.println("Grass");
		// System.out.println("  Row: " + tempRow);
		// System.out.println("  Col: " + col);
		// System.out.println("  Depth: " + depth);

		while (tempRow < simulation.getRows()-1) {
			// Check for dirt spots below the depth-zero grass to add another root.
			if (checkAdjacent(AdjacentEntities.DOWN, tempRow, col) instanceof Dirt) {
				Root r = new Root(simulation, simulation.getEntity(tempRow-1, col), rootsGrown+1, tempRow + 1, col);
				simulation.setEntity(tempRow + 1, col, r);
				break;	
			}
			tempRow++;
		}
	}

	/** Allows for root growth if nutrients are available and roots aren't at max. */
	public boolean canGrowRoot() {
		if (nutrients >= growRootRequirement && rootsGrown < maxRoots-1 && rootGrowthTickCount >= rootGrowthTimer) {
			rootsGrown++;
			rootGrowthTickCount = 0;
			return true;
		} else {
			rootGrowthTickCount++;
			return false;
		}
	}
}
