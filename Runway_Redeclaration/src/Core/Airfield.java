package Core;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import view.Notification;
import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;
import CoreInterfaces.Savable;
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
	private double runGirth, stripEnd, longSpacer, shortSpacer, mediumSpacer, shortLength, longLength;
	@Element
	private double blastProt;

	//nullary constructor
	protected Airfield() {}

	protected Airfield(int angleFromNorth, double[] dimensions, char side,
			double[] smallAngledDistances, double[] largeAngledDistances) throws VariableDeclarationException{
		//Checks
		if(dimensions.length != 7) throw new VariableDeclarationException("lengths", dimensions, "Needs to be 7 cells");
		if(smallAngledDistances.length != 4)throw new VariableDeclarationException("smallAngledDistances", smallAngledDistances, "Needs to be 4 cells");
		if(largeAngledDistances.length != 4)throw new VariableDeclarationException("largeAngledDistances", largeAngledDistances, "Needs to be 4 cells");

		//Dimensions
		setRunwayGirth  (dimensions[0]);
		setStripEnd     (dimensions[1]);
		setLongSpacer   (dimensions[2]);
		setShortSpacer  (dimensions[3]);
		setMediumSpacer (dimensions[4]);
		setShortLength  (dimensions[5]);
		setLongLength   (dimensions[6]);
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
		this.runways[0] = new DeclaredRunway(this, angleFromNorth, side, lTORA, lASDA, lTODA, lLDA);
		this.defaultRunways[0] = new DeclaredRunway(this, angleFromNorth, side, lTORA, lASDA, lTODA, lLDA);


		//large angled runway stuff
		double rTORA = largeAngledDistances[0];
		double rASDA = largeAngledDistances[1];
		double rTODA = largeAngledDistances[2];
		double rLDA =  largeAngledDistances[3];
		char opSide = DeclaredRunway.oppositeSide(side);

		this.runways[1] = new DeclaredRunway(this, angleFromNorth+180, opSide, rTORA, rASDA, rTODA, rLDA);
		this.defaultRunways[1] = new DeclaredRunway(this, angleFromNorth+180, opSide, rTORA, rASDA, rTODA, rLDA);
	}

	public static final double DEFAULT_GIRTH = 100;
	public static final double DEFAULT_STRIP_END = 60;
	public static final double DEFAULT_LONG_SPACER = 150;
	public static final double DEFAULT_SHORT_SPACER = 75;
	public static final double DEFAULT_MEDIUM_SPACER = 105;
	public static final double DEFAULT_SHORT_LENGTH = 150;
	public static final double DEFAULT_LONG_LENGTH = 300;

	/**
	 * Default value constructor
	 */
	protected Airfield(int angleFromNorth, char side ,double[] smallAngledDistances, double[] largeAngledDistances) throws VariableDeclarationException{
		this(angleFromNorth, new double[] { DEFAULT_GIRTH, DEFAULT_STRIP_END, 
				DEFAULT_LONG_SPACER, 
				DEFAULT_SHORT_SPACER, DEFAULT_MEDIUM_SPACER, 
				DEFAULT_SHORT_LENGTH, DEFAULT_LONG_LENGTH}, 
				side, smallAngledDistances, largeAngledDistances);
	}

	@Override
	public double getRunwayGirth() { return this.runGirth; }
	@Override
	public double getStripEnd() { return this.stripEnd; }
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
	public void setStripEnd(double strip) { this.stripEnd = strip; }
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
		double radiusBuffer = 0;
		if(hasObstacle()){
			double smallDis = getPositionedObstacle().distanceFromSmallEnd();
			double largeDis = getPositionedObstacle().distanceFromLargeEnd();
			if(0 > smallDis){
				radiusBuffer = -smallDis;
			}else if(0 > largeDis){
				radiusBuffer = -largeDis;
			}
		}
		return getStripEnd()+longestTODA()+radiusBuffer+getStripEnd();
	}

	private double longestTODA(){ 
		if (getDefaultSmallAngledRunway().getTODA() > getDefaultLargeAngledRunway().getTODA()){
			return getDefaultSmallAngledRunway().getTODA();
		}else{
			return getDefaultLargeAngledRunway().getTODA();
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
	public void addObstacle(ObstacleInterface obj, double distanceFromSmall, double distanceFromLarge, 
			boolean smallLandTowards,boolean smallTakeOffTowards, 
			boolean largeLandTowards,boolean largeTakeOffTowards) 
					throws VariableDeclarationException {

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

	@Override
	public void addObstacle(ObstacleInterface obj,
			double distanceFromSmall, double distanceFromLarge)  {/**/

		this.obstacle = new PositionedObstacle(obj, distanceFromSmall, distanceFromLarge);
		try {
			this.redeclareRunways();
		} catch (VariableDeclarationException  e) {
			System.err.println("Stefan Here: This really should not happen! ... me thinks");
			e.printStackTrace();
			System.out.println( ((DeclaredRunway)getSmallAngledRunway()).getLog() );
			System.out.println( ((DeclaredRunway)getLargeAngledRunway()).getLog() );

		}
	}

	@Override
	public void removeObstacle() {
		Notification.notify( this.getPositionedObstacle().getName() + " Removed From "+ this.getName(), "file");
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
		return new double[] {runGirth, stripEnd, longSpacer, shortSpacer, mediumSpacer, shortLength, longLength};
	}

}
