package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.FormEditObstacle;
import view.TopFrame;

public class ObstacleEditListener implements ActionListener{

	TopFrame topFrame;
	
	public ObstacleEditListener(TopFrame topFrame){
		this.topFrame = topFrame;
	}
	
	public void actionPerformed(ActionEvent e) {		
		FormEditObstacle fo = new FormEditObstacle(topFrame);
	}

}
