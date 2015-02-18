package Core;

import CoreInterfaces.DeclaredRunwayInterface;

/**
 * This so far is a stub for editing
 * 
 * @author Shakib
 * @Editor Stefan
 * @Tester 
 *
 */
public class DeclaredRunway implements DeclaredRunwayInterface{

	private double decTora;
	
	private double disThreshold, stopway, clearway;
	
	private int angle, ascentAngle, descentAngle;
	
	private char direction;
	
	public DeclaredRunway(double tora, double disThr, double stop, double clear, int ang, char dirc){
		decTora = tora;
		disThreshold = disThr;
		stopway = stop;
		clearway = clear;
		
		ascentAngle = DeclaredRunwayInterface.DEFAULT_ANGLE_OF_ASCENT;
		descentAngle = DeclaredRunwayInterface.DEFAULT_ANGLE_OF_DESCENT;
		
		angle = ang;
		direction = dirc;
	}
	
	//====[ Direction Methods  ]=====================================
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
		String out = "";
		if(angle<10){
			out += "0";
		}
		out += String.valueOf(angle)+direction;
		return out;
	}
	
//====[ Direction Methods  ]=====================================
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
		return getTORA()+getStopway();
	}

	@Override
	public double getTODA() {
		return getTORA()+getClearway();
	}

	@Override
	public double getLDA() {
		return getTORA()-getDisplacedThreshold();
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
