package CoreInterfaces;
/**
 * 
 * This is an object that knows it's position in relation to a side of a runway
 * 
 * @author Stefan 
 * @Editor Stefan
 *
 */
public interface PositionedObstacleInterface extends ObstacleInterface{
	/**
	 * Distance in m aways from that side
	 * @return
	 */
	double distanceFromEnd();
	
}