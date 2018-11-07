package simulation;

public class Root extends Collector {
    public Root(MainSimulation sim, Entity parent, int depth, int row, int col) {
        super(sim,parent,depth,row,col);
        name = "ROOTS";
        symbol = 'R';
    }
}
