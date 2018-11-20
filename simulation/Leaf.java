package simulation;

/**
 *  The leaf entity is created by a "stem-plant entity". It grabs sunlight
 *  as "nutrients" and passes it to its partnered plant-entity. This is
 *  handled by the "flow" parameter.
 */
public class Leaf extends Collector {
    /**
     * The contructor method of the leaf entity. This sets GUI colors and
     * letters.
     * @param sim   The simulation object that contains the entire system.
     * @param flow  A "copy" of the entity that this fruit was born from.
     * @param depth The distance of this entity from the original plant entity.
     * @param row   The "y" coordinate of this entity.
     * @param col   The "x" coordinate of this entity.
     */
    public Leaf(final MainSimulation sim, final Entity flow,
                final int depth, final int row, final int col) {
        super(sim, flow, depth, row, col);
        name = "LEAF";
        symbol = 'L';
        color = 3;
    }
}
