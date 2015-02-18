package Core;

import CoreInterfaces.DeclaredRunwayInterface;

public class DeclaredRunway implements DeclaredRunwayInterface{

	private double decTora, decAsda, decToda, decLda;
	
	private double disThreshold, stopway, clearway;
	
	private int angle, ascentAngle, descentAngle;
	
	private char direction;
	
	public DeclaredRunway(double tora, double disThr, double stop, double clear, int ang, char dirc){
		decTora = tora;
		disThreshold = disThr;
		stopway = stop;
		clearway = clear;
		
		decAsda = tora + stopway;
		decToda = tora + clearway;
		decLda = tora - disThreshold;
		
		ascentAngle = DeclaredRunwayInterface.DEFAULT_ANGLE_OF_ASCENT;
		descentAngle = DeclaredRunwayInterface.DEFAULT_ANGLE_OF_DESCENT;
		
		angle = ang;
		direction = dirc;
	}
	
	//============================GETTERS=====================================//
	@Override
	public char getDirection() {
		return direction;
	}

	@Override
	public int getAngle() {
		return angle;
	}

	@Override
	public String getIdentifier() {
		return String.valueOf(angle)+String.valueOf(direction);
	}

	@Override
	public double getTORA() {
		return decTora;
	}

	@Override
	public double getClearway() {
		return clearway;
	}

	@Override
	public double getStopway() {
		return stopway;
	}

	@Override
	public double getDisplacedThreshold() {
		return disThreshold;
	}

	@Override
	public double getASDA() {
		return decAsda;
	}

	@Override
	public double getTODA() {
		return decToda;
	}

	@Override
	public double getLDA() {
		return decLda;
	}

	@Override
	public int getAngleOfAscent() {
		return ascentAngle;
	}

	@Override
	public int getAngleOfDescent() {
		return descentAngle;
	}

}
