package view;

import io.FileSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadListener implements ActionListener{

	private TopFrame frame;

	public LoadListener(TopFrame frame){
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO create filechooser, select airport/obstacle, set frame.those_Stuff object. if
		//if frame.getAirportInterface() is null, don't let it load obstacle.
		//in that case make a pop up

		//create helper loader if you need.

		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Airport Files", "xml"); //Could be extended for individual type choice in future (i.e. "Obstacle", "obj" and "Airport", "air"). Requires custom filter class
		fc.setFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);

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
					frame.setObstacle(fs.loadObs(chosen.getName()));
				}
			}
			else{
				if(fs.checkAir(chosen)){
					frame.setAirport(fs.loadAir(chosen.getName()));
				}
			}
		}
	}



}
