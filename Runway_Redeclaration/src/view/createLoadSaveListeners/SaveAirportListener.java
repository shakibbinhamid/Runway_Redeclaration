package view.createLoadSaveListeners;

import io.FileSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import core.concrete.Airport;
import core.interfaces.Savable;
import exceptions.NothingToSaveException;
import view.TopFrame;

public class SaveAirportListener implements ActionListener{

	private TopFrame frame;
	private Savable saveItem;

	public SaveAirportListener(TopFrame frame){
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			FileSystem fs = new FileSystem();
			saveItem = (Savable) frame.getAirport();
			if(saveItem == null){
				JOptionPane.showMessageDialog(null, "There is no airport to save...", "No Airport Error", JOptionPane.ERROR_MESSAGE);
			}
			else{
				fs.saveAir((Airport)saveItem);
			}
		} catch (NothingToSaveException e) {
			JOptionPane.showMessageDialog(null, "There is no airport to save...", "No Airport Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}