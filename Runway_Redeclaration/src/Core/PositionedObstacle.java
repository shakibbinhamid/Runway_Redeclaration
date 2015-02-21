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
	private double distance;
	
	public PositionedObstacle(ObstacleInterface obj, double howFarInFromSmallAngledSide){
		this.unpositionedObj=  obj;
		this.distance = howFarInFromSmallAngledSide;
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
	public double distanceFromEnd() {
		return this.distance;
	}
}
