package simulation;

public class Grass extends Plant {

    public Grass(MainSimulation sim, int nutrients, int height, int width, int row, int col) {
        super(sim, nutrients, height, width, row, col);
        name = "GRASS";
        symbol = 'g';
        nutrientGrowthRequirement = 10;
    }
    public void doStep() {
        super.doStep();
        if (nutrients >= nutrientGrowthRequirement) {
            if (checkAdjacent(AdjacentEntities.UP, row, col) instanceof Air) {
                Leaf l = new Leaf(simulation, 0, height, width, row, col - 1);
                simulation.setEntity(row - 1,col,l);
            }
        }
    }

}
