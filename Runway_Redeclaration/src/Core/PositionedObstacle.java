package Core;

import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;


/**
 * This implementation of {@link PositionedObstacleInterface} works as a packer
 * It requires an un-positioned obstacle to instantiate {@link PositionedObstacleInterface}
 * 
 * @author Stefan
 * @Editor Stefan
 * @Testor Stefan
 *
 */
public class PositionedObstacle implements PositionedObstacleInterface{
	private ObstacleInterface unpositionedObj;
	private double distanceSmall, distanceLarge;
	
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
