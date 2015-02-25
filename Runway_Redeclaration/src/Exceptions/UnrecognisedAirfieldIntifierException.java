package Exceptions;

import java.util.Iterator;
import java.util.List;

import CoreInterfaces.AirportInterface;




public class UnrecognisedAirfieldIntifierException extends Exception{
	private static final long serialVersionUID = 5534661978678818822L;

	public UnrecognisedAirfieldIntifierException(AirportInterface airport, String attemptedIdentifier){
		super("The identifier \""+attemptedIdentifier+"\" is not a know runway\n"+
				"known runways: "+ interpretStringSet(airport.getAirfieldNames()));
	}

	private static String interpretStringSet(List<String> ids){
		String out = "";
		if(ids.size()>0){
			Iterator<String> iter = ids.iterator();
			while(iter.hasNext()){
				out += iter.next();
				if(iter.hasNext()){
					out += ",";
				}
			}
		}else{
			out = "None";
		}
		return out;
	}

}
