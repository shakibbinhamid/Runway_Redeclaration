package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import view.customComponents.Notification;
import view.customComponents.NotificationPanel;
import view.panels.LogPanel;
import view.panels.TabbedPanel;
import view.panels.WelcomePanel;
import core.interfaces.AirfieldInterface;
import core.interfaces.AirportInterface;
import core.interfaces.ObstacleInterface;
import exceptions.CannotMakeRunwayException;
import exceptions.UnrecognisedAirfieldIntifierException;
import exceptions.VariableDeclarationException;

/**
 * This is the entry point of our program
 * @author Shakib-Bin Hamid
 * @see {@link JSplitPane}
 * @see {@link LogPanel}
 * @see {@link TabbedPanel}
 * @see {@link WelcomePanel}
 */
public class TopFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSplitPane topPanel;
		private LogPanel logPanel;
		private TabbedPanel tabbedPanel;
		private WelcomePanel welcomePanel;
	
	private AirportInterface airport;
	
	private static String EMAIL = null;
	private static boolean EMAIL_ENABLED = false;

	public TopFrame(){
		init();
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
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
		
		topPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		topPanel.setOneTouchExpandable(true);
		this.setContentPane(topPanel);
		
		logPanel = new LogPanel();
		topPanel.setLeftComponent(logPanel);
		
		tabbedPanel = new TabbedPanel();
		
		welcomePanel = new WelcomePanel(this);
		topPanel.setRightComponent(welcomePanel);
		
		topPanel.setDividerLocation(300);
		
		this.setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we){ 
				String ObjButtons[] = {"Yes","No"};
				int promptResult = JOptionPane.showOptionDialog(null, 
						"Are you sure you want to exit?", "Confirmation for Exit", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
						ObjButtons,ObjButtons[1]);
				if(promptResult==JOptionPane.YES_OPTION){
					if(WelcomePanel.isHelpingDeveloper())
						logPanel.saveLogToDeveloper();
					System.exit(0);          
				}
			}
		});
		
		this.setTitle("Runway Redeclaration Tool v.0.91");
		this.setMinimumSize(new Dimension(1200,630));
		tabbedPanel.setMinimumSize(new Dimension(this.getMinimumSize().width - 300, 200));
		this.pack();
		this.setLocationRelativeTo(null);

		setVisible(true);

	}
	
	public static void setEmail(String email){
		EMAIL = email;
	}
	
	public static String getEmail(){
		return EMAIL;
	}
	
	public static boolean hasEmail(){
		return EMAIL != null;
	}
	
	public static void setEnableEmail(boolean enable){
		EMAIL_ENABLED = enable;
	}
	
	public static boolean isEmailEnabled(){
		return EMAIL_ENABLED;
	}
	
	/**
	 * To be used by the airport loader or creator
	 * Causes a Notification on the Notification Panel
	 * @param airport the airport to be loaded or created
	 */
	public void loadOrCreateAirport(AirportInterface airport){
		setAirport(airport);
		logPanel.updateLabelText(airport.getName());
		tabbedPanel.setAirport(airport);
		NotificationPanel.notifyIt(airport.getName()+" Added", airport.getName() +" loaded.", Notification.FILE);
		switchToTabbedPanel();
	}
	
	/**
	 * To be used by the airfield loader or creator
	 * Causes a Notification on Notification Panel
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
	 * Removes the given airfield from the view and backbone
	 * @param field the field to remove
	 */
	public void removeField(AirfieldInterface field){
		try {
			airport.removeAirfield(field.getName());
			getTabbePanel().removeTab(field);
		} catch (UnrecognisedAirfieldIntifierException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To be used by the airfield loader or creator
	 * Causes a Notification on Notification Panel
	 * @param obs the obstacle to be loaded
	 * @param field the airfield to be added to
	 * @param distanceFromLeft distance from the left hand side
	 */
	public void loadOrCreateObstacle(ObstacleInterface obs, AirfieldInterface field, double distanceFromLeft, double distanceFromRight){
		try {
			field.addObstacle(obs, distanceFromLeft, distanceFromRight);
			if(field.hasObstacle()){
				tabbedPanel.updateTab(field);
				String title = obs.getName() + " Loaded";
				String details = obs.getName() + " placed on " + 
						field.getName() + "\n"+
						"Distance from "+field.getSmallAngledRunway().getIdentifier() +": "+ field.getPositionedObstacle().distanceFromSmallEnd() + " m"+"\n"+
						"Distance from "+field.getLargeAngledRunway().getIdentifier() +": "+ field.getPositionedObstacle().distanceFromLargeEnd() + " m";
				NotificationPanel.notifyIt(title, details, Notification.FILE);
			}
		} catch (VariableDeclarationException e) {
			JOptionPane.showMessageDialog(this, "Your Obstacle has made the runway unusable --- " + e.getMessage(), "ERROR: Unusable Runway", JOptionPane.ERROR_MESSAGE);
			NotificationPanel.notifyIt(field.getName() + " Unusable",
					obs.getName() + " placed on "+
					field.getName() + "\n"+
					"Distance from "+field.getSmallAngledRunway().getIdentifier() +": "+ field.getPositionedObstacle().distanceFromSmallEnd() + " m"+"\n"+
					"Distance from "+field.getLargeAngledRunway().getIdentifier() +": "+ field.getPositionedObstacle().distanceFromLargeEnd() + " m" +"\n"+
					"\n" + "It has made this runway unusable in the following way: "+e.getMessage(),
					Notification.ERROR);
			field.removeObstacle();
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					revalidate();
				}
			});
		}
	}

	/**
	 * Return the airport
	 * @return the current active airport
	 */
	public AirportInterface getAirport() {
		return airport;
	}
	
	/**
	 * Returns true if there is an active airport, false otherwise
	 * @return true if there is an airport, false otherwise
	 */
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
	
	public void switchToTabbedPanel(){
		if(hasAirport()){
			this.remove(welcomePanel);
			topPanel.setRightComponent(this.tabbedPanel);
		}
		repaint();
	}
	
	protected boolean isUserHelpingDeveloper(){
		return WelcomePanel.isHelpingDeveloper();
	}
}
