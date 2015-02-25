package view;

import io.FileSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import CoreInterfaces.AirportInterface;
import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.PositionedObstacleInterface;

public class LoadListener{

	private TopFrame frame;
	private javax.swing.filechooser.FileFilter filter;
	private String typeDir;
	
	public LoadListener(TopFrame frame, javax.swing.filechooser.FileFilter filter, String typeDir){
		this.frame = frame;
		this.filter = filter;
		this.typeDir = typeDir;
		load();
	}

	public void load(){

		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/dat/" + typeDir));

		int result = fc.showOpenDialog(frame);
		File chosen = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			chosen = fc.getSelectedFile();
			FileSystem fs = new FileSystem();
			if(fs.checkObs(chosen)){
				//If true, user is not allowed to load an obstacle
				if(frame.getAirport() == null){
					JOptionPane.showMessageDialog(null, "You cannot load an obstacle before loading an airport.", "Error",JOptionPane.ERROR_MESSAGE);
				}
				else{
					ObstacleInterface obs = fs.loadObs(chosen);
					Notification.notify(obs.getName() + " loaded.", "file");
					frame.loadOrCreateObstacle(obs, ((Tab)frame.getTabbePanel().getSelectedComponent()).getField(), 3);
					
				}
			}
			else{
				if(fs.checkAir(chosen)){
					AirportInterface airport = fs.loadAir(chosen);
					Notification.notify(airport.getName() +" loaded.");
					frame.loadOrCreateAirport(airport);
					
					frame.getLogPanel().repaint();
				}
			}
		}
	}
}
