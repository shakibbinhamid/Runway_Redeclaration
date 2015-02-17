/**
 * 
 * This is an object that knows it's position in relation to a side of a runway
 * 
 * @author Stefan 
 * @Editor Stefan
 *
 */
public interface PositionedObstacle extends ObstacleInterface{
	
	DeclaredRunwayInterface getRunway();
	
	int distanceFromEnd();
	
}
