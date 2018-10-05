package simulation;

public class Soil extends Entity {
	
	private int ab;
	private int regen;
	
	
	public Soil(String name,int nutrients, int height, int width,int regen) {
		super(name,nutrients,height,width);
		
	}
	
	@Override
	public int GrowthNeed() {
		if(getNutrients() == 0) {
			setNutrients(500);
		}
		return 0;
	}
	
	@Override 
	public int Growth() {
		regen = 10;
		return regen;
		
	}

	
	
	

}
