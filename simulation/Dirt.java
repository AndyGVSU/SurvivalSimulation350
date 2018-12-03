package simulation;

/**
 * The dirt-entity is initially created/set by the system to the grid.
 * It is used to validate where a plant can be set.
 */
public class Dirt extends Entity {
    /**
     * The contructor method of the fruit entity. This sets GUI colors and
     * letters.
     *
     * @param sim   The simulation object that contains the entire system.
     * @param flow  Unused with DIRT
     * @param depth Unused with DIRT
     * @param row   The "y" coordinate of this entity.
     * @param col   The "x" coordinate of this entity.
     */
    public Dirt(final MainSimulation sim, final Entity flow,
                final int depth, final int row, final int col) {
        super(sim, flow, depth, row, col);
        name = "DIRT";
        symbol = 'D';
        final int orangeColor = 2;
        this.setColor(orangeColor);
    }
}
