//
//package simulation;
//
//public class Animal extends Entity {
//
//    private int length;
//    private int energyConsumption;
//
//    /**
//    digestionTime is used to determine if an animal can eat certain
//    plants or not based on the level of cellulose in a plant
//    (herbivores have very long digestive systems,
//    carnivores have short systems because meat is easier to digest.)
//    **/
//    private int digestionTime;
//
//    private int speed;
//    private int viewDistance;
//    private int health;
//
//    private int nutrition;
//    private int lifespan;
//    private int age;
//
//    public Animal(MainSimulation sim, int nutrients, int height, int width, int digestionTime, int length, int viewDistance, int speed, int nutrition, int row, int col) {
//        super(sim, nutrients,height,width,row,col);
//        age = 0;
//        lifespan = findLifeSpan(height, width, length);
//        nutrition = nutrition;
//        digestionTime = digestionTime;
//        length = length;
//        speed = speed;
//        viewDistance = viewDistance;
//        energyConsumption = findEnergyConsumption(height,width,length);
//
//    }
//
//    //What is our metric for energy in the simulation?
//    private int findEnergyConsumption(int height, int width, int length){
//        return (height*width*length*(1-(age/lifespan)));
//    }
//
//    //How are we measuring time?
//    private int findLifeSpan(int height, int width, int length){
//        return (height*width*length);
//    }
//
//    // Added due to abstract in the entitiy.
//    public void doStep() { }
//}