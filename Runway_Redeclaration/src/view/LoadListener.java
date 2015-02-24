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
import CoreInterfaces.PositionedObstacleInterface;

public class LoadListener{

	private TopFrame frame;
	private javax.swing.filechooser.FileFilter filter;
	
	public LoadListener(TopFrame frame, javax.swing.filechooser.FileFilter filter){
		this.frame = frame;
		this.filter = filter;
		
		load();
	}

	public void load(){

		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/dat/"));

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
					//PositionedObstacleInterface obs = fs.loadObs(chosen.getName()));
				}
			}
			else{
				if(fs.checkAir(chosen)){
					AirportInterface airport = fs.loadAir(chosen.getName());
					frame.loadOrCreateAirport(airport);
					frame.getLogPanel().makeLog("Airport loaded.");
					frame.getLogPanel().repaint();
				}
			}
		}
	}
}
