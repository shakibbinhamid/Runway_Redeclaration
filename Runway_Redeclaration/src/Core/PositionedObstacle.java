package Core;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;
import CoreInterfaces.Savable;


/**
 * This implementation of {@link PositionedObstacleInterface} works as a packer
 * It requires an un-positioned obstacle to instantiate {@link PositionedObstacleInterface}
 * 
 * @author Stefan
 * @Editor Stefan
 * @Testor Stefan
 *
 */
public class PositionedObstacle implements PositionedObstacleInterface, Savable{
	@Element
	private ObstacleInterface unpositionedObj;
	@Attribute
	private double distanceSmall, distanceLarge;
	
	//nullary
	protected PositionedObstacle(){
		
	}
	
	public PositionedObstacle(ObstacleInterface obj, double howFarInFromSmallAngledSide, double howFarInFromLargeAngledSide){
		this.unpositionedObj=  obj;
		this.distanceSmall = howFarInFromSmallAngledSide;
		this.distanceLarge = howFarInFromLargeAngledSide;
	}
	
	
	private ObstacleInterface getObst(){
		return this.unpositionedObj;
	}
	
	@Override
	public String getName() {
		return getObst().getName();
	}
	@Override
	public double getHeight() {
		return getObst().getHeight();
	}
	@Override
	public double getRadius() {
		return getObst().getRadius();
	}
	@Override
	public double distanceFromSmallEnd() {
		return this.distanceSmall;
	}
	
	@Override
	public double distanceFromLargeEnd() {
		return this.distanceLarge;
	}
}
