package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import listeners.Notification;
import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.ObstacleInterface;
import Exceptions.CannotMakeRunwayException;
import Exceptions.VariableDeclarationException;

/**
 * This is the entry point of our program
 * @author shakib-binhamid
 *
 */
public class TopFrame extends JFrame{
	
	
	private JPanel topPanel;
		private LogPanel logPanel;
		private TabbedPanel tabbedPanel;
		private WelcomePanel welcomePanel;
	
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
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		this.setJMenuBar(new TopMenu(this));
		
		topPanel = new JPanel();
		this.setContentPane(topPanel);
		topPanel.setLayout(new BorderLayout());
		
		logPanel = new LogPanel(null,this);
		topPanel.add(logPanel, BorderLayout.WEST);
		
		tabbedPanel = new TabbedPanel();
		
		welcomePanel = new WelcomePanel(this);
		topPanel.add(tabbedPanel, BorderLayout.CENTER);
		topPanel.add(welcomePanel, BorderLayout.CENTER);
		
		Notification.setFrame(this);
		
		
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Runway Redeclaration Tool v.0.81");
		this.setMinimumSize(new Dimension(1200,600));
		this.pack();
		this.setLocationRelativeTo(null);

		setVisible(true);

	}
	
	/**
	 * To be used by the airport loader or creator
	 * @param airport the airport to be loaded or created
	 */
	public void loadOrCreateAirport(AirportInterface airport){
		setAirport(airport);
		logPanel.updateLabelText(airport.getName());
		tabbedPanel.updateTabs(airport);
		switchToTabbedPanel();
	}
	
	/**
	 * To be used by the airfield loader or creator
	 * @param parseInt the angle from north
	 * @param physicalInputs physical dimensions of the runway
	 * @param smallInputs parameters of small angled runway
	 * @param bigInputs parameters of big angled runway
	 * @throws VariableDeclarationException 
	 */
	public void loadOrCreateField(int parseInt, char side,
			double[] smallInputs, double[] bigInputs) throws VariableDeclarationException {
		try {
			airport.addNewAirfield(parseInt, side, smallInputs, bigInputs);
			List<AirfieldInterface> fields = airport.getAirfields();
			tabbedPanel.addTab(fields.get(fields.size() - 1));
		} catch (CannotMakeRunwayException e) {
			JOptionPane.showMessageDialog(this, "You already have an airfield "+e.getInvalidRunway().getName(), "ERROR: Airfield Create Fail", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * To be used by the airfield loader or creator
	 * @param obs the obstacle to be loaded
	 * @param field the airfield to be added to
	 * @param distanceFromLeft distance from the left hand side
	 */
	public void loadOrCreateObstacle(ObstacleInterface obs, AirfieldInterface field, double distanceFromLeft, double distanceFromRight){
		this.getLogPanel().clearCalcTextPane();
		try {
			field.addObstacle(obs, distanceFromLeft, distanceFromRight);
			tabbedPanel.updateTab(field);
		} catch (VariableDeclarationException e) {
			JOptionPane.showMessageDialog(this, "Your Obstacle has made the runway unusable --- " + e.getMessage(), "ERROR: Unusable Runway", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Return the airport
	 * @return the current active airport
	 */
	public AirportInterface getAirport() {
		return airport;
	}
	
	public boolean hasAirport(){
		return !(getAirport()==null);
	}

	/**
	 * Set the airport
	 * @param airport change the airport
	 */
	public void setAirport(AirportInterface airport) {
		this.airport = airport;
	}
	
	/**
	 * Return the current tabbedpanel to call methods on it
	 * @return the tabbedpanel
	 */
	public TabbedPanel getTabbePanel(){
		return this.tabbedPanel;
	}

	/**
	 * Return the current logpanel to call methods on it
	 * @return the logpanel
	 */
	public LogPanel getLogPanel() {
		return logPanel;
	}
	
	
	protected void switchToTabbedPanel(){
		if(hasAirport()){
			this.remove(this.welcomePanel);;
			this.add(this.tabbedPanel, BorderLayout.CENTER);
		}
		repaint();

	}
	
	protected boolean isUserHelpingDeveloper(){
		return this.welcomePanel.isHelpingDeveloper();
	}
}
