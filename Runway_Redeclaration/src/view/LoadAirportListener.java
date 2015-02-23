package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadAirportListener implements ActionListener{

	private TopFrame frame;
	private FileNameExtensionFilter filter;
	
	public LoadAirportListener(TopFrame frame){
		filter = new FileNameExtensionFilter("Airport Files", "air.xml"); 
		

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		LoadListener lis = new LoadListener(frame, filter);
		
	}
}