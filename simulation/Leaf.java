package simulation;

public class Leaf extends Collector {
    public Leaf(MainSimulation sim, Entity flow, int depth, int row, int col) {
        super(sim,flow,depth,row,col);
        name = "LEAF";
        symbol = 'L';
        color = 3;
        
    }
}
