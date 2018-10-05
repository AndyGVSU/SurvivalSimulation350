package simulation;

public class MainSimulation {
    private int rows;
    private int columns;
    private Entity[][] entityGrid;
    private int speed; //in deciseconds
    private int[] speedRange;
    private Environment environment;

    public MainSimulation(int r, int c) {
        rows = r;
        columns = c;
        entityGrid = new Entity[rows][columns];
        speedRange = new int[]{0,50};
        environment = new Environment();
    }

    public int getRows(){
        return rows;
    }
    public int getColumns(){
        return columns;
    }
    public int[] getSpeedRange() { return speedRange; }
    public Environment getEnvironment() {return environment; }
    public int getSpeed() {return speed;}
    public int setSpeed(int s) {speed = s; return speed;}

}
