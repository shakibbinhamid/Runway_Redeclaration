package view.createLoadSaveListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.TopFrame;
import view.createEditForms.FormObstacle;

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
