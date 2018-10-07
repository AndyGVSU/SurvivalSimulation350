package simulation;

import java.util.ArrayList;

public class MainSimulation {
    private int rows;
    private int columns;
    private Entity[][] entityGrid;
    private ArrayList<EntityType[]> usedEntities;
    private int speed; //in deciseconds
    private int[] speedRange;
    private Environment environment;

    public MainSimulation(int r, int c) {
        rows = r;
        columns = c;
        entityGrid = new Entity[rows][columns];
        speedRange = new int[]{0,50};
        environment = new Environment();

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                entityGrid[i][j] = new Plant(0,0,0);
    }

    public Entity getEntity(int row, int col){
        return entityGrid[row][col];
    }

    public int getRows(){
        return rows;
    }
    public int getColumns(){
        return columns;
    }
    public int[] getSpeedRange() { return speedRange; }
    public Environment getEnvironment() {return environment; }

    public int getSpeed() {return speed;}
    public int setSpeed(int s) {speed = s; return speed;}


}
