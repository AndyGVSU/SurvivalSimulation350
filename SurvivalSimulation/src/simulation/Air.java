package simulation;

import java.awt.Color;

public class Air extends Entity {

	public Air(MainSimulation sim, Entity parent, int depth, int row, int col) {
		super(sim,parent,depth,row,col);
		name = "AIR";
		symbol = '.';
		color = Color.cyan;
	}

	public void doStep() { }
}
