package view.createLoadSaveListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.TopFrame;
import view.createEditForms.FormAirport;

public class AirportListener implements ActionListener{
	private TopFrame topFrame;
	
	public AirportListener(TopFrame topFrame){
		this.topFrame = topFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FormAirport fa = new FormAirport(topFrame);
		fa.init();		
	}
}
