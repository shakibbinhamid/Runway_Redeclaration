package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadObstacleListener implements ActionListener{

	private TopFrame frame;
	private FileNameExtensionFilter filter;
	
	public LoadObstacleListener(TopFrame frame){
		filter = new FileNameExtensionFilter("Obstacle Files", "obs.xml"); 
		

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		LoadListener lis = new LoadListener(frame, filter);
		
	}
}
