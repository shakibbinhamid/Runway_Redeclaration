package Core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;

public class Airport implements AirportInterface {
	private Map<String, Airfield> airfields;
	private String name;

	@Override
	public List<AirfieldInterface> getAirfields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AirfieldInterface getAirfield(String identifier) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

}
