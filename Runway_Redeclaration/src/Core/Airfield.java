package Core;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;
import CoreInterfaces.Savable;
import Exceptions.NoRunwaysException;

public class Airfield implements AirfieldInterface, Savable {
	private DeclaredRunwayInterface[] runways;
	private PositionedObstacle obstacle;
	
	public Airfield(int angleFromNorth, char leftOrRight){
		//TODO add stuff
		
		//Add all distances to the the fields
		this.obstacle = null;
		
		
		this.runways = new DeclaredRunway[2];
		//generate a runway for each 
	}
	
	
	@Override
	public String getName(){
		try {
			return this.getLeftStartingRunway().getIdentifier();
		} catch (NoRunwaysException e) {
			return "?";
		}
	}
	@Override
	public int getSmallestAngleFromNorth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char getDirection() {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeclaredRunwayInterface[] addObstacle(ObstacleInterface obj,
			char leftOrRight, double howFarIn) {
		if(leftOrRight == 'L'){
			this.obstacle = new PositionedObstacle(obj,howFarIn);
		}else{
			howFarIn = this.getRunwayLength()-howFarIn;
			this.obstacle = new PositionedObstacle(obj,howFarIn);
		}
		
		// TODO Re-process and redeclare all the runways
		
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeclaredRunwayInterface getLeftStartingRunway() throws NoRunwaysException {
		if(this.runways[LEFT_RUNWAY]==null) throw new NoRunwaysException(getName());
		return this.runways[LEFT_RUNWAY];
	}

	@Override
	public DeclaredRunwayInterface getRightStartingRunway() {
		// TODO Auto-generated method stub
		return null;
	}

}
