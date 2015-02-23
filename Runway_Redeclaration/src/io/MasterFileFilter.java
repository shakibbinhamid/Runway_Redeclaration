package io;

import java.io.File;

public class MasterFileFilter {

	//Returns true if chosen file is an airport file
	public boolean checkAir(File chosen) {
		String name = chosen.getName();
		if (name.split("\\.")[1].equals("air")){
			return true;
		}
		return false;
	}

	//Returns true if chosen file is an obstacle file
	public boolean checkObs(File chosen) {
		String name = chosen.getName();
		if (name.split("\\.")[1].equals("obj")){
			return true;
		}
		return false;
	}
}
