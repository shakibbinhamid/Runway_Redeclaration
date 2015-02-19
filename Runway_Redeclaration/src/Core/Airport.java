package Core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.Savable;
import Exceptions.CannotMakeRunwayException;
import Exceptions.ParrallelRunwayException;
import Exceptions.UnrecognisedAirfieldIntifierException;
import Exceptions.VariableDeclarationException;

public class Airport implements AirportInterface, Savable {
	private Map<String, AirfieldInterface> airfields;
	private String name;

	
	public Airport(String name) {
		this.name = name;
		this.airfields = new HashMap<>();
	}
	
	@Override
	public Collection<AirfieldInterface> getAirfields() {
		return this.airfields.values();
	}

	@Override
	public AirfieldInterface getAirfield(String identifier) throws UnrecognisedAirfieldIntifierException {
		AirfieldInterface chosen = this.airfields.get(identifier);
		if(chosen == null) 
			throw new UnrecognisedAirfieldIntifierException(this, identifier);
		return chosen;
	}

	@Override
	public Set<String> getIdentifiers() {
		return this.airfields.keySet();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void addNewAirfield(int angleFromNorth, double[] dimensions) throws ParrallelRunwayException, CannotMakeRunwayException, VariableDeclarationException {
		angleFromNorth %= 180;
		
		AirfieldInterface newAirfield = new Airfield(angleFromNorth, dimensions);
		
		//Identify parallel runways
		List<AirfieldInterface> parrallelRunways = new ArrayList<AirfieldInterface>();
		for(AirfieldInterface runway : getAirfields()){
			int angle = runway.getSmallAngledRunway().getAngle()/10;
			
			//parallel runways would have the same angle part of the identifier
			if(angleFromNorth == angle){
				parrallelRunways.add(runway);
			}
		}
		if(!parrallelRunways.isEmpty()){
			if(parrallelRunways.size()>=3) {
				throw new CannotMakeRunwayException(newAirfield);
			}
			throw new ParrallelRunwayException(this, parrallelRunways, newAirfield);
		}
		
		//We use "?"  for now as the sides will be recalculated afterwards in next line
		this.airfields.put("?", newAirfield);
		updateIdentifierList();
	}

	@Override
	public void updateIdentifierList() {
		Collection<AirfieldInterface> airfields = this.getAirfields();
		this.airfields = new HashMap<>();
		for(AirfieldInterface airfield : airfields){
			this.airfields.put(airfield.getSmallAngledRunway().getIdentifier(),airfield);
			this.airfields.put(airfield.getLargeAngledRunway().getIdentifier(), airfield);
		}
	}

}
