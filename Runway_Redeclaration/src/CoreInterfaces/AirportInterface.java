package CoreInterfaces;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Stefan
 * @Editor Stefan
 *
 */
public interface AirportInterface {
	
	List<AirfieldInterface> getAirfields();
	
	AirfieldInterface getAirfield(String identifier);
	
	void addNewAirfield(AirfieldInterface newAirfield);
	
	Set<String> getIdentifiers();
	
	/**
	 * The name of the airport
	 * e.g. "Heathrowe Airport"
	 */
	String getName();
	
	
}
