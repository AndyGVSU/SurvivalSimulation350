package simulation;

public class Air extends Entity {

	public Air(MainSimulation sim, Entity flow, int depth, int row, int col) {
		super(sim,flow,depth,row,col);
		name = "AIR";
		symbol = '.';
		this.setColor(1);
	}

	public void doStep() { }
}
