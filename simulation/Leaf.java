package simulation;

public class Leaf extends Entity {
    public Leaf(MainSimulation sim, int nutrients, int height, int width, int row, int col, int entityID) {
        super(sim, nutrients, height, width, row, col, entityID);
        name = "LEAF";
        symbol = 'l';
    }
}
