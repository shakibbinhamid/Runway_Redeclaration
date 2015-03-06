package CoreInterfaces;

import java.util.List;

import Exceptions.CannotMakeRunwayException;
import Exceptions.UnrecognisedAirfieldIntifierException;
import Exceptions.VariableDeclarationException;

/**
 * This is what represents an Airport, airports can have multiple airfields.
 * Please note that an airfield cannot handle more than 3 parallel runways.
 * 
 * This interface discourages the use of constructing new AirfieldInterface implementations 
 * and parsing them as arguments to be stored in this AirportInterface implementation. This is 
 * to prevent two or more airports having the same airfield.
 * 
 * @author Stefan
 * @Editor Stefan
 * @Testor Stefan
 *
 */
public interface AirportInterface {
	
	/**
	 * Returns all the airfields ascociated with this airport
	 */
	List<AirfieldInterface> getAirfields();
	
	/**
	 * Returns the airport with the given name.
	 * An airfield name is composed of the smallest angled declared runway's identifier followed by a slash 
	 * then the larger angled runway's identifier.
	 * 
	 * e.g. "09L/27R" or "05 /23 "
	 * 
	 * (Note that if the runways is not parallel it still has a space after the angle)
	 * 
	 * @throws UnrecognisedAirfieldIntifierException - When there is no AirfieldInterface implementation 
	 * 												   associated with the given name.
	 */
	AirfieldInterface getAirfield(String name) throws UnrecognisedAirfieldIntifierException;
	
	/**
	 * The use of parsing in new AirfieldInterface implementations is discouraged. Hence you parse the 
	 * correct arguments for the constructor of an Airfield and it is created locally and stored as 
	 * part of the AirportInterface implementation's attributes.
	 * 
	 * @param angleFromNorth - The anti-clockwise angle from north the runway makes in degrees
	 * @param dimensions - A 8 long double[] of distances in the form: 
	 * 	{Runway Width, Runway Length, Initial Stopway, Clearway Length, Distance to Long Spacer, Short Clear Width Spacer, Long Clear Width Spacer, Full Width Spacer}
	 * 
	 * Please refer to the diagram enclosed with the API for a bettter understanding of what these distances are.
	 *
	 * @throws ParallelRunwayException - There is already a runway with that angle from north
	 * @throws CannotMakeRunwayException - There is a problem making the runway that is not a variable problem
	 * @throws VariableDeclarationException - There is an incorrectly defined variable, check your dimensions array
	 */
	void addNewAirfield(int angleFromNorth, double[] dimensions, char side,
			double[] smallAngledDistances, double[] largeAngledDistances) 
			throws CannotMakeRunwayException, VariableDeclarationException;
	
	/**
	 * Returns a list of all the names of the runways associated with this airport
	 */
	List<String> getAirfieldNames();
	
	/**
	 * The name of the airport
	 * e.g. "Heathrowe Airport"
	 */
	String getName();
	
	
}
