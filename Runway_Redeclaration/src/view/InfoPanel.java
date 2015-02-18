package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import CoreInterfaces.AirfieldInterface;

public class InfoPanel extends JPanel{
	
	JPanel topLabelPanel;
		JLabel label;
	JPanel bottomTablePanel;
		JPanel tablePanel;
			JTable runwayDataTable;
			JTable obstacleDataTable;
			JTable advancedDataTable;
		
	public static void main(String[] args) {
		JFrame test = new JFrame();
		
		InfoPanel ip = new InfoPanel(null);
		ip.init();
	}
	
	public InfoPanel(AirfieldInterface field){
		topLabelPanel = new JPanel();
			label = new JLabel("Label");
		bottomTablePanel = new JPanel();
			tablePanel = new JPanel();
			runwayDataTable = new JTable(5, 3);
			obstacleDataTable = new JTable(3, 2);
			advancedDataTable = new JTable(6, 2);			
	}
	
	public void init(){
		this.setLayout(new BorderLayout());
			this.add(topLabelPanel, BorderLayout.NORTH);
			this.add(bottomTablePanel, BorderLayout.CENTER);
		
		topLabelPanel.setLayout(new BorderLayout());
			topLabelPanel.add(label, BorderLayout.CENTER);
			
		bottomTablePanel.setLayout(new GridLayout(3, 1));
			bottomTablePanel.add(runwayDataTable);
			bottomTablePanel.add(obstacleDataTable);
			bottomTablePanel.add(advancedDataTable);
		
		this.setVisible(true);
	}
		
	

}
