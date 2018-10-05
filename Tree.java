package simulation;

public class Tree extends Entity {
	
	private int ab;
	
	public Tree(String name,int nutrients, int height, int width,int ab) {
		super(name,nutrients,height,width);
		
	}
	
	
	
	@Override 
	public int GrowthNeed() {
		ab = 5 * getHeight() * getWidth();
		return ab;
		
		
	}

	@Override
	public int Growth() {
		
		if(getNutrients() > 500) {
			setHeight(getHeight()+1);
			
		}
		if(getNutrients() < 100) {
			setHeight(getHeight()-1);	
		}
		
		return 0;
	}
	
	
	
	

}
