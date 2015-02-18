package Core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;
import Exceptions.UnrecognisedAirfieldIntifierException;

public class Airport implements AirportInterface {
	private Map<String, Airfield> airfields;
	private String name;

	@Override
	public Collection<Airfield> getAirfields() {
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
	public void addNewAirfield(AirfieldInterface newAirfield) {
		List<AirfieldInterface> parrallelRunways = new ArrayList<AirfieldInterface>();
		
		for(AirfieldInterface runway : getAirfields()){
			String id = runway.getSmallIdentifier();
			
			if(newAirfield.getSmallAngledRunway().getIdentifier().equals(id)){
				parrallelRunways.add(runway);
			}
		}
		
		if(!parrallelRunways.isEmpty()){
			//TODO throw
		}
	}

}
