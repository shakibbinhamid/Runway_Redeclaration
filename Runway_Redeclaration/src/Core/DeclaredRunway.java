package Core;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.PositionedObstacleInterface;
import Exceptions.UnusableRunwayException;
import Exceptions.VariableDeclarationException;

/**
 * When an airfield is made, the air traffic controller will declare a 'runway' which the planes will use to
 * take off and land on. This is a region of lets say tarmac that will be used. Any time an obstacle is on the 
 * tarmac strip the runway needs to be redeclared. This is creating a new RedeclaredRunway rather than mutating it.
 * 
 * It consists of 4 main attributes: 
 * 	-TORA 
 *  -Stopway
 *  -Clearway
 *  -Displaced Threshold
 *  
 * We call them inert as they are not dependent on other variables once declared
 *  All other attributes are calculated and not inert, as they depend on these 4 variables.
 *  
 *  If due to some obstacle this side of the runway is too short and hence unusable, any attempt to get the distances
 *   (inert and calculated) will result in a UnusableRunwayException.
 *  
 * @author Shakib
 * @Editor Stefan Shakib
 * @Tester Stefan
 *
 */
@Root
class DeclaredRunway implements DeclaredRunwayInterface{
	@Element
	private double decTora, disThreshold, stopway, clearway;
	
	/** Ratio Format 1:n NOT DEGREES*/
	public static final int DEFAULT_DESC_ANGLE = 50;
	/** Ratio Format 1:n NOT DEGREES*/
	public static final int DEFAULT_ASC_ANGLE = 50;
	
	
	//TODO Use these!
	private double decLda, decAsda, decToda;
	
	@Element
	private int angle;
	@Element
	private char direction;

	protected static final double DEFAULT_RESA = 240;

	/**
	 * An Airfield has all the relevant information needed to declare a runway except we need the 
	 * angleFromNorth to know which end of the tarmac we are heading from.s
	 * 
	 * @param runway - The airfield with the runway on it that you want to redeclare
	 * @param angleFromNorth - The angle (in degrees '�') divided by 10 and rounded to the nearest 10
	 * 
	 * @throws VariableDeclarationException is thrown when an invalid distance variable is declared
	 */

	//nullary constructor
	protected DeclaredRunway(){

	}
	
	protected DeclaredRunway(AirfieldInterface runway ,int angleFromNorth) throws VariableDeclarationException{

		setDisplacedThreshold(0);
		if(!runway.hasObstacle()){

			setTORA(runway.getRunwayLength());
			setStopway(runway.getInitialStopway());

			double clearway = this.stopway+runway.getStripEndSideLength();
			setClearway(clearway);

		}else{
			//These are garuneteed to fail, as we have not handled the obstacle scenarios yet!
			//TODO handle an obstacle
			setTORA(-1);
			setStopway(-1);
			setClearway(-1);
		}
		//TODO include final check: TORA <= ASDA <= TODA 

		setAngle(angleFromNorth);
		direction = ' ';
	}

	//This is the new one
	protected DeclaredRunway(AirfieldInterface airfield, int angleFromNorth,
			double tora, double stopway, double clearway, double displacedThreshold) throws VariableDeclarationException{

		setTORA(tora);
		setStopway(stopway);
		setClearway(clearway);
		setDisplacedThreshold(displacedThreshold);
		
		setLDA(this.decTora - displacedThreshold);
		setASDA(this.decTora + clearway);
		setTODA(this.decTora + stopway);
		
		if(!(this.decTora <= this.decAsda && this.decAsda <= this.decToda)) 
			throw new VariableDeclarationException("TORA, ASDA, TODA", new double[] {decTora,decAsda,decToda}, "TORA <= ASDA <= TODA");
		

		setAngle(angleFromNorth);
		this.direction = ' ';

	}

	//====[ Direction Methods  ]=====================================
	//----[ Getters ]------------------------------------------------------
	@Override
	public char getSideLetter() {
		return direction;
	}

	@Override
	/**
	 * The angle will be in actual degrees i.e. 270� or 51� 
	 * Note: they are not the shortened 2sf version used in the identifier 
	 */
	public int getAngle() {
		return angle;
	}

	@Override
	/**
	 * The identifier consists of the angle rounded to the 
	 * nearest 10 and divided by 10 followed by the its parallel 
	 * runway side character.
	 * 
	 * e.g. 289� on the left side = "29L"
	 */
	public String getIdentifier() {
		String out = "";
		if(angle<100){
			out += "0";
		}
		out += String.valueOf(Math.round((double)angle/10))+direction;
		return out;
	}
	//----[ Setters ]---------------------------------------------------s---
	@Override
	/**
	 * The character that identifies a runway from a group of parallel,
	 *  the valid option for a parallel runway is 'L', 'R' or 'C'
	 *  If the runway is not parallel to any other runways then it must be ' ' i.e. empty space or null char
	 *  
	 *  @throws VariableDeclarationException - When the option is not one of the above options
	 */
	public void setSideLetter(char leftOrRight) throws VariableDeclarationException{
		//If LoR is not L or R
		if ( !(leftOrRight == ' ' || leftOrRight == 'L' || leftOrRight == 'R' || leftOrRight == 'C') )
			throw new VariableDeclarationException("Direction",leftOrRight,"Must be 'L', 'C', 'R' or space ");

		this.direction = leftOrRight;
	}

	/**
	 * Accepts any angle of degree but stores it as a value of v where:  0� <= v < 360�
	 */
	public void setAngle(int angle) {
		while(angle<0){
			angle += 360;
		}
		this.angle = angle%360;
	}


	//====[ Inert Distance Methods  ]=====================================
	//----[ Getters ]------------------------------------------------------
	@Override
	public double getTORA() throws UnusableRunwayException {
		return decTora;
	}
	@Override
	public double getClearway()throws UnusableRunwayException {
		return clearway;
	}
	@Override
	public double getStopway() throws UnusableRunwayException {
		return stopway;
	}
	@Override
	public double getDisplacedThreshold() throws UnusableRunwayException {
		return disThreshold;
	}
	@Override
	public double getRESA() throws UnusableRunwayException{
		return DEFAULT_RESA;
	}
	@Override
	public double getTODA() throws UnusableRunwayException{
		return this.decToda;
	}
	@Override
	public double getASDA() throws UnusableRunwayException{
		return this.decAsda;
	}
	@Override
	public double getLDA() throws UnusableRunwayException {
		return this.decLda;
	}
	//----[ Setters ]---------------------------------------------------------
	/**
	 * @throws VariableDeclarationException - when the 'tora' is not > 0
	 */
	private void setTORA(double tora) throws VariableDeclarationException{
		if(tora <= 0) throw new VariableDeclarationException("TORA", tora, "TORA > 0");

		this.decTora = tora;
	}
	/**
	 * @throws VariableDeclarationException - when 'threshold'  is not >= 0
	 */
	private void setDisplacedThreshold(double threshold) throws VariableDeclarationException{
		if(threshold < 0) throw new VariableDeclarationException("Displaced Threshold", threshold, "Threshold >= 0");

		this.disThreshold = threshold;
	}
	/**
	 * @throws VariableDeclarationException - when 'stopway'  is not >= 0
	 */
	private void setStopway(double stopway) throws VariableDeclarationException{
		if( stopway < 0 ) throw new VariableDeclarationException("Stopway", stopway, "Stopway >= 0");
		
		this.stopway = stopway;
	}
	/**
	 * @throws VariableDeclarationException - when 'clearway'  is not >= 0 OR when 'clearway' < getStopway
	 */
	private void setClearway(double clearway) throws VariableDeclarationException{
		if( clearway < 0 ) throw new VariableDeclarationException("Clearway", clearway, "Clearway > 0");
		if( clearway < this.stopway ) throw new VariableDeclarationException("Clearway", clearway, "Clearway >= Stopway");

		this.clearway = clearway;
	}
	/**
	 * @throws VariableDeclarationException - when 'lda' is not >= 0
	 */
	private void setLDA(double lda) throws VariableDeclarationException{
		if ( lda  < 0 ) throw new VariableDeclarationException("LDA", lda, "LDA >= 0");
		
		this.decLda = lda;
	}
	private void setASDA(double asda) throws VariableDeclarationException{
		if ( asda  <= 0 ) throw new VariableDeclarationException("ASDA", asda, "ASDA > 0");
		
		this.decAsda = asda;
	}
	private void setTODA(double toda) throws VariableDeclarationException{
		if ( toda  < 0 ) throw new VariableDeclarationException("TODA", toda, "TODA > 0");
		
		this.decToda = toda;
	}
	//====[ Calculated Distance Methods  ]=====================================
	
	//====[ Mutators ]=============================================================

	@Override
	public void landOver(DeclaredRunwayInterface original, AirfieldInterface parent) throws UnusableRunwayException, VariableDeclarationException {
		
		double distFromObs = distanceFrom(parent.getObstacle());
		double RESA = DEFAULT_RESA;
		double ALS = parent.getObstacle().getHeight() * DEFAULT_DESC_ANGLE;
		
		double largestFactor = Math.max(Math.max(RESA,ALS)+parent.getStripEndSideLength(), Airfield.BLAST_PROT);
		double newLDA = original.getLDA() - largestFactor -  distFromObs;
		setLDA(newLDA);
			
	}

	@Override
	public void landTowards(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException {
		double distFromObs = distanceFrom(parent.getObstacle());
		double resa = DEFAULT_RESA;
		
		double newLDA = distFromObs - resa - parent.getStripEndSideLength();
		setLDA(newLDA);
	}

	@Override
	public void takeOffAwayFrom(DeclaredRunwayInterface original, AirfieldInterface parent) throws UnusableRunwayException, VariableDeclarationException {
		//ASSUMPTION: stopway is part of clearway
		
		double distFromObs = distanceFrom(parent.getObstacle());
		
		double newTORA = original.getTORA() - distFromObs - Airfield.BLAST_PROT;
		setTORA(newTORA);
	}

	@Override
	public void takeOffTowardsOver(DeclaredRunwayInterface original, AirfieldInterface parent) throws UnusableRunwayException, VariableDeclarationException {
		double distFromObs = distanceFrom(parent.getObstacle());
		double ALS = getAscentAngle()*parent.getObstacle().getHeight();
		
		double newTORA = distFromObs + getDisplacedThreshold() - ALS - parent.getStripEndSideLength();
		
		setTORA(newTORA);
		setASDA(newTORA); //Stopway blocked
		setTODA(newTORA);//clearway blocked
		setStopway(0);
		setClearway(0);
	}

	private boolean isSmallEnd(){
		return this.angle<180;
	}

	/**
	 * Returns the distance from threshold to the nearest part of the obstacle
	 */
	private double distanceFrom(PositionedObstacleInterface obs){
		if(this.isSmallEnd()){
			return obs.distanceFromSmallEnd()-obs.getRadius();
		}else{
			return obs.distanceFromLargeEnd()-obs.getRadius();
		}
	}
	
	@Override
	public int getDescentAngle() {
		return DEFAULT_DESC_ANGLE;
	}

	@Override
	public int getAscentAngle() {
		return DEFAULT_ASC_ANGLE;
	}






}
