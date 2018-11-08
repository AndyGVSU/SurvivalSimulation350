package simulation;

import java.awt.Color;

public class Dirt extends Entity {

	public Dirt(MainSimulation sim, Entity parent, int depth, int row, int col) {
		super(sim,parent,depth,row,col);
		name = "DIRT";
		symbol = 'D';
		color = Color.ORANGE;
	}
}