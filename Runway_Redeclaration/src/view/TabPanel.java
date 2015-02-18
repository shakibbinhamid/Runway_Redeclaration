package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;

public class TabPanel extends JPanel{
	
	private AirfieldInterface field;
	
	private InfoPanel info;
	private ViewPanel views;
	private JPanel viewOptionPanel;
	
	public TabPanel(AirfieldInterface field){
		this.field = field;
	}
	
	private void init(){
		this.setLayout(new BorderLayout());
		
		info = new InfoPanel(field);
		this.add(info, BorderLayout.WEST);
		
		views = new ViewPanel(field);
		this.add(views, BorderLayout.CENTER);
		
		viewOptionPanel = new JPanel();
	}
}
