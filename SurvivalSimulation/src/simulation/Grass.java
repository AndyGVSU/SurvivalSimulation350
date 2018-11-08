package simulation;

import java.awt.Color;

public class Grass extends Plant {
    private final int GROWTH_REQ = 3;

    public Grass(MainSimulation sim, Entity parent, int depth, int row, int col) {
        super(sim,parent,depth,row,col);
        name = "GRASS";
        symbol = 'G';
        nutrientGrowthRequirement = GROWTH_REQ;
        color = Color.GREEN;
        
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
    	if (checkAdjacent(AdjacentEntities.LEFT, row, col -1 ) instanceof Air) {
    		if (checkAdjacent(AdjacentEntities.RIGHT, row, col+1) instanceof Air) {
    			
            Leaf l = new Leaf(simulation,null, depth , row , col+1);
            this.setParent(l);
            //Leaf l = new Leaf(simulation, this, depth + 1, row, col - 1);
            simulation.setEntity(row,col + 1,l);
            
    		}
        }
    	

    }

    @Override
    public void growRoot() {

    }
}
