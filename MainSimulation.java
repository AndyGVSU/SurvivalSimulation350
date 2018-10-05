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
            			createEntity(2);
            		
            		      
          }
        }
    }

    public int getRows(){
        return rows;
    }
    public int getColumns(){
        return columns;
    }
    
    public void createEntity(int choice) {
    		if(choice == 1) {

        		Entity Soil = new Soil("Soil",500,2,1,4);
        		
        	}
    		if(choice == 2) {

        		Entity Plant = new Plant("Flower",100,2,1,4);
        	}
    		if(choice == 3) {

        		Entity Tree = new Tree("Pine",150,2,1,4);
        	}
    }
    
    
}
    
    		
    		
    	
    	
    	
    	
  