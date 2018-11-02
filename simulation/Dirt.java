package simulation;

public class Dirt extends Entity {

	public Dirt(MainSimulation sim, int nutrients, int height, int width, int row, int col, int entityID) {
		super(sim, nutrients,height,width,row,col,entityID);
		name = "DIRT";
		symbol = 'D';
	}

	public void doStep() { }
}
