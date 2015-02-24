package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Core.Obstacle;
import Core.PositionedObstacle;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.PositionedObstacleInterface;
import Exceptions.InvalidIdentifierException;
import Exceptions.UnrecognisedAirfieldIntifierException;

public class TopFrame extends JFrame{
	
	private JPanel topPanel;
		private LogPanel logPanel;
		private TabbedPanel tabbedPanel;
	
	private AirportInterface airport;
	
	private PositionedObstacleInterface obs;

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
		//PositionedObstacle o = new PositionedObstacle(new Obstacle("A380", 8, 9), 100, 100);
		//loadOrCreateObstacle(o);
	}
	
	public void loadOrCreateObstacle(PositionedObstacleInterface obs){
		setObstacle(obs);
		Tab current = ((Tab)tabbedPanel.getSelectedComponent());
		try {
			airport.getAirfield(current.getName()).addObstacle(obs, current.getField().getSmallAngledRunway().getIdentifier(), obs.distanceFromSmallEnd());
			//current.getField().addObstacle(obs, current.getField().getSmallAngledRunway().getIdentifier(), obs.distanceFromSmallEnd());
		} catch (InvalidIdentifierException e) {
			e.printStackTrace();
		} catch (UnrecognisedAirfieldIntifierException e) {
			e.printStackTrace();
		}
		tabbedPanel.updateTabs(airport);
	}

	private void setObstacle(PositionedObstacleInterface obs) {
		this.obs = obs;
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
