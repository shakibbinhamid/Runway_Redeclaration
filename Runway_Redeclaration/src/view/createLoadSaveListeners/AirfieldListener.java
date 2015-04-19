package view.createLoadSaveListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.TopFrame;
import view.createEditForms.FormCreateAirfield;

public class AirfieldListener implements ActionListener{
	private TopFrame topFrame;
	
	public AirfieldListener(TopFrame topFrame){
		this.topFrame = topFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FormCreateAirfield fa = new FormCreateAirfield(topFrame);
		fa.init();
	}
	
}
