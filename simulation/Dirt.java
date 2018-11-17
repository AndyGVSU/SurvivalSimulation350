package simulation;

public class Dirt extends Entity {

	public Dirt(MainSimulation sim, Entity flow, int depth, int row, int col) {
		super(sim,flow,depth,row,col);
		name = "DIRT";
		symbol = 'D';
		color = 2;
	}
}
