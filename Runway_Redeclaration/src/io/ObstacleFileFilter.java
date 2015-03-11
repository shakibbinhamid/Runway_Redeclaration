package io;
 
import java.io.File;
 
public class ObstacleFileFilter extends javax.swing.filechooser.FileFilter{
 
    @Override
    public boolean accept(File arg0) {
        FileSystem fs = new FileSystem();
        return fs.checkObsExt(arg0) || arg0.isDirectory();
    }
 
    @Override
    public String getDescription() {
        return "Obstacles (*.obs.xml)";
    }
 
}