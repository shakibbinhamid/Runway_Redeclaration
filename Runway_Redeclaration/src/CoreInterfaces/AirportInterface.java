package CoreInterfaces;

import java.util.List;

import Exceptions.CannotMakeRunwayException;
import Exceptions.ParrallelRunwayException;
import Exceptions.UnrecognisedAirfieldIntifierException;
import Exceptions.VariableDeclarationException;

/**
 * 
 * @author Stefan
 * @Editor Stefan
 *
 */
public interface AirportInterface {
	
	List<AirfieldInterface> getAirfields();
	
	AirfieldInterface getAirfield(String name) throws UnrecognisedAirfieldIntifierException;
	
	void addNewAirfield(int angleFromNorth, double[] dimensions) throws ParrallelRunwayException, CannotMakeRunwayException, VariableDeclarationException;
	
	List<String> getAirfieldNames();
	
	
	/**
	 * The name of the airport
	 * e.g. "Heathrowe Airport"
	 */
	String getName();
	
	
}
