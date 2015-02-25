package view;

import io.FileSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Core.Airport;
import CoreInterfaces.Savable;
import Exceptions.NothingToSaveException;

public class SaveAirportListener implements ActionListener{
	
	private TopFrame frame;
	private Savable saveItem;
	
	public SaveAirportListener(TopFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Take saveItem, serialise and verify
		FileSystem fs = new FileSystem();
		saveItem = (Savable) frame.getAirport();
		try {
			fs.saveAir((Airport)saveItem);
		} catch (NothingToSaveException e) {
			JOptionPane.showMessageDialog(null, "There is no airport to save...", "No Airport Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}