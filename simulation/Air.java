package simulation;

public class Air extends Entity {

	public Air(int nutrients, int height, int width, int row, int col, int entityID) {
		super(nutrients,height,width,row,col,entityID);
		name = "AIR";
		symbol = '.';
	}
}
