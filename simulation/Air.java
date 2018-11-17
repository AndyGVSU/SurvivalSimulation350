package simulation;

public class Air extends Entity {

	public Air(MainSimulation sim, Entity flow, int depth, int row, int col) {
		super(sim,flow,depth,row,col);
		name = "AIR";
		symbol = '.';
		color = 1;
	}

	public void doStep() { }
}
