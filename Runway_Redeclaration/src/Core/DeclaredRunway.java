package Core;

import CoreInterfaces.DeclaredRunwayInterface;
import Exceptions.DistanceDeclarationException;

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
	
	public DeclaredRunway(double tora, double disThr, double stop, double clear, int ang, char dirc) throws DistanceDeclarationException{
		setTORA(tora);
		setDisplacedThreshold(disThr);
		setStopway(stop);
		setClearway(clear);
		
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
	
//====[ Inert Direction Methods  ]=====================================
//----[ Getters ]------------------------------------------------------
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
//----[ Setters ]---------------------------------------------------------
		private void setTORA(double tora) throws DistanceDeclarationException{
			if(tora <= 0) throw new DistanceDeclarationException("TORA", tora, "TORA > 0");
			
			this.decTora = tora;
		}
		
		private void setDisplacedThreshold(double threshold) throws DistanceDeclarationException{
			if(threshold < 0) throw new DistanceDeclarationException("Displaced Threshold", threshold, "Threshold > 0");
			
			this.disThreshold = threshold;
		}
		
		private void setStopway(double stopway) throws DistanceDeclarationException{
			if( stopway < 0 ) throw new DistanceDeclarationException("Stopway", stopway, "Stopway >= 0");
		}
		
		private void setClearway(double clearway) throws DistanceDeclarationException{
			if( clearway < 0 ) throw new DistanceDeclarationException("Clearway", clearway, "Clearway > 0");
			if( clearway < getStopway() ) throw new DistanceDeclarationException("Clearway", clearway, "Clearway >= Stopway");
			
			this.clearway = clearway;
		}
	
//====[ Calculated Distance Methods  ]=====================================
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

	
//====[ Angle Methods  ]==================================================
	@Override
	public int getAngleOfAscent() {
		return ascentAngle;
	}

	@Override
	public int getAngleOfDescent() {
		return descentAngle;
	}

}
