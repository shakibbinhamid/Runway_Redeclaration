
public interface TarmacInterface {
	public static final int LEFT_RUNWAY = 5354;
	public static final int RIGHT_RUNWAY = 74448;
	
	
	
	int getWidth();
	
	int getHeight();
	
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
	
	void removeObstacle();
	
	boolean hasObstacle();
	
	
	
	
	DeclaredRunwayInterface[] getRunways();
	
	DeclaredRunwayInterface getLeftStartingRunway();
	
	DeclaredRunwayInterface getRightStartingRunway();

}
