package simulation;

/**
 * The root-entity is created by the species of plant-entity, such as grass.
 * The root-entity object "collects" nutrients for the entire plant.
 */
public class Root extends Collector {
    /**
     * The contructor method of the root-entity. This sets GUI colors and
     * letters.
     * @param sim   The simulation object that contains the entire system.
     * @param flow  A "copy" of the entity that this was born from.
     * @param depth The distance of this entity from the original plant
     *              entity.
     * @param row   The "y" coordinate of this entity.
     * @param col   The "x" coordinate of this entity.
     */
    public Root(final MainSimulation sim, final Entity flow,
                final int depth, final int row, final int col) {
        super(sim, flow, depth, row, col);
        name = "ROOTS";
        symbol = 'R';
        final int brownColor = 4;
        this.setColor(brownColor);
        survivalRequirement = 2;
    }
}
