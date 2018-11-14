package simulation;

public class Air extends Entity {

	public Air(MainSimulation sim, Entity parent, int depth, int row, int col) {
		super(sim,parent,depth,row,col);
		name = "AIR";
		symbol = '.';
		color = 1;
	}

	public void doStep() { }
}
