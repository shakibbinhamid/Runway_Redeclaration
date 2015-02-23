package io;

import java.io.File;
import java.io.FileFilter;

public class AirportFileFilter extends MasterFileFilter implements FileFilter {

	@Override
	public boolean accept(File arg0) {
		return checkAir(arg0);
	}

}
