package interfaces;

import exceptions.InvalidIdentifierException;
import exceptions.UnusableRunwayException;
import exceptions.VariableDeclarationException;


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
	
	/**
	 * Valid 'letter's are 'C', 'L' or 'R'
	 * By changing the smallest side letter, this method must 
	 * automatically update the other side as well.
	 * @throws VariableDeclarationException 
	 */
	void setSmallestSideLetter(char letter) throws VariableDeclarationException;
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
	double getStripEndSideLength();
	
	
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
	
	double getBlastAllowanceDistance();
//=====================================================	

//====[ Obstacles ]====================================
	/**
	 * Returns null if there is no Obstacle or a PositionedObstacle implementation
	 */
	PositionedObstacleInterface getPositionedObstacle();
	
	/**
	 * Changing/adding the obstacle on the tarmac. 
	 * Will cause a re-declaration  of both declared runways to be stored in this class.
	 * 
	 * Even though an ObstacleInterface is used to add an object,
	 *  a PositionedObstacleObject must be stored. An internal conversion must be made.
	 * @throws UnusableRunwayException 
	 */
	//void addObstacle(ObstacleInterface obj, double distanceFromSmall, double distanceFromLarge) throws InvalidIdentifierException;
	
	void addObstacle(ObstacleInterface obj, String indentifier, double howFarIn) throws InvalidIdentifierException, UnusableRunwayException;
	
	
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
	//---[ Active ]--------------------------------------
	DeclaredRunwayInterface[] getRunways();
	
	DeclaredRunwayInterface getSmallAngledRunway();
	
	DeclaredRunwayInterface getLargeAngledRunway();
	
	//---[ Default/Origonal ]------------------------------
	DeclaredRunwayInterface[] getDefaultRunways();
	
	DeclaredRunwayInterface getDefaultLargeAngledRunway();

	DeclaredRunwayInterface getDefaultSmallAngledRunway();
//===============================================================


}
