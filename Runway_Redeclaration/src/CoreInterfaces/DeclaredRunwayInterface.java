package CoreInterfaces;

/**
 * 
 * This represents a newly declared runway.
 *  It will include any it's angle and direction to identify it and the 
 * 
 * @author Stefan
 * @Editors Stefan
 *
 */
public interface DeclaredRunwayInterface {
	
	static final int DEFAULT_ANGLE_OF_ASCENT = 50;
	static final int DEFAULT_ANGLE_OF_DESCENT = 50;

	/** 
	 * The direction/side of the runway we are referring to.
	 * 
	 * @return 'L' for left or 'R' for right or 'C' for centre
	 */
	char getSideLetter();
	
	/**
	 * The anti-clockwise angle in degrees of the that runway from North
	 * e.g. 150 (degrees) 
	 */
	int getAngle();
	
	/**
	 * The way of uniquely identifying a runway
	 * e.g. 27R  or 03L
	 * @return 
	 */
	String getIdentifier();
	
	/**
	 * Take-Off-Runway-Available
	 */
	double getTORA();
	
	double getClearway();
	
	double getStopway();
	
	double getDisplacedThreshold();
	
	/**
	 * ASDA = TORA + Stopway
	 */
	double getASDA();
	
	/**
	 * TODA = TORA + Clearway
	 */
	double getTODA();
	
	/**
	 * LDA = TORA - Displaced Threshold
	 */
	double getLDA();
	
	int getAngleOfAscent();
	
	int getAngleOfDescent();
	
}
