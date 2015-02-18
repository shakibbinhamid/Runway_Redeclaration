package CoreInterfaces;

import java.util.Collection;
import java.util.Set;

import Core.Airfield;
import Exceptions.UnrecognisedAirfieldIntifierException;

/**
 * 
 * @author Stefan
 * @Editor Stefan
 *
 */
public interface AirportInterface {
	
	Collection<Airfield> getAirfields();
	
	AirfieldInterface getAirfield(String identifier) throws UnrecognisedAirfieldIntifierException;
	
	void addNewAirfield(AirfieldInterface newAirfield);
	
	Set<String> getIdentifiers();
	
	/**
	 * The name of the airport
	 * e.g. "Heathrowe Airport"
	 */
	String getName();
	
	
}
