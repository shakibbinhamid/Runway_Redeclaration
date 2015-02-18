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
	public void addNewAirfield(AirfieldInterface newAirfield) throws ParrallelRunwayException, CannotMakeRunwayException {
		List<AirfieldInterface> parrallelRunways = new ArrayList<AirfieldInterface>();
		
		for(AirfieldInterface runway : getAirfields()){
			String id = runway.getSmallIdentifier();
			
			if(newAirfield.getSmallAngledRunway().getIdentifier().equals(id)){
				parrallelRunways.add(runway);
			}
		}
		if(!parrallelRunways.isEmpty()){
			if(parrallelRunways.size()>=3) {
				throw new CannotMakeRunwayException(newAirfield);
			}
			throw new ParrallelRunwayException(this, parrallelRunways, newAirfield);
		}
		
		//Otherwise add the runway with an ? as the identifier
		updateIdentifierList();
	}

	@Override
	public void updateIdentifierList() {
		Collection<AirfieldInterface> airfields = this.getAirfields();
		this.airfields = new HashMap<>();
		for(AirfieldInterface airfield : airfields){
			this.airfields.add()
		}
	}

}
