package view;

import java.awt.Component;
import java.util.ArrayList;
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
	private ArrayList<Tab> tabs;
	
	public TabbedPanel(){
		tabs = new ArrayList<>();
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
			tabs.add(new Tab(airfield));
			this.addTab(airfield.getName(), new Tab(airfield));
		}
		
	}
	
	public void updateTab(AirfieldInterface field){
		int index = this.indexOfTab(field.getName());
		
		Tab add = new Tab(field);
		
		this.removeAll();
		tabs.set(index, add);
		
		for(Tab tab: tabs){
			this.addTab(tab.getField().getName(), tab);
		}
		
		this.setSelectedIndex(index);
	}

	public AirportInterface getAirport() {
		this.getSelectedComponent();
		return airport;
	}

	public void setAirport(AirportInterface airport) {
		this.airport = airport;
	}
	
	public Tab getTab(String airfieldId){
		for(Tab tab: tabs){
			if (tab.getField().getName().equals(airfieldId))
				return tab;
		}
		return null;
	}
}