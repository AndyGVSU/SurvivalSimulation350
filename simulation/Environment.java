package simulation;

public class Environment {
    private int[] temperatureRange;
    private int[] sunlightRange;
    private int[] weatherRange;

    private int temperature;
    private int weatherFreq;
    private int sunlight;

    public Environment(){
        temperatureRange = new int[]{-50, 150};
        sunlightRange = new int[]{0,100};
        weatherRange = new int[]{0,100};
    }

    public int[] getTemperatureRange() { return temperatureRange; }
    public int[] getWeatherRange() { return weatherRange; }
    public int[] getSunlightRange() { return sunlightRange; }
    public int getSunlight() {return sunlight;}
    public int getTemperature() {return temperature;}
    public int getWeatherFreq() {return weatherFreq;}
    public int setSunlight(int s) {sunlight = s; return sunlight;}
    public int setTemperature(int t) {temperature = t; return temperature;}
    public int setWeatherFreq(int w) {weatherFreq = w; return weatherFreq;}
}
