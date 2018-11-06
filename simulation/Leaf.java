package simulation;

public class Leaf extends Entity {
    public Leaf(MainSimulation sim, int nutrients, int height, int width, int row, int col) {
        super(sim, nutrients, height, width, row, col);
        name = "LEAF";
        symbol = 'l';
    }
}
