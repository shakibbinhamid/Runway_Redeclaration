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
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.ObstacleInterface;
import Exceptions.CannotMakeRunwayException;
import Exceptions.ParrallelRunwayException;
import Exceptions.UnrecognisedAirfieldIntifierException;
import Exceptions.VariableDeclarationException;

public class TopFrame extends JFrame{
	
	private JPanel topPanel;
		private LogPanel logPanel;
		private TabbedPanel tabbedPanel;
	
	private AirportInterface airport;

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
	
	public void init(){
		
		/**
		 * DON'T USE THIS
		 */
		// TODO 
		airport = new Airport("     Heathrodnf kol     ");
		try {
			airport.addNewAirfield(50, new double[]{50,500,50,100,100,200,300,400});
			//runway = airport.getAirfield("05 /23 ").getSmallAngledRunway();
		} catch (ParrallelRunwayException | CannotMakeRunwayException | VariableDeclarationException e) {
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
		tabbedPanel = new TabbedPanel();
		topPanel.add(tabbedPanel, BorderLayout.CENTER);
		
		this.loadOrCreateAirport(airport);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1200,800));
		this.pack();
		setVisible(true);
	}
	
	public void loadOrCreateAirport(AirportInterface airport){
		setAirport(airport);
		tabbedPanel.updateTabs(airport);
	}

	public AirportInterface getAirport() {
		return airport;
	}

	public void setAirport(AirportInterface airport) {
		this.airport = airport;
	}
	
	public TabbedPanel getTabbePanel(){
		return this.tabbedPanel;
	}
}
