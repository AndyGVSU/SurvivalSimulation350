package simulation;

public class Root extends Collector {
    public Root(MainSimulation sim, Entity flow, int depth, int row, int col) {
        super(sim,flow,depth,row,col);
        name = "ROOTS";
        symbol = 'R';
        color = 4;
        survivalRequirement = 2;
    }
}
