package io;

import java.io.File;
import java.io.FileFilter;

public class AirportFileFilter extends javax.swing.filechooser.FileFilter {

	@Override
	public boolean accept(File arg0) {
		FileSystem fs = new FileSystem();
		return fs.checkAir(arg0) || arg0.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Airports";
	}

}
