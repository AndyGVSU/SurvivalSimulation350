package simulation;

public class Dirt extends Entity {

	public Dirt(int nutrients, int height, int width, int row, int col, int entityID) {
		super(nutrients,height,width,row,col,entityID);
		name = "DIRT";
		symbol = 'D';
	}
}
