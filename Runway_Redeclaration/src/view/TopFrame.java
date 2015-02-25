package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Core.Obstacle;
import Core.ParrallelRunwayException;
import Core.PositionedObstacle;
import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;
import Exceptions.CannotMakeRunwayException;
import Exceptions.InvalidIdentifierException;
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
		
		this.setJMenuBar(new TopMenu(this));
		
		topPanel = new JPanel();
		this.setContentPane(topPanel);
		topPanel.setLayout(new BorderLayout());
		
		logPanel = new LogPanel(null);
		topPanel.add(logPanel, BorderLayout.WEST);
		
		tabbedPanel = new TabbedPanel();
		topPanel.add(tabbedPanel, BorderLayout.CENTER);
		
		Notification.setFrame(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1200,800));
		this.pack();
		setVisible(true);
	}
	
	public void loadOrCreateAirport(AirportInterface airport){
		setAirport(airport);
		logPanel.updateLabelText(airport.getName());
		tabbedPanel.updateTabs(airport);
	}
	
	public void loadOrCreateField(int parseInt, double[] physicalInputs,
			double[] smallInputs, double[] bigInputs) {
		try {
			airport.addNewAirfield(parseInt, physicalInputs, smallInputs, bigInputs);
		} catch (VariableDeclarationException | ParrallelRunwayException | CannotMakeRunwayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<AirfieldInterface> fields = airport.getAirfields();
		tabbedPanel.addTab(fields.get(fields.size() - 1));
	}
	
	public void loadOrCreateObstacle(ObstacleInterface obs, AirfieldInterface field, double distanceFromLeft){
		try {
			field.addObstacle(obs, field.getSmallAngledRunway().getIdentifier(), distanceFromLeft);	
		} catch (InvalidIdentifierException e) {
			e.printStackTrace();
		}
		tabbedPanel.updateTab(field);
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

	public LogPanel getLogPanel() {
		return logPanel;
		
	}
}
