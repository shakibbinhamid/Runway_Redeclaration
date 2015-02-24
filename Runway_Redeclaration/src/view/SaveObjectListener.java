package view;

import io.FileSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Core.Airport;
import Core.Obstacle;
import CoreInterfaces.Savable;

public class SaveObjectListener implements ActionListener{

	private TopFrame frame;

	private Savable saveItem;
	
	public SaveObjectListener(TopFrame frame){
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Take saveItem, serialise and verify
		FileSystem fs = new FileSystem();
		saveItem = (Savable) ((Tab)frame.getTabbePanel().getSelectedComponent()).getField().getPositionedObstacle().getUnpositionedObstacle();
		//Horrible code
		if(saveItem instanceof Obstacle){
			fs.saveObs((Obstacle)saveItem);
		}
		else{
			fs.saveAir((Airport)saveItem);
		}
	}
	
}
