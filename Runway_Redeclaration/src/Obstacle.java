/**
 * 
 * @author Stefan 
 * @Editor Stefan
 * @Tester
 */
public class Obstacle implements ObstacleInterface {
	private String name;
	private int radius, height;
	
	public Obstacle(String name, int farthestPoint, int highestPoint){
		this.name = name;
		this.radius = farthestPoint;
		this.height = highestPoint;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public int getRadius() {
		return this.radius;
	}

}
