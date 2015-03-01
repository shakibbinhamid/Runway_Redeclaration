package view;

import io.ObstacleFileFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class LoadObstacleListener implements ActionListener{

	private TopFrame frame;
	private ObstacleFileFilter filter;
	private String typeDir;

	public LoadObstacleListener(TopFrame frame){
		this.frame = frame;
		filter = new ObstacleFileFilter(); 
		typeDir = "obs/";

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(frame.getAirport() != null){
			LoadListener lis = new LoadListener(frame, filter, typeDir);
		}
		else{
			JOptionPane.showMessageDialog(null, "You cannot load an object while there is no airport to load it to.", "No Airport Error", JOptionPane.ERROR_MESSAGE);

		}
	}
}
