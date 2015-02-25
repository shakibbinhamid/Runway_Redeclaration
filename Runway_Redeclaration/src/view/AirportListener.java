package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirportListener implements ActionListener{
	private TopFrame topFrame;
	
	// test
	public static void main(String[] args) {
		TopFrame tf = new TopFrame();
		FormAirport fa = new FormAirport(tf);
		fa.init();
	}
	
	public AirportListener(TopFrame topFrame){
		this.topFrame = topFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FormAirport fa = new FormAirport(topFrame);
		fa.init();		
	}

	
}
