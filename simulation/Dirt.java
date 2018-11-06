package simulation;

public class Dirt extends Entity {

	public Dirt(MainSimulation sim, int nutrients, int height, int width, int row, int col) {
		super(sim, nutrients,height,width,row,col);
		name = "DIRT";
		symbol = 'D';
	}

	public void doStep() { }
}
