package Core;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.Savable;

/**
 * 
 * @author Stefan 
 * @Editor Stefan
 * @Tester
 */

@Root
public class Obstacle implements ObstacleInterface, Savable {
	
	@Attribute
	private String name;
	@Element
	private double radius, height;
	
	public Obstacle(String name, int farthestPoint, int highestPoint){
		this.name = name;
		this.radius = farthestPoint;
		this.height = highestPoint;
	}
	
	public Obstacle(){
		
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
