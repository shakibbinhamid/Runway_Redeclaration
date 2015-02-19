package CoreInterfaces;

import java.util.Collection;
import java.util.Set;

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
	
	Collection<AirfieldInterface> getAirfields();
	
	AirfieldInterface getAirfield(String identifier) throws UnrecognisedAirfieldIntifierException;
	
	void addNewAirfield(int angleFromNorth, double[] dimensions) throws ParrallelRunwayException, CannotMakeRunwayException, VariableDeclarationException;
	
	Set<String> getIdentifiers();
	
	void updateIdentifierList();
	
	/**
	 * The name of the airport
	 * e.g. "Heathrowe Airport"
	 */
	String getName();
	
	
}
