package io;

import java.io.File;
import java.io.FileFilter;

public class ObstacleFileFilter extends javax.swing.filechooser.FileFilter{

	@Override
	public boolean accept(File arg0) {
		FileSystem fs = new FileSystem();
		return fs.checkObs(arg0) || arg0.isDirectory();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
