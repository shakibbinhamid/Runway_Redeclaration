package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import CoreInterfaces.AirfieldInterface;

public class Tab extends JPanel{
	
	private AirfieldInterface field;
	
	private InfoPanel info;
	private ViewPanel views;
	private JPanel selectDirectionPanel;
	
	public Tab(AirfieldInterface field){
		this.field = field;
		init();
	}
	
	private void init(){
		this.setLayout(new BorderLayout());
		
		info = new InfoPanel(field);
		this.add(info, BorderLayout.WEST);
		
		views = new ViewPanel(field);
		this.add(views, BorderLayout.CENTER);
		
	}
}
