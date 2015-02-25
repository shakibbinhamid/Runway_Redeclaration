package view;

import io.ObstacleFileFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.filechooser.FileNameExtensionFilter;

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
		LoadListener lis = new LoadListener(frame, filter, typeDir);
	}
}
