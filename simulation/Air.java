package simulation;

public class Air extends Entity {

	public Air(MainSimulation sim, int nutrients, int height, int width, int row, int col, int entityID) {
		super(sim, nutrients,height,width,row,col,entityID);
		name = "AIR";
		symbol = '.';
	}

	public void doStep() { }
}
