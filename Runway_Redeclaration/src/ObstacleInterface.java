
/**
 * 
 * This interface represents any obstacle that will obstruct the runway.
 * 
 * @author Stefan 
 * @Editors Andrew Stefan
 *
 */
public interface ObstacleInterface {
	
	/**
	 * The point further from the ground (in m).
	 * 
	 * This is in reference to the y perspective
	 */
	int getHeight();
	
	/** 
	 * The farthest point (in m) from the centre of the object.
	 * There are other smaller distances but we take the largest.
	 * 
	 * This is in reference to the x,z perspective
	 */
	int getRadius();
	
	/**
	 * The distance (in m) from the end of the runway with the smallest angle.
	 */
	int getDistanceFromLeft();
	
	/**
	 * The distance (in m) from the end of the runway with the largest angle.
	 */
	int getDistanceFromRight();

}
