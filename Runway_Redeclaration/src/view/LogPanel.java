package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import notification.NotificationPanel;

/**
 * 
 * This panel shows the central log and the current airport name
 * 
 * @author Shakib-Bin Hamid
 * @editor Jonathon
 * @see {@link Logger}
 * @see {@link NotificationPanel}
 */
public class LogPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel airportLabel;
	private NotificationPanel notPanel;
	
	private File devLog;
	private static String log; 
	private static Logger LOGGER = Logger.getLogger("DevLogs");
	public static final String LOG_LOCATION = "./dat/devLog";
	public static final String LOG_FILE_EXT = ".txt";

	public LogPanel(){
		init();
	}

	private void init(){

		this.setLayout(new BorderLayout());

		devLog = this.makeFile();
		
		airportLabel = new JLabel("Airport");
		airportLabel.setFont(new Font("verdana", Font.ITALIC, 15));
		airportLabel.setHorizontalAlignment(SwingConstants.CENTER);
		airportLabel.setPreferredSize(new Dimension(300,20));
		this.add(airportLabel, BorderLayout.NORTH);
		
		notPanel = new NotificationPanel();
		this.add(notPanel, BorderLayout.CENTER);
		
	}
	
	private File makeFile(){
		int i = 0;
		File f = new File(LOG_LOCATION+i+LOG_FILE_EXT);
		while(f.exists()){
			f = new File(LOG_LOCATION+i+LOG_FILE_EXT);
			i++;
		}
		return f;
	}

	/**
	 * Update the airport label to the specified name
	 * @param airportName the name of an airport
	 */
	public void updateLabelText(String airportName){
		airportLabel.setText(airportName);
	}
	
	/**
	 * Returns the developer's log file
	 * @return developer's log
	 */
	public File getDevFile(){
		return devLog;
	}
	
	/**
	 * Adds the String to dev log. No newline is inserted.
	 * No actual log is written to file until the application closes.
	 * @param info
	 */
	public static void log(String info){
		log += info;
	}
	
	/**
	 * The log file is saved to the default developer log position
	 */
	public void saveLogToDeveloper(){
		try {
			devLog.createNewFile();
			FileHandler fh = new FileHandler(devLog.getAbsolutePath());
			fh.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fh);
			LOGGER.info(log);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


