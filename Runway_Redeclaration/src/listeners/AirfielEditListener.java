package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.FormEditAirfield;
import view.TopFrame;

public class AirfielEditListener implements ActionListener{
	private TopFrame topFrame;
	
	public AirfielEditListener(TopFrame topFrame){
		this.topFrame = topFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unused")
		FormEditAirfield fa = new FormEditAirfield(topFrame);
	}
	
}
