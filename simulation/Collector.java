package simulation;

public abstract class Collector extends Entity {
    public Collector(MainSimulation sim, Entity parent, int depth, int row, int col) {
        super(sim,parent,depth,row,col);
    }

}
