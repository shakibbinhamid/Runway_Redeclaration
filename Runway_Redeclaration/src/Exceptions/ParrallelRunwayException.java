package Exceptions;

import java.util.ArrayList;
import java.util.List;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;

public class ParrallelRunwayException extends Exception {
	private static final long serialVersionUID = 1L;
	private List<AirfieldInterface> parrallels;
	private AirfieldInterface newAirfield;
	private AirportInterface airport;


	public ParrallelRunwayException(AirportInterface airportWithIssue, List<AirfieldInterface> parrallels, AirfieldInterface newAirfield){
		super("The new runway is parrallel to "+interpretStringSet(parrallels));
		this.parrallels = parrallels;
		this.newAirfield = newAirfield;
		this.airport = airportWithIssue;
	}


	private static String interpretStringSet(List<AirfieldInterface> runways){
		String out = "";
		out += runways.get(0).getSmallIdentifier();
		if(runways.size() >1){
			for(int index = 1; index < runways.size(); index++){
				out += " & "+runways.get(index).getSmallIdentifier();
			}
		}
		return out;
	}

	public List<Character> getAvailableOptions(){
		List<Character> options = new ArrayList<Character>();
		if(this.parrallels.size() >= 1){
			options.add('L');
			options.add('R');
		}
		if(this.parrallels.size() >= 2){
			options.add('C');
		}
		return options;
	}

	public void chooseOption(char sideOfNewRunway){
		newAirfield.setSideLetter(sideOfNewRunway);
		if(sideOfNewRunway=='C'){ 
			//Don't worry it's all worked out easy 
		}else{
			for(AirfieldInterface runway : this.parrallels){
				if(runway.getSideLetter()==sideOfNewRunway){
					runway.setSideLetter('C');
				}
			}
		}
		this.airport.updateIdentifierList();
	}
}