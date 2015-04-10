package view;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JTabbedPane;

import coreInterfaces.AirfieldInterface;
import coreInterfaces.AirportInterface;
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
	
	/**
	 * Return the current active airfield
	 * @return the active airfield
	 */
	public AirfieldInterface getActiveField(){
		if (this.getSelectedComponent() != null)
			return ((Tab) this.getSelectedComponent()).getField();
		return null;
	}
	
	public Tab getActiveTab(){
		return (Tab) this.getSelectedComponent();
	}
	
	/**
	 * Given an existing airfield, it will update that field (mutated).
	 * Used to update an redeclare airfield runways.
	 * @param field the airfield to update
	 */
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
	
	/**
	 * Add a new tab for a field.
	 * Used to create airfields.
	 * @param field the airfield to add
	 */
	public void addTab(AirfieldInterface field){
		int index = this.getSelectedIndex();
		
		Tab add = new Tab(field);
		
		this.removeAll();
		tabs.add(add);
		
		for(Tab tab: tabs){
			this.addTab(tab.getField().getName(), tab);
		}
		
		this.setSelectedIndex(index);
	}

	/**
	 * The airport this tabbedpanel is serving
	 * @return the active airport
	 */
	public AirportInterface getAirport() {
		this.getSelectedComponent();
		return airport;
	}

	/**
	 * The airport this tabbedpanel is serving is set to the new one
	 * @param airport a new airport
	 */
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