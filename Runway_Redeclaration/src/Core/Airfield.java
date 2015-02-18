package Core;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;
import CoreInterfaces.Savable;
import Exceptions.NoRunwayException;

public class Airfield implements AirfieldInterface, Savable {
	private DeclaredRunwayInterface[] runways;
	private PositionedObstacle obstacle;
	
	public Airfield(int angleFromNorth, char sideLetter){
		//TODO add stuff - dunno what though
		
		//Add all distances to the the fields
		this.obstacle = null;
		
		
		this.runways = new DeclaredRunway[2];
		//generate a runway for each 
	}
	
	
	@Override
	public String getName(){
		return ""+this.getSmallestAngleFromNorth()+this.getSideLetter();
	}
	
	@Override
	public int getSmallestAngleFromNorth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char getSideLetter() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getRunwayWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRunwayLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getInitialStopway() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getClearedLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double distanceToLongSpacer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShortClearedWidthSpacer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLongClearedWidthSpacer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFullWidthSpacer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PositionedObstacleInterface getObstacle() {
		return this.obstacle;
	}

	@Override
	public void addObstacle(ObstacleInterface obj,
			String indentifier, double howFarIn) throws NoRunwayException {
		
		if(indentifier.contains(""+this.getSmallestAngleFromNorth())){
			this.obstacle = new PositionedObstacle(obj,howFarIn);
			
		}else{
			howFarIn = this.getRunwayLength()-howFarIn;
			this.obstacle = new PositionedObstacle(obj,howFarIn);
		}
		
		// TODO Re-process and redeclare all the runways
		
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
	public DeclaredRunwayInterface getSmallAngledRunway() throws NoRunwayException {
		if(this.runways[0] == null) throw new NoRunwayException(getName()+"Small Angle");
		return this.runways[0];
	}

	@Override
	public DeclaredRunwayInterface getLargeAngledRunway() throws NoRunwayException{
		if(this.runways[1] == null) throw new NoRunwayException(getName()+"Large Angle");
		return this.runways[1];
	}

}
