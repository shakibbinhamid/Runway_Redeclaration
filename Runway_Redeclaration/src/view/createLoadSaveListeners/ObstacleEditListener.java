package view.createLoadSaveListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.TopFrame;
import view.createEditForms.FormEditObstacle;

public class ObstacleEditListener implements ActionListener{

	TopFrame topFrame;
	
	public ObstacleEditListener(TopFrame topFrame){
		this.topFrame = topFrame;
	}
	
	public void actionPerformed(ActionEvent e) {		
		@SuppressWarnings("unused")
		FormEditObstacle fo = new FormEditObstacle(topFrame);
	}

}
