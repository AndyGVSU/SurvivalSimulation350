package simulation;

/**
 *  The fruit entity is created by a "stem-plant entity". It is created in the
 *  place of a recently removed leaf. This fruit is intended to act as a seed.
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
        color = redColor;
    }

    /**
     * The number of ticks before this fruit should try to spawn a plant below.
     */
    private final int fruitDropTime = 5;

    /**
     * The number of spaces to the side that the fruit will try to root into
     * when it falls off the plant.
     */
    private final int fruitDropSideDistance = 2;

    /**
     * @return  The number of ticks that this particular fruit takes to drop a
     *          seed.
     * @see     MainSimulation.java fruitManage()
     * @author  Brendon
     * @since   Nov 18, 2018
     */
    public int getFruitDropTime() {
        return fruitDropTime;
    }

    /**
     * Tries to handle the fruit removal and generation of a potential plant.
     */
    public void seedDrop() {
        // "Drops" regardless of if new plant is able to grow below.
        simulation.setEntity(getRow(), getColumn(),
             new Air(simulation, null, getDepth(), getRow(), getColumn()));

        // For all columns within the reach of side-distance from the original
        // column, check the ground for viability, then plant seed.
        //   Starts at the right and searches to the left.
        boolean successfullyPlanted = false;
        for (int tempCol = getColumn() + fruitDropSideDistance;
             Math.abs(getColumn() - tempCol) < fruitDropSideDistance
                     && !successfullyPlanted; tempCol--) {
            // Iterate "downward" until hitting dirt.
            for (int tempRow = getRow(); tempRow < simulation.getRows();
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


