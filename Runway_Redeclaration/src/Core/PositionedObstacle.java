package Core;


/**
 * This implementation of {@link PositionedObstacleInterface} works as a packer
 * It requires an un-positioned obstacle to instantiate {@link PositionedObstacleInterface}
 * 
 * @author Stefan
 * @Editor Stefan
 *
 */
public class PositionedObstacle implements PositionedObstacleInterface{
	private ObstacleInterface unpositionedObj;
	private int distance;
	private DeclaredRunwayInterface side;
	
	
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
	public DeclaredRunwayInterface getRunway() {
		return this.side;
	}
	@Override
	public double distanceFromEnd() {
		return this.distance;
	}
	
	
	

}
