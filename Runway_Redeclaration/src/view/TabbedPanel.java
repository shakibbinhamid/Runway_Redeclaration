package view;

import java.util.Collection;
import java.util.HashMap;

import javax.swing.JTabbedPane;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.PositionedObstacleInterface;
/**
 * This is just a holder class for all the tabs.
 * 
 * primary method is updateTabs
 * @author shakib-binhamid
 *
 */
public class TabbedPanel extends JTabbedPane{

	private AirportInterface airport;
	private HashMap<String, Tab> tabs;
	
	public TabbedPanel(){
		tabs = new HashMap<>();
	}
	
	/**
	 * Removes all the current tabs and airfields.
	 * Then readds all the tabs- one for each airfield.
	 * @param airport
	 */
	public void updateTabs(AirportInterface airport){
		tabs.clear();
		this.removeAll();
		
		this.airport = airport;
		
		Collection<AirfieldInterface> airfields = airport.getAirfields();
		
		for(AirfieldInterface airfield: airfields){
			tabs.put(airfield.getName(), new Tab(airfield));
			this.addTab(airfield.getName(), new Tab(airfield));
		}
		
	}
	
	public void updateTab(AirfieldInterface field){
		Tab whichTab = getTab(field.getName());
		Tab newTab = new Tab(field);
		int location = this.indexOfComponent(whichTab);
		this.remove(whichTab);
		this.add(newTab, location);
	}

	public AirportInterface getAirport() {
		this.getSelectedComponent();
		return airport;
	}

	public void setAirport(AirportInterface airport) {
		this.airport = airport;
	}
	
	public Tab getTab(String airfieldId){
		return tabs.get(airfieldId);
	}
}