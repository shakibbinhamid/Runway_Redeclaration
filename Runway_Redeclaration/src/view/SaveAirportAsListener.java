package view;
 
import io.AirportFileFilter;

import java.awt.event.ActionListener;
import java.io.File;

import Core.Airport;
import CoreInterfaces.Savable;
import Exceptions.NothingToSaveException;
 
public class SaveAirportAsListener extends SaveSaveableAsListener  implements ActionListener{
    public SaveAirportAsListener(TopFrame frame){
    	super("airport", "Airport", new AirportFileFilter());
        this.frame = frame;
    }

	@Override
	public void fsSave(Savable saveItem, File selectedFile)
			throws NothingToSaveException {
		fs.saveAir((Airport) saveItem, selectedFile);
	}

	@Override
	public String getExt() {
		return fs.getAirExt();
	}

	@Override
	public String getDir() {
		return fs.getAirDir();
	}

	@Override
	public boolean fsCheckExt(File selectedFile) {
		return fs.checkAirExt(selectedFile);
	}

	@Override
	public String fsGetItemName() {
		return frame.getAirport().getName();
	}

	@Override
	public Savable getSavableItem() {
		return (Savable) frame.getAirport();
	}
 
     
 
}