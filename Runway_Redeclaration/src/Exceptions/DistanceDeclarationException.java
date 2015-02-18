package Exceptions;

public class DistanceDeclarationException extends Exception{
	/** @Version 18/02/2015 */
	private static final long serialVersionUID = 179922121914261148L;

	public DistanceDeclarationException(String distanceName, int value, String constraintInequality){
		super("DistanceDeclarationException:\n"
				+"The variable "+distanceName+" has the value: "+value
				+"Which does not follow: "+constraintInequality);
		
	}
	

}
