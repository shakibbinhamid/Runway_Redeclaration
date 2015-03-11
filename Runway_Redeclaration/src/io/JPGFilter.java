package io;

import java.io.File;


public class JPGFilter extends ImageFilter{
	 public boolean accept(File f) {
         return f.getName().endsWith(".jpg") || f.getName().endsWith(".jpeg");
     }

     public String getDescription() {
         return "JPEG file";
     }
     
     public String getExt(){
     	return ".jpg";
     }
     
     public String getName(){
    	 return "JPG";
     }
}
