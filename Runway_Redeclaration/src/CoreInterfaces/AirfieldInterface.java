package CoreInterfaces;


/**
 * //TODO add a docstring
 *  
 * @author Stefan 
 * @Editor Stefan
 *
 */
public interface AirfieldInterface {
//====[ Misc Methods ]===========================================
	String getName();
	
	int getSmallestAngleFromNorth();
	
	/**
	 * Which one of the parallel runways you are referring to. 
	 * In the context of the smallest angle.
	 */
	char getSideLetter();
	
	/**
	 * Valid 'letter's are 'C', 'L' or 'R'
	 */
	void setSideLetter(char letter);
//===============================================================

//====[ Inert Distance Values ]==================================
	/**
	 * The width or girth of the tarmac runway
	 */
	double getRunwayWidth();
	
	/**
	 * The length of the tarmac runway (the initial TORA)
	 */
	double getRunwayLength();
	
	/**
	 * (150m horiz on diagram)
	 */
	double getInitialStopway();
	
	/**
	 * The extra distance either side (behind) the length of the runway 
	 * that make up longest reach of the cleared and graded Area
	 * 
	 * (60m horizontal on the diagram)
	 */
	double getClearedLength();
	
	
	/**
	 * The distance between the start of the runway and the
	 * long cleared spacer
	 *
	 * (300m horiz on the diagram)
	 */
	double distanceToLongSpacer();
	
	
	/**
	 * (75m vertical in diagram)
	 */
	double getShortClearedWidthSpacer();
	
	/**
	 * (105m vertical in diagram)
	 */
	double getLongClearedWidthSpacer();
	
	/**
	 * (150m vertical in diagram)
	 */
	double getFullWidthSpacer();
//=====================================================	

//====[ Obstacles ]====================================
	/**
	 * Returns null if there is no Obstacle or a PositionedObstacle implementation
	 */
	PositionedObstacleInterface getObstacle();
	
	/**
	 * Changing/adding the obstacle on the tarmac. 
	 * Will cause a re-declaration  of both declared runways to be stored in this class.
	 * 
	 * Even though an ObstacleInterface is used to add an object,
	 *  a PositionedObstacleObject must be stored. An internal conversion must be made.
	 * 
	 * @param obj - The new obstacle on the runway 
	 * @param runwayIdentifier - The side it is closest to. Either TarmacInterface.LEFT_RUNWAY or TarmacInterface.RIGHT_RUNWAY
	 * @param howFarIn - How far from the chosen tarmac side 
	 */
	void addObstacle(ObstacleInterface obj, String identifier, double howFarIn);
	
	
	
	/** 
	 * Changes the obstacle that is on the runway to null 
	 */
	void removeObstacle();
	
	/**
	 *  true or false whether there is an obstacle on the tarmac
	 */
	boolean hasObstacle();
//===============================================================
	
	
//====[ Declared Runways ]=======================================
	DeclaredRunwayInterface[] getRunways();
	
	DeclaredRunwayInterface getSmallAngledRunway();
	
	DeclaredRunwayInterface getLargeAngledRunway();
	
	String getSmallIdentifier();
	
	String getLargeIdentifier();
//===============================================================
	
}
