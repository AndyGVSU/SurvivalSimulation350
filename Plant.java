package simulation;

public class Plant extends Entity {
	
	private int ab;
	
	
	public Plant(String name,int nutrients, int height, int width,int ab ) {
		super(name,nutrients,height,width);
		
	}
	
	
	@Override 
	public int GrowthNeed() {
		ab = 2 * getHeight() * getWidth();
		return ab;
		
	}
	
	@Override
	public int Growth() {
		if(getNutrients() > 100) {
			setHeight(getHeight()+1);
			
		}
		if(getNutrients() < 25) {
			setHeight(getHeight()-1);	
		}
		return 0;
	}
}