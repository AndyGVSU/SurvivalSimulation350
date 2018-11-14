package simulation;

import java.awt.Color;

public class Grass extends Plant {
	
	private final int SURVIVAL_REQ = 3;
	
	private final int PLANT_REQ = 20;
	private final int ROOT_REQ = 5;
	private final int LEAF_REQ = 10;
	

	public Grass(MainSimulation sim, Entity parent, int depth, int row, int col) {
		super(sim, parent, depth, row, col);
		name = "GRASS";
		symbol = 'G';
		survivalRequirement = SURVIVAL_REQ;
		growPlantRequirement = PLANT_REQ;
		growLeafRequirement = LEAF_REQ;
		growRootRequirement = ROOT_REQ;
		
		
		color = Color.GREEN;

	}
	

	@Override
	public void growPlant() {
		
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
	public void growRoot() {
		int tempRow = row;
		
		while (tempRow < simulation.getRows()-1) {
			
			System.out.println("Plant s");
			System.out.println("Row: "+ tempRow);
			System.out.println("Col: " + col);
			
			if (checkAdjacent(AdjacentEntities.DOWN, tempRow, col) instanceof Dirt) {

				Root r = new Root(simulation, simulation.getEntity(tempRow-1, col), depth+1, tempRow + 1, col);
				simulation.setEntity(tempRow + 1, col, r);
				break;	
			}
			tempRow++;
		}

	}
}
