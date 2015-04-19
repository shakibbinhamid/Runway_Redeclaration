package view.createLoadSaveListeners;
 
import io.CustomFilter;

import java.awt.event.ActionListener;
import java.io.File;

import core.concrete.Obstacle;
import core.interfaces.Savable;
import exceptions.NothingToSaveException;
import view.TopFrame;
import view.panels.Tab;
 
public class SaveObstacleAsListener extends SaveSaveableAsListener  implements ActionListener{
	public SaveObstacleAsListener(TopFrame frame){
    	super("obstacle", "Obstacle", CustomFilter.getObsFilter());
        this.frame = frame;
    }

	@Override
	public void fsSave(Savable saveItem, File selectedFile)
			throws NothingToSaveException {
		fs.saveObs((Obstacle) saveItem, selectedFile);
	}

	@Override
	public String getExt() {
		return fs.getObsExt();
	}

	@Override
	public String getDir() {
		return fs.getObsDir();
	}

	@Override
	public boolean fsCheckExt(File selectedFile) {
		return fs.checkObsExt(selectedFile);
	}

	@Override
	public String fsGetItemName() {
		return ((Tab)frame.getTabbePanel().getSelectedComponent()).getField().getPositionedObstacle().getUnpositionedObstacle().getName();
	}

	@Override
	public Savable getSavableItem() {
		return (Savable) ((Tab)frame.getTabbePanel().getSelectedComponent()).getField().getPositionedObstacle().getUnpositionedObstacle();
	}
 
 
}