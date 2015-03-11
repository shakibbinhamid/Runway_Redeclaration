package io;

import java.io.File;


public class PNGFilter extends ImageFilter{
	 public boolean accept(File f) {
         return f.getName().endsWith(".png") || f.isDirectory();
     }

     public String getDescription() {
         return "PNG file";
     }
     
     public String getExt(){
     	return ".png";
     }
     
     public String getName(){
    	 return "PNG";
     }
}
