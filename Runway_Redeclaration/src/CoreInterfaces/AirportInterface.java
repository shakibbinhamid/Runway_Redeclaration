package CoreInterfaces;
/**
 * 
 * @author Stefan
 * @Editor 
 *
 */
public interface AirportInterface {
	
	AirfieldInterface getAirfields();
	
	/**
	 * The name of the airport
	 * e.g. "Heathrowe Airport"
	 */
	String getName();
	
	
}
