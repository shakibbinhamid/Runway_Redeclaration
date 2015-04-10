package io;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CustomFilter extends FileFilter{
	
	private String name, ext, description;

	public CustomFilter(String name, String ext, String description){
		this.name = name;
		this.ext = ext;
		this.description = description;
	}
	
	public boolean accept(File f) {
		 return f.getName().endsWith(ext) || f.isDirectory();
	}
	public String getDescription() {
		return description;
	}
	public String getExt() {
		return ext;
	}
	public String getName() {
		return name;
	}
	
	
	//Commonly used filters
	public static CustomFilter getAirFilter(){
		FileSystem fs = new FileSystem();
		return new CustomFilter("Airports", fs.getAirExt(), "Airports (*.air.xml)");
	}
	
	public static CustomFilter getObsFilter(){
		FileSystem fs = new FileSystem();
		return new CustomFilter("Obstacles", fs.getObsExt(), "Obstacles (*.obs.xml)");
	}

}
