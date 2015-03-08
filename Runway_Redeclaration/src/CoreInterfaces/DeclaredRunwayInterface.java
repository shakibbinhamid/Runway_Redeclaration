package CoreInterfaces;

import Exceptions.VariableDeclarationException;

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
	 * @return 'L' for left or 'R' for right or 'C' for centre
	 */
	char getSideLetter();
	
	void setSideLetter(char side) throws VariableDeclarationException;
	
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
	void setTORA(double tora) throws VariableDeclarationException;
	
	double getASDA();
	void setASDA(double asda) throws VariableDeclarationException;
	
	double getTODA();
	void setTODA(double toda) throws VariableDeclarationException;
	
	double getLDA();
	void setLDA(double lda) throws VariableDeclarationException;
	
	double getClearway();
	
	double getStopway();
	
	double getDisplacedThreshold();
	void setDisplacedThreshold(double dT) throws VariableDeclarationException;

	double getRESA();
	void setRESA(double resa) throws VariableDeclarationException;
	
	double getStartOfRoll();
	void setStartOfRoll(double roflmao) throws VariableDeclarationException;
	
//=====[ Mutators ]=================================================
	void resetToNoObstacle(DeclaredRunwayInterface original) throws VariableDeclarationException;

	void landOver(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException;
	
	void landTowards(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException;
	
	void takeOffAwayFrom(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException;
	
	void takeOffTowardsOver(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException;
	
//=====[ Angles ]====================================================
	double getDescentAngle();
	
	double getAscentAngle();
}
