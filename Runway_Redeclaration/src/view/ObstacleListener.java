package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObstacleListener implements ActionListener{

	TopFrame topFrame;
	
	public ObstacleListener(TopFrame topFrame){
		this.topFrame = topFrame;
	}
	
	public void actionPerformed(ActionEvent e) {		
		FormObstacle fo = new FormObstacle(topFrame);
		fo.init();
	}

}
