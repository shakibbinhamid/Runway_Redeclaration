package io;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public abstract class ImageFilter extends FileFilter{

	
	public abstract boolean accept(File arg0);
	public abstract String getDescription();
	public abstract String getExt();
	public abstract String getName();

}
