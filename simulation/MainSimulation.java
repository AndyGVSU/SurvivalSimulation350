package simulation;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class MainSimulation {
    private int rows;
    private int columns;
    private boolean playing;
    private Entity[][] entityGrid;
    private ArrayList<EntityType[]> usedEntities;
    private int speed = 50; //in deciseconds TODO Temp as "50" Brendon
    private int[] speedRange;
    private Environment environment;

    // For updating existing plants with doStep().
    private Entity[] plantsArray;
    private int plantCount = 0;
    private int step = 0; // steps to iterate as "time"
    private int entityIDs = 0; // IDs to give to each entity.

    public MainSimulation(int r, int c) {
        rows = r;
        columns = c;
        entityGrid = new Entity[rows][columns];
        plantsArray = new Entity[rows * columns];
        speedRange = new int[]{0,50};
        environment = new Environment();

        /*
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                entityGrid[i][j] = new Plant(this, 0, 0, 0, i, j, entityIDs);
                entityIDs = entityIDs + 1;
            }
        */
    }

    // Changes the simulation up for a sample run. After initialization. #Brendon
    public void setDefaultOne(int r, int c) {
        rows = r;
        columns = c;

        // Setting the air entities
        for (int i = 0; i < rows - 5; i++) // Leave the bottom 5 for dirt
            for (int j = 0; j < columns; j++) {
                entityGrid[i][j] = new Air(this, 0, 0, 0, i, j, entityIDs);
                entityIDs = entityIDs + 1;
            }

        // Setting the bottom dirt layers
        for (int i = rows - 5; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                entityGrid[i][j] = new Dirt(this, 0, 0, 0, i, j, entityIDs);
                entityIDs = entityIDs + 1;
            }

        // Set some default plants
        int numberOfPlants = 3;
        Random rand = new Random();
        int Low = 0;
        int High = columns;
        for(int i = 0; i < numberOfPlants; i++) {
            while(true) {
                int Result = rand.nextInt(High-Low) + Low;
                if (!(entityGrid[rows - 6][Result] instanceof Plant)) {
                    entityGrid[rows - 6][Result] = new Grass(this, 20,0,0, rows - 6, Result, entityIDs);
                    entityIDs = entityIDs + 1;
                    plantsArray[plantCount] = entityGrid[rows - 6][Result];
                    plantCount = plantCount + 1;
                    break;
                }
            }
        }
    }

    public Entity getEntity(int row, int col){
        if (row < 0 || row > rows || col < 0 || col > columns)
            return null;
        return entityGrid[row][col];
    }
    public Entity setEntity(int row, int col, Entity e) {
        entityGrid[row][col] = e;
        return e;
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
    public void setPlaying(boolean play) {playing = play;}
    public boolean getPlaying() {return playing;}
    public void stepForward() {
        step++;
        // For each plant, call doStep().
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                entityGrid[i][j].doStep();
            }
        }
    }
    public void stepBackward() {}
    public void save() {};
    public void load() {};
}
