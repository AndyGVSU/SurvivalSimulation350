package simulation;

public class Grass extends Plant {
    private final int GROWTH_REQ = 3;

    public Grass(MainSimulation sim, Entity parent, int depth, int row, int col) {
        super(sim,parent,depth,row,col);
        name = "GRASS";
        symbol = 'g';
        nutrientGrowthRequirement = GROWTH_REQ;
    }

    @Override
    public void grow() {
        if (checkAdjacent(AdjacentEntities.UP, row, col) instanceof Air) {
            Grass g = new Grass(simulation,null, depth + 1, row - 1, col);
            this.setParent(g);
            //Leaf l = new Leaf(simulation, this, depth + 1, row, col - 1);
            simulation.setEntity(row - 1,col,g);
        }
    }

    @Override
    public void growLeaf() {

    }

    @Override
    public void growRoot() {

    }
}
