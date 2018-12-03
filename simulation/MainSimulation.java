package simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Represents the simulation and its parts.
 */
public class MainSimulation {
    /**
     * Used to divide sunlight to small numbers below current entity.
     */
    private final int nutrientsSunlightDiminish = 5;
    /**
     * The number of nutrients that a root intially pulls.
     */
    private final int nutrientsRootBase = 10;
    /**
     * Number of rows in the simulation.
     */
    private int rows;
    /**
     * Number of columns in the simulation.
     */
    private int columns;
    /**
     * Whether the simulation is currently running.
     */
    private boolean playing;
    /**
     * The array of contained entities.
     */
    private Entity[][] entityGrid;
    /**
     * The default speed of the simulation.
     */
    private final int defaultSpeed = 50;
    /**
     * The current speed of the simulation in deciseconds.
     */
    private int speed = defaultSpeed;
    /**
     * The range of appropriate speeds given to the user.
     */
    private int[] speedRange;
    /**
     * The environment object for this simulation.
     */
    private Environment environment;
    /**
     * The tool to save and write simulations.
     */
    private SimulationRecord record;

    /**
     * Keeps track of plant dependencies for nutrient-transfer.
     */
    private ArrayList<ArrayList<Entity>> depthPlant;
    /**
     * Keeps track of plant dependencies for nutrient-transfer.
     */
    private ArrayList<ArrayList<Entity>> depthCollector;
    /**
     * List of all the current simulation fruits.
     */
    private ArrayList<Entity> fruitList
            = new ArrayList<Entity>(rows * columns);

    /**
     * Number of plants.
     */
    private int plantCount = 0;
    /**
     * Total simulation steps.
     */
    private int totalSteps;
    /**
     * Current step the simulation is showing.
     */
    private int currentStep;

    /** Runs the logic of the simulation. */
    /**
     * Runs the logic of the simulation.
     *
     * @param row The number of rows in this simulation.
     * @param col The number of columns in this simulation.
     */
    public MainSimulation(final int row, final int col) {
        rows = row;
        columns = col;
        final int speedMax = 100;
        speedRange = new int[]{0, speedMax};

        try {
            record = new SimulationRecord();
        } catch (IOException e) {
            System.exit(1);
        }

        playing = false;

        reset(false);
    }

    /**
     * Sets up the map as one of many potential setups.
     */
    public final void setDefaultOne() {
        final int numberOfDirtLayers = 5;
        final int numberOfAirLayers = rows - numberOfDirtLayers;

        // Setting the air entities
        for (int i = 0; i < numberOfAirLayers; i++) {
            for (int j = 0; j < columns; j++) {
                setEntity(i, j, new Air(this, null, 0, i, j));
            }
        }

        // Setting the bottom dirt layers
        for (int i = numberOfAirLayers; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                entityGrid[i][j] = new Dirt(this, null, 0, i, j);
                setEntity(i, j, entityGrid[i][j]);
            }
        }

        // Set some default plants
        final int numberOfPlants = 3;
        Random rand = new Random();
        int randLow = 0;
        int randHigh = columns;
        for (int i = 0; i < numberOfPlants; i++) {
            while (true) {
                int result = rand.nextInt(randHigh - randLow)
                        + randLow;
                if (!(entityGrid[rows - numberOfDirtLayers - 1]
                        [result] instanceof Plant)) {
                    setEntity(rows - numberOfDirtLayers - 1,
                        result, new Grass(this,
                        null, 0,
                        rows - numberOfDirtLayers - 1,
                        result));
                    plantCount = plantCount + 1;
                    break;
                }
            }
        }
    }

    /**
     * Returns the entity-object at the specified location.
     *
     * @param row The "y" coordinate of this entity.
     * @param col The "x" coordinate of this entity.
     * @return Entity located at row and col.
     */
    public final Entity getEntity(final int row, final int col) {
        // "col > columns" -> "col >= columns" for the case of
        // a right-most leaf error.
        if (row < 0 || row > rows || col < 0 || col >= columns) {
            return null;
        }
        return entityGrid[row][col];
    }

    /**
     * Appropriately sets an entity at a location.
     *
     * @param row The "y" coordinate of this entity.
     * @param col The "x" coordinate of this entity.
     * @param e   The new entity to set to this location.
     */
    public final void setEntity(final int row, final int col, final Entity e) {
        int depth = e.getDepth();
        Entity previous = getEntity(row, col);
        int prevDepth = previous.getDepth();

        // Handle lists that are crucial for nutrient transfer
        if (depthCollector.size() <= depth) {
            depthCollector.add(new ArrayList<>());
        }
        if (depthPlant.size() <= depth) {
            depthPlant.add(new ArrayList<>());
        }

        // Remove the previous entity
        if (previous instanceof Fruit) {
            fruitList.remove(previous);
        }
        if (previous instanceof Collector) {
            depthCollector.get(prevDepth).remove(previous);
        }
        if (previous instanceof Plant) {
            depthPlant.get(prevDepth).remove(previous);
        }

        replaceEntity(row, col, e);

        // Add the new entity
        if (e instanceof Fruit) {
            fruitList.add(e);
        }
        if (e instanceof Collector) {
            depthCollector.get(depth).add(e);
        }
        if (e instanceof Plant) {
            depthPlant.get(depth).add(e);
        }
    }

    /**
     * Low-level method that changes the grid entity.
     *
     * @param row The "y" coordinate of this entity.
     * @param col The "x" coordinate of this entity.
     * @param e   The new entity to set to this location.
     */
    public final void replaceEntity(final int row,
                                    final int col, final Entity e) {
        entityGrid[row][col] = e;
    }

    /**
     * Returns the number of rows in the simulation.
     *
     * @return The number of rows in the simulation
     */
    public final int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the simulation.
     *
     * @return The number of columns in the simulation.
     */
    public final int getColumns() {
        return columns;
    }

    /**
     * Returns the appropriate range of speeds available to the user.
     *
     * @return The appropriate range of speeds available to the user.
     */
    public final int[] getSpeedRange() {
        return speedRange;
    }

    /**
     * Returns the environment of the current simulation.
     *
     * @return The environment of the current simulation.
     */
    public final Environment getEnvironment() {
        return environment;
    }

    /**
     * Gets the current simulation speed.
     *
     * @return The current simulation speed.
     */
    public final int getSpeed() {
        return speed;
    }

    /**
     * Sets the simulation speed.
     *
     * @param s The new simulation speed.
     * @return The new simulaiton speed.
     */
    public final int setSpeed(final int s) {
        speed = s;
        return speed;
    }

    /**
     * Iterates the entire simulation one time-increment forward.
     * - Calls methods for nutrient grabbing and transfer
     * - Calls plants to grow
     * - Calls fruits to do their actions
     * - Steps each entity's lifetime count
     * - Appropriately records/plays the simulation
     */
    public final void stepForward() {
        currentStep++;
        if (currentStep > totalSteps) {
            totalSteps++;
            nutrientReceive();
            nutrientTransfer();
            growthManage();
            fruitManage();

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    entityGrid[i][j].doStep();
                }
            }
            try {
                record.writeStep(currentStep, this);
            } catch (IOException e) {
                reset(true);
            }
        } else {
            try {
                record.readStep(currentStep, this);
            } catch (Exception e) {
                reset(true);
            }
        }
    }

    /**
     * Iterates the simulation backwards to allow for more user observation.
     */
    public final void stepBackward() {
        if (currentStep > 0) {
            currentStep--;
            try {
                record.readStep(currentStep, this);
            } catch (Exception e) {
                reset(true);
            }
        }
    }

    /**
     * Holder method for the potential save functionality.
     */
    public void save() {
    }

    /**
     * Holder method for the potential load functionality.
     */
    public void load() {
    }

    /**
     * Generates the roots and leaves "receiving" of nutrients.
     */
    private void nutrientReceive() {
        // Generate root nutrients
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Entity e = entityGrid[i][j];
                if (e instanceof Root) {
                    e.setNutrients(e.getDepth()
                            * nutrientsRootBase - e.getLifeSteps());
                }
            }
        }
        // Give sunlight nutrients to leaves
        int sunlight;
        for (int j = 0; j < columns; j++) {
            sunlight = getEnvironment().getSunlight();
            for (int i = 0; i < rows; i++) {
                Entity e = entityGrid[i][j];
                if (e instanceof Leaf) {
                    if (sunlight == 0) {
                        // When leaf-deleted, removes
                        // from plant's nutrient list.
                        ((Plant) e.getFlowTo()).removeNutrientsFrom(e);
                        setEntity(i, j,
                                new Air(this, null, 0, i, j));
                        // TODO - Set flow-to plant to not
                        // be able to grow leaves again
                    } else {
                        e.setNutrients(sunlight);
                        sunlight /= nutrientsSunlightDiminish;
                    }
                } else if (e instanceof Air) {
                    e.setNutrients(sunlight);
                } else if (e instanceof Fruit) {
                    sunlight /= nutrientsSunlightDiminish;
                } else if (e instanceof Dirt) {
                    sunlight = 0;
                }
            }
        }
    }

    /**
     * Passes the nutrients from each plant to its respective flowTo.
     */
    private void nutrientTransfer() {

        int d = depthCollector.size() - 1;

        // Reset all plant nutrient values
        for (ArrayList<Entity> elist : depthPlant) {
            for (Entity e : elist) {
                e.setNutrients(0);
            }
        }

        // Go through collector transfers (descending by depth, skip 0)
        ArrayList<Entity> entityDepthList = depthCollector.get(d);
        while (d != 0) {
            for (Entity e : entityDepthList) {
                if (e.getNutrients() > 0) {
                    e.getFlowTo().addNutrients(e.getNutrients());
                }
            }
            d--;
            entityDepthList = depthCollector.get(d);
        }

        // Go through plant transfers (ascending by depth)

        for (d = 0; d < depthPlant.size(); d++) {
            entityDepthList = depthPlant.get(d);
            for (Entity e : entityDepthList) {
                e.useNutrients();
                if (e.getFlowTo() != null) {
                    e.getFlowTo().addNutrients(e.getNutrients());
                }
            }
        }
    }

    /**
     * Manages the growth logic for each plant.
     */
    private void growthManage() {

        // copy the list; grow() adds to the depthPlant list
        ArrayList<Entity> copy = new ArrayList<>();
        for (ArrayList<Entity> elist : depthPlant) {
            copy.addAll(elist);
        }

        //go through plants, ascending by depth
        Entity e;
        ArrayList<Entity> deletedItems = new ArrayList<>();
        for (Iterator<Entity> iter = copy.iterator(); iter.hasNext();) {
            e = iter.next();

            if (deletedItems.contains(e)) {
                iter.remove();
            } else {
                if (e.canLive()) {

                    // Calls on stem to see if it can produce a fruit to its
                    // left or right.
                    if (((Plant) e).canGrowFruit()) {
                        ((Plant) e).growFruit();
                    }

                    // Plant-based boolean to see if the stem should grow.
                    // Allows for limitations of growth speed and max-height
                    // specific to varying plant-types.
                    if (((Plant) e).canGrowPlant()) {
                        ((Plant) e).growPlant();
                    }

                    if (((Plant) e).canGrowLeaf()) {
                        ((Plant) e).growLeaf();
                    }

                    // Plant-based boolean on if the root should grow
                    // Brendon would suggest expanding this to leaf and stem.

                    // The depth-zero plant will be responsible for limiting
                    // root growth.
                    if (((Plant) e).canGrowRoot()) {
                        ((Plant) e).growRoot();
                    }
                } else {
                    // only the depth-zero plant can die (to totally eliminate
                    // the plant)
                    if (e instanceof Plant && e.getDepth() == 0) {
                        deletedItems.addAll(((Plant) e).die());
                    }
                }
            }
        }
    }

    /**
     * This method oversees the functionality of all the fruit-entities
     * currently on the view. Brendon.
     * <p>
     * 1. Should a fruit drop based on time? Drop a fruit.
     * 2. Is the plant the fruit is connected to dead? Drop fruit.
     */
    public final void fruitManage() {
        for (int i = 0; i < fruitList.size(); i++) {
            Entity f = fruitList.get(i);

            // If the fruit has lasted a certain amount of time..
            if (f.getLifeSteps() > ((Fruit) f).getFruitDropTime()) {
                System.out.println("SeedDrop r" + f.getRow()
                        + " - c" + f.getColumn());
                ((Fruit) f).seedDrop();
                continue;
            }

            // If the plant that the fruit is connected to is gone..
            // This checks for difference between lifesteps of
            // flowto() and the new entity's lifesteps.
            if (f.getFlowTo().getLifeSteps()
                    != getEntity(f.getFlowTo().getRow(),
                    f.getFlowTo().getColumn()).getLifeSteps()) {
                ((Fruit) f).seedDrop();
            }
        }
    }

    /**
     * Get the current simulation step.
     *
     * @return The step number that the simulation is
     * currently looking at.
     */
    public final int getCurrentStep() {
        return currentStep;
    }

    /**
     * Get the total number of steps demonstrated.
     *
     * @return The total number of steps generated in this
     * simulation.
     */
    public final int getTotalSteps() {
        return totalSteps;
    }

    /**
     * @return Gets the simulation's SimulationRecord object.
     */
    public final SimulationRecord getRecord() {
        return record;
    }

    /**
     * Resets the simulation to the starting state.
     * @param hardReset Whether to reset the directory files or not.
     */
    public final void reset(final boolean hardReset) {
        currentStep = 0;
        totalSteps = 0;
        environment = new Environment();
        entityGrid = new Entity[rows][columns];
        depthPlant = new ArrayList<>();
        depthCollector = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                replaceEntity(r, c,
                        new Air(this, null, 0, r, c));
            }
        }

        // Resets the map to this particular default setup.
        setDefaultOne();

        if (hardReset) {
            record.reset();
        }

        if (!record.simulationExists()) {
            try {
                record.writeStep(currentStep, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                record.readStep(currentStep, this);
                totalSteps = record.readTotalSteps();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
