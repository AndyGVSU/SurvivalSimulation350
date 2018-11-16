package simulation;

import javax.swing.*;
import java.io.IOException;
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
	private SimulationRecord record;

	/**
	 * Keeps track of plant dependencies
	 */
	private ArrayList<ArrayList<Entity>> depthPlant;
	private ArrayList<ArrayList<Entity>> depthCollector;

	private int plantCount = 0;
	private int totalSteps;
	private int currentStep; // steps to iterate as "time"

	public MainSimulation(int r, int c) {
		rows = r;
		columns = c;
		speedRange = new int[]{0, 100};
		record = new SimulationRecord();

		playing = false;

		reset();
	}

	// Changes the simulation up for a sample run. After initialization. #Brendon
	public void setDefaultOne() {
		// Setting the air entities
		for (int i = 0; i < rows - 5; i++) // Leave the bottom 5 for dirt
			for (int j = 0; j < columns; j++) {
				setEntity(i, j, new Air(this, null, 0, i, j));
			}

		// Setting the bottom dirt layers
		for (int i = rows - 5; i < rows; i++)
			for (int j = 0; j < columns; j++) {
				setEntity(i, j, entityGrid[i][j] = new Dirt(this, null, 0, i, j));
			}

		// Set some default plants
		int numberOfPlants = 3;
		Random rand = new Random();
		int Low = 0;
		int High = columns;
		for (int i = 0; i < numberOfPlants; i++) {
			while (true) {
				int Result = rand.nextInt(High - Low) + Low;
				if (!(entityGrid[rows - 6][Result] instanceof Plant)) {
					setEntity(rows - 6, Result, new Grass(this, null, 0, rows - 6, Result));
					plantCount = plantCount + 1;
					break;
				}
			}
		}
	}

	public Entity getEntity(int row, int col) {
		// "col > columns" -> "col >= columns" for the case of a right-most leaf error.
		if (row < 0 || row > rows || col < 0 || col >= columns)
			return null;
		return entityGrid[row][col];
	}

	public void setEntity(int row, int col, Entity e) {
		int depth = e.getDepth();
		Entity previous = getEntity(row, col);

		if (depthCollector.size() <= depth)
			depthCollector.add(new ArrayList<>());
		if (depthPlant.size() <= depth)
			depthPlant.add(new ArrayList<>());

		//remove previous entity
		if (previous instanceof Collector)
			depthCollector.get(depth).remove(previous);
		if (previous instanceof Plant)
			depthPlant.get(depth).remove(previous);

		replaceEntity(row, col, e);

		//add new entity
		if (e instanceof Collector)
			depthCollector.get(depth).add(e);
		if (e instanceof Plant)
			depthPlant.get(depth).add(e);
	}

	public void replaceEntity(int row, int col, Entity e) {
		entityGrid[row][col] = e;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int[] getSpeedRange() {
		return speedRange;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public int getSpeed() {
		return speed;
	}

	public int setSpeed(int s) {
		speed = s;
		return speed;
	}

	public void setPlaying(boolean play) {
		playing = play;
	}

	public boolean getPlaying() {
		return playing;
	}

	public void stepForward() {
		currentStep++;
		if (currentStep > totalSteps) {
			totalSteps++;
			nutrientReceive();
			nutrientTransfer();
			growthManage();

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					entityGrid[i][j].doStep();
				}
			}
			try {
				record.writeStep(currentStep, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				record.readStep(currentStep, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// For each plant, call doStep().


	}

	public void stepBackward() {
		if (currentStep > 0) {
			currentStep--;
			try {
				record.readStep(currentStep, this);
			} catch (Exception e) {
				reset();
			}
		}
	}

	public void save() {
	}

	;

	public void load() {
	}

	;

	// Generates nutrients to leaves and roots from environment
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
		for (ArrayList<Entity> elist : depthPlant)
			for (Entity e : elist)
				e.setNutrients(0);

		//go through collector transfers (descending by depth, skip 0)
		ArrayList<Entity> entityDepthList = depthCollector.get(d);
		while (d != 0) {
			for (Entity e : entityDepthList) {
				if (e.getNutrients() > 0)
					e.getParent().addNutrients(e.getNutrients());
			}
			d--;
			entityDepthList = depthCollector.get(d);
		}

		//go through plant transfers (ascending by depth)

		for (d = 0; d < depthPlant.size(); d++) {
			entityDepthList = depthPlant.get(d);
			for (Entity e : entityDepthList) {
				e.useNutrients();
				if (e.getParent() != null)
					e.getParent().addNutrients(e.getNutrients());
			}
		}
	}

	private void growthManage() {
		// copy the list; grow() adds to the depthPlant list
		ArrayList<ArrayList<Entity>> copy = new ArrayList<>();
		copy.addAll(depthPlant);

		for (ArrayList<Entity> elist : copy)
			for (Entity e : elist)
				if (e.canLive()) {
					// Plant-based boolean to see if the stem should grow.
					// Allows for limitations of growth speed and max-height
					// specific to varying plant-types.
					if (((Plant)e).canGrowPlant()) {
						((Plant)e).growPlant();
					}

					if (e.canGrowLeaf()) {
						((Plant) e).growLeaf();
					}

					// Plant-based boolean on if the root should grow
					// Brendon would suggest expanding this to leaf and stem.
					// The depth-zero plant will be responsible for limiting root
					// growth.
					if (((Plant)e).canGrowRoot() && ((Plant)e).getDepth() == 0) {
						((Plant)e).growRoot();
					}
				}

		// if else plant has enough nutrients to grow a Root
		// growRoot(); (roots aren't prioritized as heavily)
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public int getTotalSteps() {
		return totalSteps;
	}

	public SimulationRecord getRecord() {
		return record;
	}

	public void reset() {
		currentStep = 0;
		totalSteps = 0;
		environment = new Environment();
		entityGrid = new Entity[rows][columns];
		depthPlant = new ArrayList<>();
		depthCollector = new ArrayList<>();

		setDefaultOne();

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