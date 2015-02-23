package io;

import java.io.File;
import java.io.FileFilter;

public class ObstacleFileFilter extends MasterFileFilter implements FileFilter{

	@Override
	public boolean accept(File arg0) {
		return checkObs(arg0);
	}

}
