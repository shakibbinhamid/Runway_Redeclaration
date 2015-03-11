package listeners;

import io.AirportFileFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileFilter;

import javax.swing.filechooser.FileNameExtensionFilter;

import view.LoadListener;
import view.TopFrame;

public class LoadAirportListener implements ActionListener{

	private TopFrame frame;
	private AirportFileFilter filter;
	private String typeDir;
	
	public LoadAirportListener(TopFrame frame){
		this.frame = frame;
		filter = new AirportFileFilter(); 
		typeDir = "airports/";

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		LoadListener lis = new LoadListener(frame, filter, typeDir);
	}
}