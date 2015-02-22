package view;

import io.FileSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Core.Airport;
import Core.Obstacle;
import CoreInterfaces.Savable;

public class SaveListener implements ActionListener{

	private TopFrame frame;

	private Savable saveItem;
	
	public SaveListener(TopFrame frame){
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Take saveItem, serialise and verify
		FileSystem fs = new FileSystem();
		
		//Horrible code
		if(saveItem instanceof Obstacle){
			fs.saveObs((Obstacle)saveItem);
		}
		else{
			fs.saveAir((Airport)saveItem);
		}
	}
	
}
