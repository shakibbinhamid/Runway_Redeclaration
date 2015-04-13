package core;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import coreInterfaces.ObstacleInterface;
import coreInterfaces.Savable;

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
	
	public static final String HEIGHT_REGEX = "(^[0-9]*\\.[0-9]+$)|"
											+ "(^[0-9]+$)";
	
	public static final String RADIUS_REGEX = "(^[0-9]*\\.[0-9]+$)|"
											+ "(^[0-9]+$)";
	
	public static final String NAME_REGEX = "[a-zA-Z].*";
	
	public static final String DIST_REGEX = "(^-?[0-9]*\\.[0-9]+$)|"
			  							  + "(^-?[0-9]+$)";
	
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
