package core.concrete;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import core.interfaces.ObstacleInterface;
import core.interfaces.PositionedObstacleInterface;
import core.interfaces.Savable;


/**
 * This implementation of {@link PositionedObstacleInterface} works as a packer
 * It requires an un-positioned obstacle to instantiate {@link PositionedObstacleInterface}
 * 
 * @author Stefan
 * @Editor Stefan Jon
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
	
	@Override
	public ObstacleInterface getUnpositionedObstacle(){
		return this.unpositionedObj;
	}
	
	@Override
	public String getName() {
		return getUnpositionedObstacle().getName();
	}
	@Override
	public double getHeight() {
		return getUnpositionedObstacle().getHeight();
	}
	@Override
	public double getRadius() {
		return getUnpositionedObstacle().getRadius();
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
