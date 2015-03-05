package Core;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;
import CoreInterfaces.Savable;
import Exceptions.InvalidIdentifierException;
import Exceptions.VariableDeclarationException;

@Root
/**
 * 
 * @author Stefan
 * @Editor Stefan Shakib
 * @Testor Stefan
 */
public class Airfield implements AirfieldInterface, Savable {
	public static final double BLAST_PROT_RADIUS = 300;

	@ElementArray
	private DeclaredRunwayInterface[] runways, defaultRunways;
	@Element (required = false)
	private PositionedObstacle obstacle;
	
	@Element
	private double runGirth, lStripEnd, rStripEnd, longSpacer, shortSpacer, mediumSpacer, shortLength, longLength;
	@Element 
	private double blastProt;
	
	//nullary constructor
	protected Airfield(){}

	protected Airfield(int angleFromNorth, double[] dimensions, double[] smallAngledDistances, double[] largeAngledDistances) throws VariableDeclarationException{
		//Checks
		if(dimensions.length != 8) throw new VariableDeclarationException("lengths", dimensions, "Needs to be 8 cells");
		if(smallAngledDistances.length != 5)throw new VariableDeclarationException("smallAngledDistances", smallAngledDistances, "Needs to be 4 cells");
		if(largeAngledDistances.length != 5)throw new VariableDeclarationException("largeAngledDistances", largeAngledDistances, "Needs to be 4 cells");

		//Dimensions
		setRunwayGirth  (dimensions[0]);
		setLeftStripEnd (dimensions[1]);
		setRightStripEnd(dimensions[2]);
		setLongSpacer   (dimensions[3]);
		setShortSpacer  (dimensions[4]);
		setMediumSpacer (dimensions[5]);
		setShortLength  (dimensions[6]);
		setLongLength   (dimensions[7]);
		//TODO Check these values against CAA stuff in INCREMENT 2

		setBlastAllowance(BLAST_PROT_RADIUS);
		
		this.obstacle = null;

		
		//--[ Handle Runways ]-----
		this.runways = new DeclaredRunwayInterface[2];
		this.defaultRunways= new DeclaredRunwayInterface[2];

		//small angled runway stuff
		double lTORA = smallAngledDistances[0];
		double lASDA = smallAngledDistances[1];
		double lTODA = smallAngledDistances[2];
		double lLDA =  smallAngledDistances[3];

		angleFromNorth %= 180;
		this.runways[0] = new DeclaredRunway(this, angleFromNorth, lTORA, lASDA, lTODA, lLDA);
		this.defaultRunways[0] = new DeclaredRunway(this, angleFromNorth, lTORA, lASDA, lTODA, lLDA);

		
		//large angled runway stuff
		double rTORA = largeAngledDistances[0];
		double rASDA = largeAngledDistances[1];
		double rTODA = largeAngledDistances[2];
		double rLDA =  largeAngledDistances[3];

		this.runways[1] = new DeclaredRunway(this, angleFromNorth+180, rTORA, rASDA, rTODA, rLDA);
		this.defaultRunways[1] = new DeclaredRunway(this, angleFromNorth+180, rTORA, rASDA, rTODA, rLDA);
	}
	
	public static final double DEFAULT_GIRTH = 100;
	public static final double DEFAULT_LEFT_STRIP_END = 60;
	public static final double  DEFAULT_RIGHT_STRIP_END = 60;
	public static final double DEFAULT_LONG_SPACER = 150;
	public static final double DEFAULT_SHORT_SPACER = 75;
	public static final double DEFAULT_MEDIUM_SPACER = 105;
	public static final double DEFAULT_SHORT_LENGTH = 150;
	public static final double DEFAULT_LONG_LENGTH = 300;

	/**
	 * Default value constructor
	 */
	protected Airfield(int angleFromNorth, double[] smallAngledDistances, double[] largeAngledDistances) throws VariableDeclarationException{
		this(angleFromNorth, new double[] { DEFAULT_GIRTH, DEFAULT_LEFT_STRIP_END, 
											DEFAULT_RIGHT_STRIP_END, DEFAULT_LONG_SPACER, 
											DEFAULT_SHORT_SPACER, DEFAULT_MEDIUM_SPACER, 
											DEFAULT_SHORT_LENGTH, DEFAULT_LONG_LENGTH}, 
			 smallAngledDistances,largeAngledDistances);
	}
	
	@Override
	public double getRunwayGirth() { return this.runGirth; }
	@Override
	public double getLeftStripEnd() { return this.lStripEnd; }
	@Override
	public double getRightStripEnd() { return this.rStripEnd; }
	@Override
	public double getLongSpacer() { return this.longSpacer; }
	@Override
	public double getMediumSpacer() { return this.mediumSpacer; }
	@Override
	public double getShortSpacer() { return this.shortSpacer; }
	@Override
	public double getShortLength() { return this.shortLength; }
	@Override
	public double getLongLength() { return longLength; }
	@Override
	public double getBlastAllowance() { return this.blastProt; }
	
	
	@Override
	public void setRunwayGirth(double rGirth) { this.runGirth = rGirth; }
	@Override
	public void setLeftStripEnd(double lStrip) { this.lStripEnd = lStrip; }
	@Override
	public void setRightStripEnd(double rStrip) { this.rStripEnd = rStrip; }
	@Override
	public void setLongSpacer(double lSpacer) { this.longSpacer = lSpacer; }
	@Override
	public void setMediumSpacer(double medSpacer) { this.mediumSpacer = medSpacer; }
	@Override
	public void setShortSpacer(double sSpacer) { this.shortSpacer = sSpacer; }
	@Override
	public void setShortLength(double sLength) { this.shortLength = sLength; }
	@Override
	public void setLongLength(double lLength) { this.longLength = lLength; }
	@Override
	public void setBlastAllowance(double blast) { this.blastProt = blast; }
	
	@Override
	public double getTotalWidth() {
		return getLeftStripEnd()+longestTORA()+getRightStripEnd();
	}
	
	private double longestTORA(){ 
		if (getSmallAngledRunway().getTORA() > getLargeAngledRunway().getTORA()){
			return getSmallAngledRunway().getTORA();
		}else{
			return getLargeAngledRunway().getTORA();
		}
	}

	@Override
	public double getTotalHeight() {
		return getLongSpacer()*2;
	}

	/**
	 * We redeclare when there is an object added or removed, 
	 * without allowing the user to choose which side does what
	 * 
	 * @throws VariableDeclarationException
	 * @throws  
	 */
	private void redeclareRunways() throws VariableDeclarationException{
		getSmallAngledRunway().resetToNoObstacle(getDefaultSmallAngledRunway());
		getLargeAngledRunway().resetToNoObstacle(getDefaultLargeAngledRunway());

		//Excuse me sir we seem to have a collision imminent, CHANGE YOUR JAM! SORT DAT SHEET OUT MUN!
		if(hasObstacle()){
			//OK MUN, WE GOTS TO DECIDE IF DEM PROBLEMS IS NEAREREST TO WHAT SIDE OF DEM AIRPORT ROAD TINGS
			if(getPositionedObstacle().distanceFromSmallEnd() < getPositionedObstacle().distanceFromLargeEnd()){
				//closer to small angled end

				getSmallAngledRunway().takeOffAwayFrom(getDefaultSmallAngledRunway(), this);
				getSmallAngledRunway().landOver(getDefaultSmallAngledRunway(), this);

				getLargeAngledRunway().takeOffTowardsOver(getDefaultLargeAngledRunway(), this);
				getLargeAngledRunway().landTowards(getDefaultLargeAngledRunway(), this);

			}else{
				//closer to large angled end or equal hence IT DONT MATTER BRUV

				getSmallAngledRunway().takeOffTowardsOver(getDefaultSmallAngledRunway(), this);
				getSmallAngledRunway().landTowards(getDefaultSmallAngledRunway(), this);

				getLargeAngledRunway().takeOffAwayFrom(getDefaultLargeAngledRunway(), this);
				getLargeAngledRunway().landOver(getDefaultLargeAngledRunway(), this);
			}
		}
		//ALL DEM CHANGES IS MADE MUN, Go chill dem plane drivers sort da rest of it out mun ;)
		//Wait for the stewardesses, it's safe ;*
		System.out.println( ((DeclaredRunway)getSmallAngledRunway()).getLog() );
		System.out.println( ((DeclaredRunway)getLargeAngledRunway()).getLog() );

	}

	
	/**
	 * We redeclare when there is an object added or removed, 
	 * without allowing the user to choose which side does what
	 * 
	 * @throws VariableDeclarationException
	 * @throws  
	 */
	public void redeclareRunways(boolean smallLandTowards, boolean smallTakeOffTowards, 
			boolean largeLandTowards, boolean largeTakeOffTowards) 
					throws VariableDeclarationException{
		getSmallAngledRunway().resetToNoObstacle(getDefaultSmallAngledRunway());
		getLargeAngledRunway().resetToNoObstacle(getDefaultLargeAngledRunway());

		//Excuse me sir we seem to have a collision imminent, CHANGE YOUR JAM! SORT DAT SHEET OUT MUN!
		if(hasObstacle()){
			//OK MUN, WE GOTS TO DECIDE IF DEM PROBLEMS IS NEAREREST TO WHAT SIDE OF DEM AIRPORT ROAD TINGS
			if(getPositionedObstacle().distanceFromSmallEnd() < getPositionedObstacle().distanceFromLargeEnd()){
				//closer to small angled end

				getSmallAngledRunway().takeOffAwayFrom(getDefaultSmallAngledRunway(), this);
				getSmallAngledRunway().landOver(getDefaultSmallAngledRunway(), this);

				getLargeAngledRunway().takeOffTowardsOver(getDefaultLargeAngledRunway(), this);
				getLargeAngledRunway().landTowards(getDefaultLargeAngledRunway(), this);

			}else{
				//closer to large angled end or equal hence IT DONT MATTER BRUV

				getSmallAngledRunway().takeOffTowardsOver(getDefaultSmallAngledRunway(), this);
				getSmallAngledRunway().landTowards(getDefaultSmallAngledRunway(), this);

				getLargeAngledRunway().takeOffAwayFrom(getDefaultLargeAngledRunway(), this);
				getLargeAngledRunway().landOver(getDefaultLargeAngledRunway(), this);
			}
		}
		//ALL DEM CHANGES IS MADE MUN, Go chill dem plane drivers sort da rest of it out mun ;)
		//Wait for the stewardesses, it's safe ;*
		System.out.println( ((DeclaredRunway)getSmallAngledRunway()).getLog() );
		System.out.println( ((DeclaredRunway)getLargeAngledRunway()).getLog() );

	}

	@Override
	public String getName(){
		return this.getSmallAngledRunway().getIdentifier()+"/"+this.getLargeAngledRunway().getIdentifier();
	}

	@Override
	/**
	 * Sets the smallest side letter, please note that the 
	 */
	public void setSmallestSideLetter(char letter) throws VariableDeclarationException {
		getSmallAngledRunway().setSideLetter(letter);

		//Change other side as well
		if(letter=='C'){
			getLargeAngledRunway().setSideLetter(letter);
		}else if(letter == 'R'){
			getLargeAngledRunway().setSideLetter('L');
		}else{//letter must == 'L'
			getLargeAngledRunway().setSideLetter('R');
		}
	}


	@Override
	public PositionedObstacleInterface getPositionedObstacle() {
		return this.obstacle;
	}

	/**/@Override
	public void addObstacle(ObstacleInterface obj,
			double distanceFromSmall, double distanceFromLarge) throws InvalidIdentifierException {/**/

		this.obstacle = new PositionedObstacle(obj, distanceFromSmall, distanceFromLarge);
		try {
			this.redeclareRunways();


		} catch (VariableDeclarationException  e) {
			System.err.println("Stefan Here: This really should not happen! ... me thinks");
			e.printStackTrace();
		}
	}
	
	/*@Override
	 @Deprecated
	public void addObstacle(ObstacleInterface obj, String indentifier, double howFarIn) 
			throws InvalidIdentifierException  {

		//Figuring where dat ting is near bruv... 
		if(indentifier.equals(this.getSmallAngledRunway().getIdentifier())){
			double otherhowFarIn = this.getSmallAngledRunway().getTORA()-this.getSmallAngledRunway().getDisplacedThreshold()-this.getLargeAngledRunway().getDisplacedThreshold()-howFarIn;
			this.obstacle = new PositionedObstacle(obj,howFarIn, otherhowFarIn);

		}else if(indentifier.equals(this.getLargeAngledRunway().getIdentifier())){
			double otherhowFarIn = this.getSmallAngledRunway().getTORA()-this.getLargeAngledRunway().getDisplacedThreshold()-this.getSmallAngledRunway().getDisplacedThreshold()-howFarIn;
			this.obstacle = new PositionedObstacle(obj,otherhowFarIn, howFarIn);
			
		}else{
			//Excuse me! I don't own one of those, how dare you suggest such a thing!
			throw new InvalidIdentifierException(indentifier, this);
		} 
		try {
			this.redeclareRunways();


		} catch (VariableDeclarationException  e) {
			System.err.println("Stefan Here: This really should not happen! ... me thinks");
			e.printStackTrace();
		}
		*/
		 

	@Override
	public void removeObstacle() {
		this.obstacle = null;
		try {
			this.getSmallAngledRunway().resetToNoObstacle(getDefaultSmallAngledRunway());
			this.getLargeAngledRunway().resetToNoObstacle(getDefaultLargeAngledRunway());
			
		} catch (VariableDeclarationException e) {
			System.err.println("Stefan Again: Urm I don't think any of this should have happened...");
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasObstacle() {
		return !(getPositionedObstacle()==null);
	}

	@Override
	public DeclaredRunwayInterface[] getRunways() {
		return this.runways;
	}

	@Override
	public DeclaredRunwayInterface getSmallAngledRunway()  {
		return this.runways[0];
	}

	@Override
	public DeclaredRunwayInterface getLargeAngledRunway() {
		return this.runways[1];
	}


	@Override
	public DeclaredRunwayInterface[] getDefaultRunways() {
		return this.defaultRunways;
	}

	@Override
	public DeclaredRunwayInterface getDefaultSmallAngledRunway(){
		return this.defaultRunways[0];
	}

	@Override
	public DeclaredRunwayInterface getDefaultLargeAngledRunway(){
		return this.defaultRunways[1];
	}
	
	public String toString(){
		return "Airfield: "+getName();
		
	}

	@Override
	public double[] getDimensionsToArray() {
		return new double[] {runGirth, lStripEnd, rStripEnd, longSpacer, shortSpacer, mediumSpacer, shortLength, longLength};
	}

	@Override
	public void addObstacle(ObstacleInterface obj, double distanceFromSmall, double distanceFromLarge, 
			boolean smallLandTowards,boolean smallTakeOffTowards, 
			boolean largeLandTowards,boolean largeTakeOffTowards) 
					throws InvalidIdentifierException, VariableDeclarationException {
		
		this.obstacle = new PositionedObstacle(obj, distanceFromSmall, distanceFromLarge);
		
		this.getSmallAngledRunway().resetToNoObstacle(getDefaultSmallAngledRunway());
		this.getLargeAngledRunway().resetToNoObstacle(getDefaultLargeAngledRunway());
		
		if(smallTakeOffTowards){
			this.getSmallAngledRunway().takeOffTowardsOver(getDefaultSmallAngledRunway(), this);
		}else{
			this.getSmallAngledRunway().takeOffAwayFrom(getDefaultSmallAngledRunway(), this);
		}
		if(smallLandTowards){
			this.getSmallAngledRunway().landTowards(getSmallAngledRunway(), this);
		}else{
			this.getSmallAngledRunway().landOver(getDefaultSmallAngledRunway(), this);
		}
		
		
		if(largeTakeOffTowards){
			this.getLargeAngledRunway().takeOffTowardsOver(getDefaultLargeAngledRunway(), this);
		}else{
			this.getLargeAngledRunway().takeOffAwayFrom(getDefaultLargeAngledRunway(), this);
		}
		if(largeLandTowards){
			this.getLargeAngledRunway().landTowards(getDefaultLargeAngledRunway(), this);
		}else{
			this.getLargeAngledRunway().landOver(getDefaultLargeAngledRunway(), this);
		}
		
	}
}
