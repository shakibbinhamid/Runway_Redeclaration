/**
 * The Tarmac refers to the area that the plane can take off from 
 * @author Stefan 
 * @Editor Stefan
 *
 */
public interface TarmacInterface {
	public static final int LEFT_RUNWAY = 5354;
	public static final int RIGHT_RUNWAY = 74448;
	
	
	/**
	 * The width or girth of the tarmac 
	 */
	double getWidth();
	
	/**
	 * The length of the tarmac (the initial TORA)
	 */
	double getHeight();
	
	ObstacleInterface getObstacle();
	
	/**
	 * Changing/adding the obstacle on the tarmac. 
	 * Will cause a re-declaration  of both declared runways to be stored in this class.
	 * 
	 * @param obj - The new obstacle on the runway 
	 * @param runwayIdentifier - The side it is closest to. Either TarmacInterface.LEFT_RUNWAY or TarmacInterface.RIGHT_RUNWAY
	 * @param howFarIn - How far from the chosen tarmac side 
	 * @return The two new declared Runways
	 */
	DeclaredRunwayInterface[] addObstacle(ObstacleInterface obj, int runwayIdentifier, int howFarIn);
	
	/** 
	 * Changes the obstacle that is on the runway to null 
	 */
	void removeObstacle();
	
	/**
	 *  true or false whether there is an obstacle on the tarmac
	 */
	boolean hasObstacle();
	
	
	
	
	DeclaredRunwayInterface[] getRunways();
	
	DeclaredRunwayInterface getLeftStartingRunway();
	
	DeclaredRunwayInterface getRightStartingRunway();

}
