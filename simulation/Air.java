package simulation;

public class Air extends Entity {

	public Air(MainSimulation sim, int nutrients, int height, int width, int row, int col) {
		super(sim, nutrients,height,width,row,col);
		name = "AIR";
		symbol = '.';
	}

	public void doStep() { }
}
