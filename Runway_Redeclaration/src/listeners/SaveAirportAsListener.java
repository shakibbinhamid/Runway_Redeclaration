package listeners;
 
import io.CustomFilter;

import java.awt.event.ActionListener;
import java.io.File;

import core.Airport;
import coreInterfaces.Savable;
import exceptions.NothingToSaveException;
import view.TopFrame;
 
public class SaveAirportAsListener extends SaveSaveableAsListener  implements ActionListener{
    public SaveAirportAsListener(TopFrame frame){
    	super("airport", "Airport", CustomFilter.getAirFilter());
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