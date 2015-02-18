package CoreInterfaces;

import java.util.List;

/**
 * 
 * @author Stefan
 * @Editor Stefan
 *
 */
public interface AirportInterface {
	
	List<AirfieldInterface> getAirfields();
	
	AirfieldInterface getAirfield(String identifier);
	
	List<String> getIdentifiers();
	
	/**
	 * The name of the airport
	 * e.g. "Heathrowe Airport"
	 */
	String getName();
	
	
}
