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
 * @author shakib-binhamid
 * @editor Jonathon
 */
public class LogPanel extends JPanel{

	private JLabel airportLabel;
	private NotificationPanel notPanel;
	
	private File devLog;
	private static String log; 
	private static Logger LOGGER = Logger.getLogger("DevLogs");

	public LogPanel(){
		devLog = this.makeFile();
		init();
	}

	private void init(){

		this.setLayout(new BorderLayout());

		airportLabel = new JLabel("Airport");
		airportLabel.setFont(new Font("verdana", Font.ITALIC, 15));
		airportLabel.setHorizontalAlignment(SwingConstants.CENTER);
		airportLabel.setPreferredSize(new Dimension(300,20));
		this.add(airportLabel, BorderLayout.NORTH);
		
		notPanel = new NotificationPanel();
		this.add(notPanel, BorderLayout.CENTER);
		
	}

	/**
	 * Update the airport label to the specified name
	 * @param airportName the name of an airport
	 */
	public void updateLabelText(String airportName){
		airportLabel.setText(airportName);
	}
	
	private File makeFile(){
		int i = 0;
		File f = new File("./dat/devLog"+i+".txt");
		while(f.exists()){
			f = new File("./dat/devLog"+i+".txt");
			i++;
		}
		return f;
	}
	
	public File getDevFile(){
		return devLog;
	}
	
	public static void log(String info){
		log += info;
	}
	
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


