package simulation;

public abstract class Collector extends Entity {
    public Collector(MainSimulation sim, Entity flow, int depth, int row, int col) {
        super(sim,flow,depth,row,col);
    }

}
