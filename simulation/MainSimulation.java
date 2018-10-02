package simulation;

public class MainSimulation {
    private int rows;
    private int columns;
    private Entity[][] entityGrid;

    public MainSimulation(int r, int c) {
        rows = r;
        columns = c;
        entityGrid = new Entity[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                entityGrid[i][j] = new Entity();
            }
        }
    }

    public int getRows(){
        return rows;
    }
    public int getColumns(){
        return columns;
    }
}
