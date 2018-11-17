package simulation;

import java.util.ArrayList;

public class Grass extends Plant {

	/** Number of nutrients used to live another step. Should be low. */
	private final int SURVIVAL_REQ = 1;
	/** Number of nutrients used to grow another plant-stem. */
	private final int PLANT_REQ = 20;
	/** Number of nutrients used to grow another root. */
	private final int ROOT_REQ = 5;
	/** Number of nutrients used to create another leaf. */
	private final int LEAF_REQ = 10;
	/** Maximum depth of plant blocks. **/
	private final int STEM_DEPTH = 5;
	/** Maximum number of roots. **/
	private final int MAX_ROOTS = 11;
	/** Time in steps between root growth attempts. **/
	private final int ROOT_INTERVAL = 4;
	/** Time in steps between plant growth attempts. **/
	private final int PLANT_INTERVAL = 5;

	/** Is this stem, the top one? Should grown be applied from here?
	 *  When creating a new stem, make the old one false and new one true. */
	private boolean isTopStem = true;

	public Grass(MainSimulation sim, Entity flow, int depth, int row, int col) {
		super(sim, flow, depth, row, col);
		name = "GRASS";
		symbol = 'G';

		// These are currently used in MainSimulation() to check for possible growth.
		survivalRequirement = SURVIVAL_REQ;
		growPlantRequirement = PLANT_REQ;
		growLeafRequirement = LEAF_REQ;
		growRootRequirement = ROOT_REQ;

		maxStemDepth = STEM_DEPTH;
		maxRoots = MAX_ROOTS;
		rootGrowthInterval = ROOT_INTERVAL;
		plantGrowthInterval = PLANT_INTERVAL;

		color = 0;
	}



	/** Allows for root growth if nutrients are available and roots aren't at max. */
	public boolean canGrowRoot() {
		// Specifies the original-grass as responsible for this logic.
		if (depth == 0 &&
				nutrients >= growRootRequirement &&
				rootsGrown < maxRoots - 1 &&
				rootGrowthTickCount >= rootGrowthInterval) {
			rootsGrown++;
			rootGrowthTickCount = 0;
			return true;
		} else {
			rootGrowthTickCount++;
			return false;
		}
	}

	public boolean canGrowLeaf() {
		Entity left = checkAdjacent(AdjacentEntities.LEFT,row,col);
		Entity right = checkAdjacent(AdjacentEntities.RIGHT,row,col);
		return (nutrients >= growLeafRequirement &&
				(left instanceof Air && left.getNutrients() > 0) ||
				 right instanceof Air && right.getNutrients() > 0);
	}

	@Override
	public void growPlant() {
		// Which "grass" is this being called on? Debugging.
		/*
		System.out.println("Grass");
		System.out.println("  Row: " + row);
		System.out.println("  Col: " + col);
		System.out.println("  Depth: " + depth);
		*/

		if (checkAdjacent(AdjacentEntities.UP, row, col) instanceof Air) {
			Grass g = new Grass(simulation, null, depth + 1, row - 1, col);
			this.setFlowTo(g);
			simulation.setEntity(row - 1, col, g);
		}
	}
	//Leafs can't grow leaves
	@Override
	public void growLeaf() {
		
		if (checkAdjacent(AdjacentEntities.LEFT, row, col) instanceof Air) {

			Leaf l = new Leaf(simulation, this, depth+1, row, col - 1);
			nutrientsFrom.add(l);
			simulation.setEntity(row, col - 1, l);
		}

		else if (checkAdjacent(AdjacentEntities.RIGHT, row, col) instanceof Air) {

			Leaf l = new Leaf(simulation, this, depth+1, row, col + 1);
			nutrientsFrom.add(l);
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
	 *  below this grass entity. Brendon. **/
	public void growRoot() {
		int tempRow = row;

		// Which "grass" is this being called on? Debugging.
		// System.out.println("Grass");
		// System.out.println("  Row: " + tempRow);
		// System.out.println("  Col: " + col);
		// System.out.println("  Depth: " + depth);

		while (tempRow < simulation.getRows()-1) {
			// Check for dirt spots below the depth-zero grass to add another root.
			if(checkAdjacent(AdjacentEntities.DOWN, tempRow, col) instanceof Dirt) {
        
				Root r = new Root(simulation, simulation.getEntity(tempRow, col), tempRow - row + 1, tempRow + 1, col);
				//System.out.println("DOWNROOT");
				//System.out.println(r.getRow() + " - " + r.getColumn() + " Current Position");
				//System.out.println(r.getParent().getRow() + " - " + r.getParent().getColumn() + " Parents Position");
				// System.out.println(r.getParent().getParent().getRow() + " - " + r.getParent().getParent().getColumn() + " Parents Parents Row");
				simulation.setEntity(tempRow + 1, col, r);
				nutrientsFrom.add(r);
				break;
			}

			if(checkAdjacent(AdjacentEntities.RIGHT, tempRow, col) instanceof Dirt && tempRow > row + 1){

				Root r = new Root(simulation, simulation.getEntity(tempRow, col), tempRow - row + 1, tempRow , col+1);
				//System.out.println("RIGHTROOT");
				//System.out.println(r.getRow() + " - " + r.getColumn() + " Current Position");
				//System.out.println(r.getParent().getRow() + " - " + r.getParent().getColumn() + " Parents Position");
				//System.out.println(r.getParent().getParent().getRow() + " - " + r.getParent().getParent().getColumn() + " Parents Parents Row");
				simulation.setEntity(r.getRow(), r.getColumn(), r);
				nutrientsFrom.add(r);
				break;
			}

			if(checkAdjacent(AdjacentEntities.LEFT, tempRow, col) instanceof Dirt && tempRow > row + 1){
				Root r = new Root(simulation, simulation.getEntity(tempRow, col), tempRow - row + 1, tempRow, col-1);
				//System.out.println("LEFTROOT");
				//System.out.println(r.getRow() + " - " + r.getColumn() + " Current Position");
				//System.out.println(r.getParent().getRow() + " - " + r.getParent().getColumn() + " Parents Position");
				//System.out.println(r.getParent().getParent().getRow() + " - " + r.getParent().getParent().getColumn() + " Parents Parents Row");
				simulation.setEntity(tempRow , col-1, r);
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
		Entity e = checkAdjacent(AdjacentEntities.LEFT, row, col);
        if (e instanceof Leaf) {
            Fruit f = new Fruit(simulation, simulation.getEntity(row, col),
                    depth + 1, row , col - 1);
            simulation.setEntity(row, col-1, f);
            nutrientsFrom.remove(e);
        }
	}

	public ArrayList<Entity> die() {
		ArrayList<Entity> deletedPlants = new ArrayList<>();

		if(getFlowTo() instanceof Plant) {
			deletedPlants.addAll(((Plant) getFlowTo()).die());
        }
	    for(Collector c : nutrientsFrom) {
	    	Entity replace = null;
	    	if (c instanceof Leaf) {
				replace = new Air(simulation, null, 0, c.getRow(), c.getColumn());
			}
	    	else if (c instanceof Root) {
				replace = new Dirt(simulation, null, 0, c.getRow(), c.getColumn());
			}
			simulation.setEntity(c.getRow(), c.getColumn(), replace);
        }
        simulation.setEntity(getRow(), getColumn(),
			new Air(simulation, null, getDepth(), getRow(), getColumn()));

	    deletedPlants.add(this);
	    return deletedPlants;
    }
}
