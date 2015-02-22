package view;

import io.FileSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import CoreInterfaces.AirportInterface;

public class LoadListener implements ActionListener{

	private TopFrame frame;

	public LoadListener(TopFrame frame){
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Airport Files", "xml"); //Could be extended for individual type choice in future (i.e. "Obstacle", "obj" and "Airport", "air"). Requires custom filter class
		fc.setFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));

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
					((Tab) frame.getTabbePanel().getSelectedComponent()).setObs(fs.loadObs(chosen.getName()));
				}
			}
			else{
				if(fs.checkAir(chosen)){
					AirportInterface airport = fs.loadAir(chosen.getName());
					frame.loadOrCreateAirport(airport);
				}
			}
		}
	}
}
