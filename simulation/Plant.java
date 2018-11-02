package simulation;


public class Plant extends Entity {

	public Plant(MainSimulation sim, int nutrients, int height, int width, int row, int col, int entityID) {
		super(sim,nutrients,height,width,row,col,entityID);
		name = "PLANT";
		symbol = 'P';
	}

	public void doStep() {
		super.doStep();
		if(lifeSteps % 2 == 0) {
			System.out.println("Plant: " + entityID + " Lifesteps: " + lifeSteps + " Height: " + super.height);
		}
	}
}
