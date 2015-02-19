package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Core.Airport;
import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.ObstacleInterface;
import Exceptions.CannotMakeRunwayException;
import Exceptions.ParrallelRunwayException;
import Exceptions.VariableDeclarationException;

public class TopFrame extends JFrame{
	
	private JPanel topPanel;
		private LogPanel logPanel;
		private JTabbedPane tabbedPanel;
	
	private AirportInterface airport;
	private AirfieldInterface airfield;
	private ObstacleInterface obstacle;

	public TopFrame(){
		init();
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopFrame frame = new TopFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void populateTables(){
		
	}
	
	public void init(){
		
		/**
		 * DON'T USE THIS
		 */
		// TODO 
		airport = new Airport("     Heathrodnf kol     ");
		try {
			airport.addNewAirfield(50, new double[]{50,500,50,100,100,200,300,400});
			//airport.getAirfield("50 ").;
			//airport.addNewAirfield(new Airfield(49, 'R'));
		} catch (ParrallelRunwayException | CannotMakeRunwayException | VariableDeclarationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setJMenuBar(new TopMenu(this));
		
		topPanel = new JPanel();
		this.setContentPane(topPanel);
		topPanel.setLayout(new BorderLayout());
		
		//TODO
		logPanel = new LogPanel(airport);
		topPanel.add(logPanel, BorderLayout.WEST);
		
		//TODO
		tabbedPanel = new TabbedPanel(airport);
		topPanel.add(tabbedPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1200,800));
		this.pack();
		//setBounds(100, 100, 539, 350);
		setVisible(true);
	}

	public AirportInterface getAirport() {
		return airport;
	}

	public void setAirport(AirportInterface airport) {
		this.airport = airport;
	}

	public AirfieldInterface getAirfield() {
		return airfield;
	}

	public void setAirfield(AirfieldInterface airfield) {
		this.airfield = airfield;
	}

	public ObstacleInterface getObstacle() {
		return obstacle;
	}

	public void setObstacle(ObstacleInterface obstacle) {
		this.obstacle = obstacle;
	}
	
}
