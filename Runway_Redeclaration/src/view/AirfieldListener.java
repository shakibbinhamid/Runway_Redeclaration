package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirfieldListener implements ActionListener{
	private TopFrame topFrame;
	
	public AirfieldListener(TopFrame topFrame){
		this.topFrame = topFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FormAirfield fa = new FormAirfield(topFrame);
		fa.init();
	}
	
}
