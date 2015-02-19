package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;

public class Tab extends JPanel{
	
	private AirfieldInterface field;
	private DeclaredRunwayInterface runway;
	
	private JPanel dataPanel;
		private ChooseRunwayPanel selectDirectionPanel;
		private InfoPanel info;
	private ViewPanel views;
	
	public Tab(AirfieldInterface field){
		this.field = field;
		this.runway = field.getLargeAngledRunway();
		init();
	}
	
	private void init(){
		this.setLayout(new BorderLayout());
		
		dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		this.add(dataPanel, BorderLayout.WEST);
		
		selectDirectionPanel = new ChooseRunwayPanel(this);
		dataPanel.add(selectDirectionPanel, BorderLayout.NORTH);
		
		info = new InfoPanel(runway);
		dataPanel.add(info, BorderLayout.CENTER);
		
		views = new ViewPanel(runway);
		this.add(views, BorderLayout.CENTER);
		
	}
	
	protected InfoPanel getInfoPanel(){
		return info;
	}

	protected ViewPanel getViewPanel(){
		return views;
	}
	
	protected DeclaredRunwayInterface getRunway(){
		return runway;
	}
}
