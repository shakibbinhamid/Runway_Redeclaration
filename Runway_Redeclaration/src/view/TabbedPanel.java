package view;

import java.util.Collection;

import javax.swing.JTabbedPane;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;

public class TabbedPanel extends JTabbedPane{

	private AirportInterface airport;
	
	public TabbedPanel(AirportInterface airport){
		this.airport = airport;
	}
	
	private void init(){
		
		Collection<AirfieldInterface> airfields = airport.getAirfields();
		
		for(AirfieldInterface airfield: airfields){
			this.addTab(airfield.getName(), new Tab(airfield));
		}
	}
}
