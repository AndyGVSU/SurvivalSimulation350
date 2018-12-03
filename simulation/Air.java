package simulation;

/**
 * The Air-entity, used when there are no other entities in a place.
 */
public class Air extends Entity {

    /**
     * The constructor for the air entity.
     * @param sim   The simulation object that contains the entire system.
     * @param flow  Unused with AIR.
     * @param depth Unused with AIR.
     * @param row   The "y" coordinate of this entity.
     * @param col   The "x" coordinate of this entity.
     */
    public Air(final MainSimulation sim, final Entity flow,
               final int depth, final int row, final int col) {
        super(sim, flow, depth, row, col);
        name = "AIR";
        symbol = '.';
        this.setColor(1);
    }

    /**
     * Unused with AIR.
     */
    public void doStep() {
    }
}
