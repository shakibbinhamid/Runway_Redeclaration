package Exceptions;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import CoreInterfaces.AirfieldInterface;

public class ParrallelRunwayException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ParrallelRunwayException(List<AirfieldInterface> parrallels, AirfieldInterface newAirfield){
		super("The new runway is parrallel to "+interpretStringSet());
	}

	
	private static String interpretStringSet(List<AirfieldInterface> runways){
		
		String out = "";
		for(AirfieldInterface runway : runways){
			
		}
		
		
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
