package simulation;

/**
 * The environment class has variables that will
 * influence the entities within the simulation.
 */
public class Environment {
    /**
     * The minimum and maximum allowed values for temperature.
     */
    private int[] temperatureRange;
    /**
     * The minimum and maximum allowed values for sunlight.
     */
    private int[] sunlightRange;
    /**
     * The minimum and maximum allowed values for weatherFreq.
     */
    private int[] weatherRange;

    /**
     * The temperature of the environment.
     * Not currently implemented.
     */
    private int temperature;
    /**
     * The frequency of weather in the environment.
     * Not currently implemented.
     */
    private int weatherFreq;
    /**
     * The amount of sunlight that is present in the environment.
     */
    private int sunlight;

    /**
     * The constructor method for Environment.
     * Assigns preset values to the class variables.
     */
    public Environment() {
        final int tempRangeLow = -50;
        final int tempRangeHigh = 150;
        final int sunlightRangeLow = 0;
        final int sunlightRangeHigh = 100;
        final int weatherRangeLow = 0;
        final int weatherRangeHigh = 100;
        final int defaultTemp = 72;
        final int defaultSunlight = 100;
        final int defaultWeatherFreq = 0;

        temperatureRange = new int[]{tempRangeLow, tempRangeHigh};
        sunlightRange = new int[]{sunlightRangeLow, sunlightRangeHigh};
        weatherRange = new int[]{weatherRangeLow, weatherRangeHigh};

        temperature = defaultTemp;
        sunlight = defaultSunlight;
        weatherFreq = defaultWeatherFreq;
    }

    /**
     * @return The current value of temperatureRange.
     */
    public final int[] getTemperatureRange() {
        return temperatureRange; }

    /**
     * @return The current value of weatherRange.
     */
    public final int[] getWeatherRange() {
        return weatherRange; }

    /**
     * @return The current value of sunlightRange.
     */
    public final int[] getSunlightRange() {
        return sunlightRange; }

    /**
     * @return The current value of sunlight.
     */
    public final int getSunlight() {
        return sunlight; }

    /**
     * @return The current value of temperature.
     */
    public final int getTemperature() {
        return temperature; }

    /**
     * @return The current value of weatherFreq.
     */
    public final int getWeatherFreq() {
        return weatherFreq; }

    /**
     * @param s The desired value for the sunlight variable.
     * @return The updated value of sunlight.
     */
    public final int setSunlight(final int s) {
        sunlight = s; return sunlight; }

    /**
     * @param t The desired value for the temperature variable.
     * @return The updated value of temperature.
     */
    public final int setTemperature(final int t) {
        temperature = t; return temperature; }

    /**
     * @param w The desired value for the weatherFreq variable.
     * @return The updated value of weatherFreq.
     */
    public final int setWeatherFreq(final int w) {
        weatherFreq = w; return weatherFreq; }
}
