package view;
 
import io.ObstacleFileFilter;

import java.awt.event.ActionListener;
import java.io.File;

import Core.Obstacle;
import CoreInterfaces.Savable;
import Exceptions.NothingToSaveException;
 
public class SaveObstacleAsListener extends SaveSaveableAsListener  implements ActionListener{
	public SaveObstacleAsListener(TopFrame frame){
    	super("obstacle", "Obstacle", new ObstacleFileFilter());
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