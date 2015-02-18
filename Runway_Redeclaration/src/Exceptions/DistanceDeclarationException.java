package Exceptions;

public class DistanceDeclarationException extends Exception{
	/** @Version 18/02/2015 */
	private static final long serialVersionUID = 179922121914261148L;

	private String distanceName;
	private double incorrectValue;
	
	public DistanceDeclarationException(String distanceName, double value, String constraintInequality){
		super("DistanceDeclarationException:\n"
				+"The variable "+distanceName+" has the value: "+value
				+"Which does not follow: "+constraintInequality);
		this.distanceName = distanceName;
		this.incorrectValue = value;
	}
	
	public double getIncorrectvalue(){
		return this.incorrectValue;
	}
	
	public String getDistanceName(){
		return this.distanceName;
	}
}
