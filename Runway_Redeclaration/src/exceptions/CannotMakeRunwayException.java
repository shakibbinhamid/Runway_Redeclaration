package exceptions;

import coreInterfaces.AirfieldInterface;

public class CannotMakeRunwayException extends Exception {
	private static final long serialVersionUID = -953609051924922253L;
	private AirfieldInterface invalidRunway;

	public CannotMakeRunwayException(AirfieldInterface runway, String excuse){
		super("The runway "+runway.getName()+" cannot be made as "+excuse);
		this.invalidRunway = runway;
	}
	
	public AirfieldInterface getInvalidRunway(){
		return this.invalidRunway;
	}

}
