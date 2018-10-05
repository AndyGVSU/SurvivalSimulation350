package simulation;

public class MainSimulation {
    private int rows;
    private int columns;
    private Entity[][] entityGrid;
    private int[] temperatureRange;
    private int[] speedRange;
    private int[] sunlightRange;
    private int[] weatherRange;

    public MainSimulation(int r, int c) {
        rows = r;
        columns = c;
        entityGrid = new Entity[rows][columns];

        temperatureRange = new int[2];
        speedRange = new int[2];
        sunlightRange = new int[2];
        weatherRange = new int[2];

        temperatureRange[0] = -50; temperatureRange[1] = 150; //degrees F
        speedRange[0] = 0; speedRange[1] = 50; //decisecond intervals between steps
        weatherRange[0] = 0; weatherRange[1] = 100; //percent frequency
        sunlightRange[0] = 0; sunlightRange[1] = 100; //percent sunlight
    }

    public int getRows(){
        return rows;
    }
    public int getColumns(){
        return columns;
    }
    public int[] getTemperatureRange() { return temperatureRange;}
    public int[] getSpeedRange() { return speedRange;}
    public int[] getWeatherRange() { return weatherRange;}
    public int[] getSunlightRange() { return sunlightRange;}

}
