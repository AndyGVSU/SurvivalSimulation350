package simulation;

import java.awt.Color;

public abstract class Plant extends Entity {

	public Plant(MainSimulation sim, Entity parent, int depth, int row, int col) {
		super(sim,parent,depth,row,col);
		name = "PLANT";
		symbol = 'P';
		color = Color.GREEN;
		

		//A plant must be placed above Dirt
		Entity e = checkAdjacent(AdjacentEntities.DOWN,row,col);
		if (!(e instanceof Dirt)) {
			simulation.setEntity(row, col, new Air(sim,parent,0,row,col));
			}
		else {
			int newRow = row + 1;
			simulation.setEntity(newRow, col, new Root(sim,this, depth + 1, newRow, col));
		}

		/*
		Entity left = checkAdjacent(AdjacentEntities.LEFT,row,col);
		if (left instanceof Air) {
			int newCol = col - 1;
			simulation.setEntity(row, newCol, new Leaf(sim, this, depth + 1, row, newCol));
		}
		*/
	}

	public abstract void growPlant();
	public abstract void growLeaf();
	public abstract void growRoot();
}
