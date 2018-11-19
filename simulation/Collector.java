package simulation;

/**
 * Collectors are entities that receive nutrients and give them to
 * the main plant entities. This includes both root-entities and
 * leaf-entities. The roots "collect" from the dirt, and the leaves
 * "collect" from the sunlight.
 */
public abstract class Collector extends Entity {
    /**
     * The contructor method of the collector entity. This is used by
     * leaf-class and root-class to establish similar behaviors.
     * @param sim   The simulation object that contains the entire system.
     * @param flow  A "copy" of the entity that this grass was born from.
     * @param depth The distance of this entity from the original plant
     *              entity.
     * @param row   The "y" coordinate of this entity.
     * @param col   The "x" coordinate of this entity.
     */
    public Collector(final MainSimulation sim, final Entity flow,
                     final int depth, final int row, final int col) {
        super(sim, flow, depth, row, col);
    }
}
