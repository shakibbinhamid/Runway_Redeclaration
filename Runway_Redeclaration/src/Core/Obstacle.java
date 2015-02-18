package Core;

import CoreInterfaces.ObstacleInterface;

/**
 * 
 * @author Stefan 
 * @Editor Stefan
 * @Tester
 */
public class Obstacle implements ObstacleInterface {
	private String name;
	private double radius, height;
	
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
	public double getHeight() {
		return this.height;
	}

	@Override
	public double getRadius() {
		return this.radius;
	}

}
