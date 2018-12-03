package simulation;

import java.util.Random;

/**
 *  The fruit entity is created by a "stem-plant entity". It is created in the
 *  place of a recently removed leaf. This fruit is intended to act as a seed.
 *  @author  Brendon Murthum
 */
public class Fruit extends Entity {
    /**
     * The contructor method of the fruit entity. This sets GUI colors and
     * letters.
     * @param sim   The simulation object that contains the entire system.
     * @param flow  A "copy" of the entity that this fruit was born from.
     * @param depth The distance of this entity from the original plant entity.
     * @param row   The "y" coordinate of this entity.
     * @param col   The "x" coordinate of this entity.
     */
    public Fruit(final MainSimulation sim, final Entity flow, final int depth,
                 final int row, final int col) {
        super(sim, flow, depth, row, col);
        name = "FRUIT";
        symbol = 'F';
        final int redColor = 5;
        this.setColor(redColor);
    }

    /**
     * The number of ticks before this fruit should try to spawn a plant below.
     */
    private final int fruitDropTime = 5;

    /**
     * The number of spaces to the side that the fruit will try to root into
     * when it falls off the plant.
     */
    private final int fruitDropSideDistance = 3;

    /**
     * @return  The number of ticks that this particular fruit takes to drop a
     *          seed.
     * @see     MainSimulation fruitManage()
     * @author  Brendon
     * @since   Nov 18, 2018
     */
    public final int getFruitDropTime() {
        return fruitDropTime;
    }

    /**
     * Tries to handle the fruit removal and generation of a potential plant.
     */
    public final void seedDrop() {
        // "Drops" regardless of if new plant is able to grow below.
        // TODO - Note that this is GRASS specific.
        // It may be worth changing later to PLANT.

        System.out.println("  SeedDrop Inside");

        // Replace the fruit with a leaf that correctly acts as a collector
        Leaf l = new Leaf(simulation, this.getFlowTo(), getDepth(),
                getRow(), getColumn());
        ((Plant) this.getFlowTo()).addToNutrientsFrom(l);
        simulation.setEntity(this.getRow(), this.getColumn(), l);

        // For all columns within the reach of side-distance from the original
        // column, check the ground for viability, then plant seed.
        //   Starts at the right and searches to the left.
        boolean successfullyPlanted = false;

        // Put columns to check into a random order
        int lowestColumn;
        int highestColumn;
        if (this.getColumn() - fruitDropSideDistance < 0) {
            lowestColumn = 0;
        } else {
            lowestColumn = this.getColumn() - fruitDropSideDistance;
        }
        if (this.getColumn()
                + fruitDropSideDistance > simulation.getColumns()) {
            highestColumn = simulation.getColumns();
        } else {
            highestColumn = this.getColumn() + fruitDropSideDistance;
        }
        int numberOfColumns = highestColumn - lowestColumn + 1;
        int[] columnsToCheck = new int[numberOfColumns];
        Random randomSeed = new Random();
        // Add to each index
        for (int i = 0; i < numberOfColumns - 1; i++) {
            boolean addedEntry = false;
            // Is this new value worth adding?
            while (!addedEntry) {
                int randomInt = randomSeed.nextInt(numberOfColumns)
                        + lowestColumn;
                boolean isInArray = false;
                for (int j = 0; j < numberOfColumns; j++) {
                    if (columnsToCheck[j] == randomInt) {
                        isInArray = true;
                    }
                }
                if (!isInArray) {
                    columnsToCheck[i] = randomInt;
                    addedEntry = true;
                }
            }
        }

        // Check each of these random columns-within-range for valid
        // drop spots.
        int tempCol;
        for (int i = 0; i <  numberOfColumns && !successfullyPlanted; i++) {
            // A random column within range to check for valid planting
            tempCol = columnsToCheck[i];
            // Iterate "downward" until hitting dirt.
            for (int tempRow = this.getRow(); tempRow < simulation.getRows();
                 tempRow++) {
                // Found dirt!
                if (simulation.getEntity(tempRow, tempCol).getSymbol() == 'D') {
                    // If the spot above the dirt is air..
                    if (simulation.getEntity(tempRow - 1,
                            tempCol).getSymbol() == '.') {
                        simulation.setEntity(tempRow - 1, tempCol,
                                new Grass(simulation, null, 0,
                                        tempRow - 1, tempCol));
                        // This allows for breaking from the parent loop.
                        successfullyPlanted = true;
                    }
                    // Break from the downward search when hitting dirt.
                    break;
                }
            }
        }
    }
}


