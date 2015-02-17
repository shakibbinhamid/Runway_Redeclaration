
/**
 * 
 * This interface represents any obstacle that will obstruct the runway.
 * 
 * @author Stefan 
 * @Editors Stefan
 *
 */
public interface ObstacleInterface {
	
	/**
	 * The name of the object that is obstructing the runway
	 */
	String getName();
	
	/**
	 * The point further from the ground (in m).
	 * 
	 * This is in reference to the y perspective
	 */
	double getHeight();
	
	/** 
	 * The farthest point (in m) from the centre of the object.
	 * There are other smaller distances but we take the largest.
	 * 
	 * This is in reference to the x,z perspective
	 */
	double getRadius();
}
