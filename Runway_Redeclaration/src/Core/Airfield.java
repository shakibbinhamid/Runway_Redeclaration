package Core;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;
import CoreInterfaces.Savable;
import Exceptions.VariableDeclarationException;

@Root
class Airfield implements AirfieldInterface, Savable {
	@ElementArray
	private DeclaredRunwayInterface[] runways,defaultRunways;
	@Element
	private PositionedObstacle obstacle;
	
	private double runWidth,runLen,initStop,clearLen,dToLongSpace,shortClearWSpace,longClearWSpace,fullWSpace;
	
	/**
	 * 
	 * @param angleFromNorth
	 * @param sideLetter
	 * @param lengths
	 * @throws VariableDeclarationException - There are invalid variable declarations
	 */
	protected Airfield(int angleFromNorth, double[] lengths) throws VariableDeclarationException{
		if(lengths.length != 8) throw new VariableDeclarationException("lengths", lengths, "Needs to be 8 cells");
		
		this.runWidth = lengths[0];
		this.runLen = lengths[1];
		this.initStop = lengths[2];
		this.clearLen = lengths[3];
		this.dToLongSpace = lengths[4];
		this.shortClearWSpace = lengths[5];
		this.longClearWSpace = lengths[6];
		this.fullWSpace = lengths[7];
		//TODO Check these values against CAA stuff in INCREMENT 2
		
		
		this.obstacle = null;
		
		this.runways = new DeclaredRunway[2];
		this.defaultRunways= new DeclaredRunway[2];
		this.redeclareRunways(angleFromNorth);
		
		this.defaultRunways[0] = new DeclaredRunway(this, this.getSmallAngledRunway().getAngle());
		this.defaultRunways[1] = new DeclaredRunway(this, this.getLargeAngledRunway().getAngle());;		
	}
	
	private void redeclareRunways(int angleFromNorth) throws VariableDeclarationException{
		//Ensure that it is above 0
		while(angleFromNorth < 0){
			angleFromNorth+= 360;
		}
		
		//Modding it by 180 finds the smallest angle ;)
		angleFromNorth %= 180;
		
		this.runways[0] = new DeclaredRunway(this, angleFromNorth);
		this.runways[1] = new DeclaredRunway(this, angleFromNorth+180);
	}
	
	
	@Override
	public String getName(){
		return this.getSmallAngledRunway().getIdentifier()+"/"+this.getLargeAngledRunway().getIdentifier();
	}
	
	@Override
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
	public double getClearedLength() {
		return clearLen;
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
	public PositionedObstacleInterface getObstacle() {
		return this.obstacle;
	}

	@Override
	public void addObstacle(ObstacleInterface obj,
			String indentifier, double howFarIn) {
		
		if(indentifier.equals(this.getSmallAngledRunway().getIdentifier())){
			this.obstacle = new PositionedObstacle(obj,howFarIn);
			
		}else{
			howFarIn = this.getRunwayLength()-howFarIn;
			this.obstacle = new PositionedObstacle(obj,howFarIn);
		}
		
		try {
			this.redeclareRunways(getSmallAngledRunway().getAngle());
		} catch (VariableDeclarationException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void removeObstacle() {
		this.obstacle = null;
	}

	@Override
	public boolean hasObstacle() {
		return !(getObstacle()==null);
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
