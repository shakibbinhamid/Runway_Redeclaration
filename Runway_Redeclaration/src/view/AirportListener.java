package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
