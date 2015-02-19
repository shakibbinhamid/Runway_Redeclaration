package Exceptions;

import CoreInterfaces.AirfieldInterface;

public class CannotMakeRunwayException extends Exception {
	private static final long serialVersionUID = -953609051924922253L;
	private AirfieldInterface invalidRunway;

	public CannotMakeRunwayException(AirfieldInterface runway){
		super("The runway "+runway.getName()+" cannot be made as there are already 3 runways at that angle");
		this.invalidRunway = runway;
	}
	
	public AirfieldInterface getInvalidRunway(){
		return this.invalidRunway;
	}

}
