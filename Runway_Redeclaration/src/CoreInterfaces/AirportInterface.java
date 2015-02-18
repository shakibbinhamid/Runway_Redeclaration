package CoreInterfaces;

import java.util.Collection;
import java.util.Set;

import Core.Airfield;
import Exceptions.CannotMakeRunwayException;
import Exceptions.ParrallelRunwayException;
import Exceptions.UnrecognisedAirfieldIntifierException;

/**
 * 
 * @author Stefan
 * @Editor Stefan
 *
 */
public interface AirportInterface {
	
	Collection<AirfieldInterface> getAirfields();
	
	AirfieldInterface getAirfield(String identifier) throws UnrecognisedAirfieldIntifierException;
	
	void addNewAirfield(AirfieldInterface newAirfield) throws ParrallelRunwayException, CannotMakeRunwayException;
	
	Set<String> getIdentifiers();
	
	void updateIdentifierList();
	
	/**
	 * The name of the airport
	 * e.g. "Heathrowe Airport"
	 */
	String getName();
	
	
}
