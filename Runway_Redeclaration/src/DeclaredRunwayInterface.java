
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

	/** 
	 * The direction/side of the runway we are referring to.
	 * 
	 * @return 'L' for left or 'R' for right 
	 */
	char getDirection();
	
	/**
	 * The anti-clockwise angle of the that runway from North 
	 *   divided by and rounded to the nearest 10.
	 *   
	 *   e.g. 247degrees = 25 
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
	int getTORA();
	
	int getClearway();
	
	int getStopway();
	
	int getDisplacedThreshold();
	
	/**
	 * ASDA = TORA + Stopway
	 */
	int getASDA();
	
	/**
	 * TODA = TORA + Clearway
	 */
	int getTODA();
	
	/**
	 * LDA = TORA - Displaced Threshold
	 */
	int getDLA();
	
}
