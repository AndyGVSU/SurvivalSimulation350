package simulation;

/**
 *  The fruit entity is created by a "stem-plant entity". It is created in the
 *  place of a recently removed leaf. This fruit is intended to act as a seed.
 */
public class Fruit extends Entity {
    public Fruit(MainSimulation sim, Entity flow, int depth, int row, int col) {
        super(sim, flow, depth, row, col);
        name = "FRUIT";
        symbol = 'F';
        color = 5;
    }

    private int fruitDropTime = 5;

    public int getFruitDropTime() {
        return fruitDropTime;
    }

    /**
     *  This method is called on a "stem" plant entity. This method is only
     *  called in the case that canGrowFruit() returns TRUE. This method
     *  destroys a left or right leaf then creates a fruit entity in its place.
     */
    /*

    // TODO In progress - Brendon

    public void seedShouldDrop() {
        if (checkAdjacent(AdjacentEntities.LEFT, row, col) instanceof Leaf) {
            Fruit f = new Fruit(simulation, simulation.getEntity(row, col),
                    depth + 1, row , col - 1);
            simulation.setEntity(row, col-1, f);
        }
    }

    public void seedDrop() {
        if (checkAdjacent(AdjacentEntities.LEFT, row, col) instanceof Leaf) {
            Fruit f = new Fruit(simulation, simulation.getEntity(row, col),
                    depth + 1, row , col - 1);
            simulation.setEntity(row, col-1, f);
        }
    }
    */
}


