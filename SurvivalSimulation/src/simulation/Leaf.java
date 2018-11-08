package simulation;

import java.awt.Color;

public class Leaf extends Collector {
    public Leaf(MainSimulation sim, Entity parent, int depth, int row, int col) {
        super(sim,parent,depth,row,col);
        name = "LEAF";
        symbol = 'L';
        color = Color.green;
        
    }
}
