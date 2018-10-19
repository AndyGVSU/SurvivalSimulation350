package simulation;

public class Plant extends Entity {

	private int lifeSteps = 0; // Lifetime in "steps."

	public Plant(int nutrients, int height, int width, int row, int col, int entityID) {
		super(nutrients,height,width,row,col,entityID);
		name = "PLANT";
		symbol = 'P';
	}

	public void doStep() {
		if(lifeSteps % 2 == 0 && super.height < 10) {
			super.height = super.height + 1;
			System.out.println("Plant: " + entityID + "Lifesteps: " + lifeSteps + "Height: " + super.height);
			lifeSteps = lifeSteps + 1;
		}
	}
}
