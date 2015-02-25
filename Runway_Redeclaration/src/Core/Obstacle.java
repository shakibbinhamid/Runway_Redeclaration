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
 * @Tester Stefan
 */

@Root
public class Obstacle implements ObstacleInterface, Savable {
	
	@Attribute
	private String name;
	@Element
	private double radius, height;
	
	public Obstacle(String name, double radius, double height){
		this.name = name;
		this.radius = radius;
		this.height = height;
	}
	
	//nullary constructor
	protected Obstacle(){
		
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
