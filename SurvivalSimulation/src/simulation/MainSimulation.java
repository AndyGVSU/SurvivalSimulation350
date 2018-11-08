package simulation;

import java.util.ArrayList;
import java.util.Random;

public class MainSimulation {
    final private int NUTRIENTS_SUNLIGHT_DIMINISH = 5;
    final private int NUTRIENTS_ROOT_BASE = 10;
    private int rows;
    private int columns;
    private boolean playing;
    private Entity[][] entityGrid;
    private int speed = 50; //in deciseconds TODO Temp as "50" Brendon
    private int[] speedRange;
    private Environment environment;

    /** Keeps track of plant dependencies */
    private ArrayList<ArrayList<Entity>> depthPlant;
    private ArrayList<ArrayList<Entity>> depthCollector;

    // For updating existing plants with doStep().
    private Entity[] plantsArray;
    private int plantCount = 0;
    private int step = 0; // steps to iterate as "time"
    private int entityIDs = 0; // IDs to give to each entity.

    public MainSimulation(int r, int c) {
        rows = r;
        columns = c;
        entityGrid = new Entity[rows][columns];
        speedRange = new int[]{0,100};
        environment = new Environment();
        depthPlant = new ArrayList<>();
        depthCollector = new ArrayList<>();

        playing = false;
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
                setEntity(i,j,new Air(this, null, 0, i, j));
            }

        // Setting the bottom dirt layers
        for (int i = rows - 5; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                setEntity(i,j,entityGrid[i][j] = new Dirt(this, null, 0, i, j));
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
                	
                    setEntity(rows - 6, Result, new Grass(this,null,0, rows - 6, Result));
                    entityIDs = entityIDs + 1;
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
    public void setEntity(int row, int col, Entity e) {
        int depth = e.getDepth();
        Entity previous = getEntity(row,col);

        if (depthCollector.size() <= depth)
            depthCollector.add(new ArrayList<>());
        if (depthPlant.size() <= depth)
            depthPlant.add(new ArrayList<>());

        //remove previous entity
        if (previous instanceof Collector)
            depthCollector.get(depth).remove(previous);
        if (previous instanceof Plant)
            depthPlant.get(depth).remove(previous);

        entityGrid[row][col] = e;

        //add new entity
        if (e instanceof Collector)
            depthCollector.get(depth).add(e);
        if (e instanceof Plant)
            depthPlant.get(depth).add(e);
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
        nutrientReceive();
        nutrientTransfer();
        growthManage();

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

    private void nutrientReceive() {

        //generate root nutrients
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                Entity e = entityGrid[i][j];
                if (e instanceof Root)
                    e.setNutrients(e.getDepth() * NUTRIENTS_ROOT_BASE);
            }
        //give sunlight
        int sunlight;
        for (int j = 0; j < columns; j++) {
            sunlight = getEnvironment().getSunlight();
            for (int i = 0; i < rows; i++) {
                Entity e = entityGrid[i][j];
                if (e instanceof Leaf) {
                    e.setNutrients(sunlight);
                    sunlight /= NUTRIENTS_SUNLIGHT_DIMINISH;
                }
            }
        }
    }

    private void nutrientTransfer() {

        int d = depthCollector.size() - 1;

        //reset all plant nutrient values
        for (ArrayList<Entity> elist: depthPlant)
            for (Entity e : elist)
                e.setNutrients(0);

        //go through collector transfers (descending by depth, skip 0)
        ArrayList<Entity> entityDepthList = depthCollector.get(d);
        while (d != 0) {
            for (Entity e : entityDepthList) {
                e.getParent().addNutrients(e.getNutrients());
            }

            d--;
            entityDepthList = depthCollector.get(d);
        }

        //go through plant transfers (ascending by depth)

        for(d = 0; d < depthPlant.size(); d++) {
            entityDepthList = depthPlant.get(d);
            for (Entity e : entityDepthList) {
                e.useNutrients();
                if (e.getParent() != null)
                    e.getParent().addNutrients(e.getNutrients());
            }
        }
    }

    private void growthManage() {
        //copy the list; grow() adds to the depthPlant list
        ArrayList<ArrayList<Entity>> copy = new ArrayList<>();
        copy.addAll(depthPlant);

        for (ArrayList<Entity> elist: copy)
            for (Entity e : elist)
                if (e.canLive()) {
                    //if plant has enough nutrients to grow another Plant,
                    ((Plant) e).grow();
                    System.out.println(e.getNutrients());
                    
                    if(e.getNutrients() < 7) {
                    		((Plant) e).growLeaf();
                    }
                    
                   
                    //if else plant has enough nutrients to grow a Leaf
                        //growLeaf(); (leaves are prioritized more heavily)
                    //if else plant has enough nutrients to grow a Root
                        //growRoot(); (roots aren't prioritized as heavily)
                }
                //else
                    //die
    }
}
