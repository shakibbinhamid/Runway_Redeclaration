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
		out += runways.get(0).getSmallAngledRunway().getIdentifier();
		if(runways.size() >1){
			for(int index = 1; index < runways.size(); index++){
				out += " & "+runways.get(index).getSmallAngledRunway().getIdentifier();
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

	public void chooseOption(char sideOfNewRunway) throws VariableDeclarationException{
		newAirfield.setSmallestSideLetter(sideOfNewRunway);

		//Case 1: There is one other parrallel so we must give that parrallel the other side for the smallest runway
		//		   That runway does not actually have a side letter
		if(this.parrallels.size()==1){
			if(sideOfNewRunway=='R'){
				this.parrallels.get(0).setSmallestSideLetter('L');
				
			}else{//Must be 'L' then...
				this.parrallels.get(0).setSmallestSideLetter('R');
			}
		
		//Case 2: There are two parrallel runways, each runway has either an 'L' or an 'R'
		//        If our new runway is in the 'C' we don't change anything
		//  	  If our runway is on the 'R' then the one that was on the 'R' is now in the 'C'
		//        Vice versa for 'L'
		}else{
			if(sideOfNewRunway=='C'){ 
				//Don't worry it's all worked out easy man

			}else{
				//Oh no we have to change the sides of the other runways!
				//Let's crack on and handle 'R' or 'L'
				for(AirfieldInterface runway : this.parrallels){
					//If the this particular runway has the side letter that we want, we better change that old runway to 'C'
					if(runway.getSmallAngledRunway().getSideLetter()==sideOfNewRunway){
						//I pity the fool for loosing his side
						runway.setSmallestSideLetter('C');
					}
				}
			}
		}
		this.airport.updateIdentifierList();
	}
}