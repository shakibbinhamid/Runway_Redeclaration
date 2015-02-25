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
import Exceptions.UnusableRunwayException;
import Exceptions.VariableDeclarationException;

@Root
/**
 * 
 * @author Stefan
 * @Editor Stefan Shakib
 * @Testor Stefan
 */
class Airfield implements AirfieldInterface, Savable {
	@ElementArray
	private DeclaredRunwayInterface[] runways,defaultRunways;
	@Element (required = false)
	private PositionedObstacle obstacle;
	@Element
	private double runWidth,runLen,initStop,stripEndL,dToLongSpace,shortClearWSpace,longClearWSpace,fullWSpace;

	public static final double BLAST_PROT = 300;


	//nullary constructor
	protected Airfield(){

	}

	//TODO change the dimensions to hanlde asymetric runways (Yeh that shite)
	Airfield(int angleFromNorth, double[] dimensions, double[] smallAngledDistances, double[] largeAngledDistances) throws VariableDeclarationException{
		//Checks
		if(dimensions.length != 8) throw new VariableDeclarationException("lengths", dimensions, "Needs to be 8 cells");
		if(smallAngledDistances.length != 4)throw new VariableDeclarationException("smallAngledDistances", smallAngledDistances, "Needs to be 4 cells");
		if(largeAngledDistances.length != 4)throw new VariableDeclarationException("largeAngledDistances", largeAngledDistances, "Needs to be 4 cells");

		//Dimensions
		this.runWidth = dimensions[0];
		this.runLen = dimensions[1];
		this.initStop = dimensions[2];
		this.stripEndL = dimensions[3];
		this.dToLongSpace = dimensions[4];
		this.shortClearWSpace = dimensions[5];
		this.longClearWSpace = dimensions[6];
		this.fullWSpace = dimensions[7];
		//TODO Check these values against CAA stuff in INCREMENT 2

		this.obstacle = null;

		this.runways = new DeclaredRunway[2];
		this.defaultRunways= new DeclaredRunway[2];

		//small angled runway stuff
		double sTORA = smallAngledDistances[0];
		double sStopway = smallAngledDistances[1];
		double sClearway = smallAngledDistances[2];
		double sDisThresh = smallAngledDistances[3];

		angleFromNorth %= 180;
		this.runways[0] = new DeclaredRunway(this, angleFromNorth, sTORA, sStopway, sClearway, sDisThresh);
		this.defaultRunways[0] = new DeclaredRunway(this, angleFromNorth, sTORA, sStopway, sClearway, sDisThresh);


		//large angled runway stuff
		double lTORA = largeAngledDistances[0];
		double lStopway = largeAngledDistances[1];
		double lClearway = largeAngledDistances[2];
		double lDisThresh = largeAngledDistances[3];

		this.runways[1] = new DeclaredRunway(this, angleFromNorth+180, lTORA, lStopway, lClearway, lDisThresh);
		this.defaultRunways[1] = new DeclaredRunway(this, angleFromNorth+180, lTORA, lStopway, lClearway, lDisThresh);
	}

	/**
	 * We redeclare when there is an object added or removed
	 * 
	 * @throws VariableDeclarationException
	 * @throws UnusableRunwayException 
	 */
	private void redeclareRunways() throws VariableDeclarationException, UnusableRunwayException{
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
	public double getRunwayWidth() {
		return runWidth;
	}

	@Override
	public double getRunwayLength() {
		return runLen;
	}

	@Override
	public double getInitialStopway() {
		return initStop;
	}

	@Override
	public double getStripEndSideLength() {
		return stripEndL;
	}

	@Override
	public double distanceToLongSpacer() {
		return dToLongSpace;
	}

	@Override
	public double getShortClearedWidthSpacer() {
		return shortClearWSpace;
	}

	@Override
	public double getLongClearedWidthSpacer() {
		return longClearWSpace;
	}

	@Override
	public double getFullWidthSpacer() {
		return fullWSpace;
	}

	@Override
	public double getBlastAllowanceDistance() {
		return Airfield.BLAST_PROT;
	}

	@Override
	public PositionedObstacleInterface getPositionedObstacle() {
		return this.obstacle;
	}

	/*@Override
	public void addObstacle(ObstacleInterface obj,
			double distanceFromSmall, double distanceFromLarge) throws InvalidIdentifierException {*/

	@Override
	public void addObstacle(ObstacleInterface obj, String indentifier, double howFarIn) throws InvalidIdentifierException, UnusableRunwayException {

		/* 
		 ************************************************/
		//Figuring where dat ting is near bruv... 
		if(indentifier.equals(this.getSmallAngledRunway().getIdentifier())){
			double otherhowFarIn = this.getSmallAngledRunway().getTORA()-this.getSmallAngledRunway().getDisplacedThreshold()-this.getLargeAngledRunway().getDisplacedThreshold()-howFarIn;
			this.obstacle = new PositionedObstacle(obj,howFarIn, otherhowFarIn);

		}else if(indentifier.equals(this.getLargeAngledRunway().getIdentifier())){
			double otherhowFarIn = this.getSmallAngledRunway().getTORA()-this.getLargeAngledRunway().getDisplacedThreshold()-this.getSmallAngledRunway().getDisplacedThreshold()-howFarIn;
			this.obstacle = new PositionedObstacle(obj,otherhowFarIn, howFarIn);
			
			System.out.println("~~~~~~~~~~~");
			System.out.println("TORA: "+this.getLargeAngledRunway().getTORA());
			System.out.println("DIS THRE: "+this.getLargeAngledRunway().getDisplacedThreshold());
			System.out.println("Dist from SMall: "+otherhowFarIn);
			System.out.println("~~~~~~~~~~~");

		}else{
			//Excuse me! I don't own one of those, how dare you suggest such a thing!
			throw new InvalidIdentifierException(indentifier, this);
		}
		/* *************************************************
		 *
		this.obstacle = new PositionedObstacle(obj, distanceFromSmall, distanceFromLarge);
		 */		
		try {
			this.redeclareRunways();


		} catch (VariableDeclarationException | UnusableRunwayException e) {
			System.err.println("Stefan Here: This really should not happen! ... me thinks");
			e.printStackTrace();
		}

	}

	@Override
	public void removeObstacle() {
		this.obstacle = null;
		try {
			this.redeclareRunways();

		} catch (VariableDeclarationException | UnusableRunwayException e) {
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
}
