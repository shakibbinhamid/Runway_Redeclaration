package view;

import interfaces.AirfieldInterface;
import interfaces.DeclaredRunwayInterface;
import interfaces.PositionedObstacleInterface;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * This is a Tab. A Tab holds is responsible for viewing all data and views of an airfield.
 * It shows information on the left side, view on the right side.
 * No upperclass should know about the internal structure of this class.
 * 
 * Major method is changeCurrentRunway
 * @author shakib-binhamid
 *
 */
public class Tab extends JPanel{
	
	private AirfieldInterface field;
	private PositionedObstacleInterface obs;
	
	private JPanel dataPanel;
		private ChooseRunwayPanel selectDirectionPanel;
		private InfoPanel info;
	private ViewPanel views;
	
	public Tab(AirfieldInterface field){
		this.field = field;
		this.obs = field.getPositionedObstacle();
		init();
	}
	
	private void init(){
		this.setLayout(new BorderLayout());
		
		dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		this.add(dataPanel, BorderLayout.WEST);
		
		selectDirectionPanel = new ChooseRunwayPanel(this);
		dataPanel.add(selectDirectionPanel, BorderLayout.NORTH);
		
		info = new InfoPanel(new DeclaredRunwayInterface[]{field.getDefaultRunways()[0], field.getRunways()[0]},obs);
		dataPanel.add(info, BorderLayout.CENTER);
		
		views = new ViewPanel(field.getSmallAngledRunway());
		this.add(views, BorderLayout.CENTER);
		
	}
	
	//=============================== GETTERS AND SETTERS =============================//
	public InfoPanel getInfoPanel(){
		return info;
	}

	public ViewPanel getViewPanel(){
		return views;
	}
	
	public AirfieldInterface getField(){
		return field;
	}

	public PositionedObstacleInterface getObs() {
		return obs;
	}

	public void setField(AirfieldInterface field) {
		this.field = field;
	}
	
	public void updateInfo(AirfieldInterface field){
		
	}
	
	/**
	 * This is the method to call on a tab to switch between runways.
	 * Note, obstacle will not be updated and will be removed
	 * @param another {default, current}
	 */
	public void changeCurrentRunway(DeclaredRunwayInterface[] another){
		info.updateRunwayTables(another);
	}
	
	/**
	 * This is the method to call on a tab to switch between obstacles
	 * @param obs
	 */
	public void changeCurrentObstacle(PositionedObstacleInterface obs){
		this.obs = obs;
		info.updateObstacleTable(obs);
	}
}
