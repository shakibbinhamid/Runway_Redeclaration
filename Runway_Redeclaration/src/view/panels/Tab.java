package view.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import core.interfaces.AirfieldInterface;
import core.interfaces.DeclaredRunwayInterface;
import core.interfaces.PositionedObstacleInterface;

/**
 * This is a Tab (extends JPanel). A Tab holds is responsible for viewing all data and views of an airfield.
 * It shows information on the left side, view on the right side.
 * No upperclass should know about the internal structure of this class.
 * 
 * Major method is changeCurrentRunway
 * @author shakib-binhamid
 * @see {@link ChooseRunwayPanel}
 * @see {@link InfoPanel}
 * @see {@link ViewPanel}
 */
public class Tab extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
		
		selectDirectionPanel = new ChooseRunwayPanel();
		dataPanel.add(selectDirectionPanel, BorderLayout.NORTH);
		
		info = new InfoPanel(field);
		dataPanel.add(info, BorderLayout.CENTER);
		
		views = new ViewPanel(field, field.getSmallAngledRunway());
		this.add(views, BorderLayout.CENTER);
		
	}

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
	
	/**
	 * Saves the top view to the specified path as the specified extension
	 * @param fullpath absolute path of file
	 * @param ext extension such as "png" or "jpg" etc
	 * @throws IOException
	 */
	public void saveTopView(String fullpath, String ext) throws IOException{
		this.views.getTopView().save(fullpath, ext);
	}
	
	/**
	 * Saves the side view to the specified path as the specified extension
	 * @param fullpath absolute path of file
	 * @param ext extension such as "png" or "jpg" etc
	 * @throws IOException
	 */
	public void saveSideView(String fullpath, String ext) throws IOException{
		this.views.getSideView().save(fullpath, ext);
	}
	
	/**
	 * This is the method to call on a tab to switch between runways.
	 * Note, obstacle will not be updated and will be removed
	 * @param another {default, current}
	 */
	public void changeCurrentRunway(DeclaredRunwayInterface[] another){
		info.updateRunwayTables(another);
		views.updateView(another[1]);
	}
	
	/**
	 * This panel (extends JPanel) lets you choose between runways
	 * 
	 * @author Shakib-Bin Hamid
	 * @see {@link JRadioButton}
	 */
	private class ChooseRunwayPanel extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private JRadioButton smallAngled;
		private JRadioButton bigAngled;
		
		private ChooseRunwayPanel(){
			init();
		}
		
		private void init(){
			
			String small = getField().getSmallAngledRunway().getIdentifier();
			String big = getField().getLargeAngledRunway().getIdentifier();
			
			smallAngled = new JRadioButton(small);
			bigAngled = new JRadioButton(big);
			
			ButtonGroup runwaySelection = new ButtonGroup();
			runwaySelection.add(smallAngled);
			runwaySelection.add(bigAngled);
			
			smallAngled.setSelected(true);
			
			smallAngled.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					changeCurrentRunway(new DeclaredRunwayInterface[]{ getField().getDefaultSmallAngledRunway()
																		, getField().getSmallAngledRunway()});
				}});
			bigAngled.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					changeCurrentRunway(new DeclaredRunwayInterface[]{ getField().getDefaultLargeAngledRunway()
																		, getField().getLargeAngledRunway()});
				}});
			this.setLayout(new GridLayout(1,0));
			this.add(smallAngled);
			this.add(bigAngled);
		}
	}
}


