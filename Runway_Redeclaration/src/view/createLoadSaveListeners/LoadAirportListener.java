package view.createLoadSaveListeners;


import io.CustomFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.TopFrame;

public class LoadAirportListener implements ActionListener{

	private TopFrame frame;
	private CustomFilter filter;
	private String typeDir;
	
	public LoadAirportListener(TopFrame frame){
		this.frame = frame;
		filter = CustomFilter.getAirFilter(); 
		typeDir = "airports/";

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		@SuppressWarnings("unused")
		LoadListener lis = new LoadListener(frame, filter, typeDir);
	}
}