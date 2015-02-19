package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadListener implements ActionListener{
	
	private TopFrame frame;
	
	public LoadListener(TopFrame frame){
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO create filechooser, select airport/obstacle, set frame.those_Stuff object. if
		//if frame.getAirportInterface() is null, don't let it load obstacle.
		//in that case make a pop up
		
		//create helper loader if you need.
	}

}
