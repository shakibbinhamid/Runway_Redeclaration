package Exceptions;

import CoreInterfaces.AirfieldInterface;

public class InvalidIdentifierException extends Exception {
	private static final long serialVersionUID = -5248193185884463841L;
	private String id;
	private AirfieldInterface airfield;
	
	
	public InvalidIdentifierException(String attemptedIdentifier, AirfieldInterface airfield){
		super("There is no runway with the identifier "+attemptedIdentifier+" in the airfield"+airfield.getName());
		this.id = attemptedIdentifier;
		this.airfield = airfield;
	}
	
	public String getAttempt(){
		return this.id;
	}
	
	public AirfieldInterface getAirfield(){
		return this.airfield;
	}

}
