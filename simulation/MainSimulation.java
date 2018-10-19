package simulation;

import java.util.ArrayList;
import java.util.Random;

public class MainSimulation {
    private int rows;
    private int columns;
    private boolean playing;
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

    // Changes the simulation up for a sample run. After initialization. #Brendon
    public void setDefaultOne(int r, int c) {
        rows = r;
        columns = c;

        // Setting the air entities
        for (int i = 0; i < rows - 5; i++) // Leave the bottom 5 for dirt
            for (int j = 0; j < columns; j++)
                entityGrid[i][j] = new Air(0,0,0);

        // Setting the bottom dirt layers
        for (int i = rows - 5; i < rows; i++)
            for (int j = 0; j < columns; j++)
                entityGrid[i][j] = new Dirt(0,0,0);

        // Set some default plants
        int numberOfPlants = 3;
        Random rand = new Random();
        int Low = 0;
        int High = columns;
        for(int i = 0; i < numberOfPlants; i++) {
            while(true) {
                int Result = rand.nextInt(High-Low) + Low;
                if (!(entityGrid[rows - 6][Result] instanceof Plant)) {
                    entityGrid[rows - 6][Result] = new Plant(0,0,0);
                    break;
                }
            }
        }
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
    public void setPlaying(boolean play) {playing = play;}
    public boolean getPlaying() {return playing;}
    public void stepForward() {}
    public void stepBackward() {}
    public void save() {};
    public void load() {};

}
