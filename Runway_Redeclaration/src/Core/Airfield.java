package Core;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;
import CoreInterfaces.Savable;

public class Airfield implements AirfieldInterface, Savable {
	private DeclaredRunwayInterface[] runways;
	private PositionedObstacle obstacle;
	
	public Airfield(int angleFromNorth, char sideLetter){
		//TODO add stuff - dunno what though
		
		//TODO Add all distances to the the fields
		this.obstacle = null;
		
		
		this.runways = new DeclaredRunway[2];
		//TODO generate a runway for each 
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
			String indentifier, double howFarIn) {
		
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
	public DeclaredRunwayInterface getSmallAngledRunway()  {
		return this.runways[0];
	}

	@Override
	public DeclaredRunwayInterface getLargeAngledRunway() {
		return this.runways[1];
	}


	@Override
	public String getSmallIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getLargeIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
