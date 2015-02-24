package Exceptions;

import java.util.Arrays;

public class VariableDeclarationException extends Exception{
	/** @Version 18/02/2015 */
	private static final long serialVersionUID = 179922121914261148L;

	private String distanceName;
	private double incorrectValue;
	private double[] arr;
	
	public VariableDeclarationException(String distanceName, double value, String constraintInequality){
		super("DistanceDeclarationException:\n"
				+"The variable "+distanceName+" has the value: "+value
				+"Which does not follow: "+constraintInequality);
		this.distanceName = distanceName;
		this.incorrectValue = value;
	}
	
	public VariableDeclarationException(String distanceName, char value, String constraintInequality){
		super("DistanceDeclarationException:\n"
				+"The variable "+distanceName+" has the value: "+value
				+"Which does not follow: "+constraintInequality);
		this.distanceName = distanceName;
		this.incorrectValue = value;
	}
	
	public VariableDeclarationException(String distanceName, double[] values, String constraintInequality) {
		super("DistanceDeclarationException:\n"
				+"The variable "+distanceName+" has the value: "+Arrays.toString(values)
				+"Which does not follow: "+constraintInequality);
		this.distanceName = distanceName;
		this.arr = values;
	}

	public double[] getInvalidArray(){
		return this.arr;
	}
	
	public double getIncorrectvalue(){
		return this.incorrectValue;
	}
	
	public String getDistanceName(){
		return this.distanceName;
	}
	
}
