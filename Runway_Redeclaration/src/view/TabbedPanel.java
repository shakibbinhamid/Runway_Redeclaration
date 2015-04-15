package view;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;
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
	 * The airport this tabbedpanel is serving
	 * @return the active airport
	 */
	public AirportInterface getAirport() {
		//this.getSelectedComponent();
		return airport;
	}

	/**
	 * The airport this tabbedpanel is serving is set to the new one
	 * @param airport a new airport
	 */
	public void setAirport(AirportInterface airport) {
		updateTabs(airport.getAirfields());
		this.airport = airport;
	}
	
	/**
	 * Sets the current Airport to this Airport.
	 * Removes all the current tabs and airfields.
	 * Then re-adds all the tabs- one for each airfield.
	 * @param airport
	 */
	public void updateTabs(Collection<AirfieldInterface> airfields){
		tabs.clear();
		this.removeAll();
		
		for(AirfieldInterface airfield: airfields){
			tabs.add(new Tab(airfield));
			this.addTab(airfield.getName(), new Tab(airfield));
		}
	}
	
	public void removeTab(AirfieldInterface field){
		
		int index = this.indexOfTab(field.getName());
		
		tabs.removeIf( x -> x.getField().getName().equals(field.getName()));
		this.removeAll();
		for(Tab tab: tabs){
			this.addTab(tab.getField().getName(), tab);
		}
		
		if(index == 0)
			this.setSelectedIndex(0);
		else
			this.setSelectedIndex(index - 1);
	}
	
	/**
	 * Given an existing airfield, it will update that field (mutated).
	 * Used to update an redeclare airfield runways.
	 * @param field the 'existing' airfield to update
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
	 * Return the current active airfield
	 * @return the active airfield
	 */
	public AirfieldInterface getActiveField(){
		if (this.getSelectedComponent() != null)
			return getActiveTab().getField();
		return null;
	}
	
	/**
	 * Return the current active Tab
	 * @return the selected Tab
	 */
	public Tab getActiveTab(){
		return (Tab) this.getSelectedComponent();
	}
	
	/**
	 * Add a new tab for a field.
	 * Used to create airfields.
	 * @param field the 'new' airfield to add
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
	 * Returns the Tab that has the given ID
	 * @param airfieldId
	 * @return Tab containing the AirfieldInterface
	 */
	public Tab getTab(String airfieldId){
		for(Tab tab: tabs){
			if (tab.getField().getName().equals(airfieldId))
				return tab;
		}
		return null;
	}
	
	/**
	 * Returns the Tab that has the given AirfieldInterface
	 * @param field
	 * @return Tab containing the AirfieldInterface
	 */
	public Tab getTab(AirfieldInterface field){
		return getTab(field.getName());
	}
	
	/**
	 * Checks whether there is a Tab that has the given AirfieldInterface
	 * @param field
	 * @return true if such a tab exists, false otherwise
	 */
	public boolean hasTab(AirfieldInterface field){
		return getTab(field.getName()) != null;
	}
	
	/**
	 * Checks whether there is a Tab that has an AirfieldInterface with the given Id
	 * @param airfieldId
	 * @return true if such a tab exists, false otherwise
	 */
	public boolean hasTab(String airfiledId){
		return getTab(airfiledId) != null;
	}
}