package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

	public LogPanel(){
		this.devLog = this.makeFile();
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
		File f = new File("./dat/devLog"+i);
		while(f.exists() && !f.isDirectory()){
			f = new File("./dat/devLog"+i);
			i++;
		}
		 try {
			this.devWriter = new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}
	
	BufferedWriter devWriter; 
	private void saveLogToDeveloper(String info){
		try {
		    devWriter.write(info);
		    devWriter.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void finalize(){
		try {
			devWriter.flush();
			devWriter.close();
		} catch (IOException e) {
		}
	}
}


