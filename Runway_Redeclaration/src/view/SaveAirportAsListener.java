package view;
 
import io.AirportFileFilter;
import io.FileSystem;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
 
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
 
import Core.Airport;
import CoreInterfaces.Savable;
import Exceptions.NothingToSaveException;
import Exceptions.WrongFileExtensionException;
 
public class SaveAirportAsListener  implements ActionListener{
    private TopFrame frame;
    private Savable saveItem;
     
    public SaveAirportAsListener(TopFrame frame){
        this.frame = frame;
    }
 
    @Override
    public void actionPerformed(ActionEvent arg0) {
        FileSystem fs = new FileSystem();
        saveItem = (Savable) frame.getAirport();
        JFileChooser fsaver = createFileChooser(fs);
        saveInterface(fs, fsaver);
    }
 
    private void saveInterface(FileSystem fs, JFileChooser fsaver) {
        int userOption = fsaver.showSaveDialog(frame);
        File selectedFile =  fsaver.getSelectedFile();
        if ((selectedFile != null) && selectedFile.exists()) {
            int response = JOptionPane.showConfirmDialog(frame, "The file " + selectedFile.getName() + " already exists. Do you want to replace the existing file?", "Ovewrite file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == JOptionPane.YES_OPTION)
                saveWithError(fs, userOption, selectedFile, fsaver);
        }
        else {
            saveWithError(fs, userOption, selectedFile, fsaver);
        }
    }
 
    private void saveWithError(FileSystem fs, int userOption, File selectedFile, JFileChooser fsaver) {
        try {
            save(fs, userOption, selectedFile);
        } catch (WrongFileExtensionException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            saveInterface(fs, fsaver);
        }
    }
 
    private JFileChooser createFileChooser(FileSystem fs) {
        JFileChooser fsaver = new JFileChooser();
        fsaver.setDialogTitle("Save Airport As...");
        fsaver.setCurrentDirectory(new File(fs.getAirDir()));
        fsaver.setSelectedFile(new File(fs.getAirDir() + frame.getAirport().getName()));
        fsaver.setFileFilter(new AirportFileFilter());
        fsaver.setAcceptAllFileFilterUsed(false);
        return fsaver;
    }
 
    private void save(FileSystem fs, int userOption, File selectedFile) throws WrongFileExtensionException {
        if(userOption == JFileChooser.APPROVE_OPTION){
            try {
                if(!fs.checkAirExt(selectedFile)){
                    if(!selectedFile.getName().contains(".")){
                        selectedFile = new File(selectedFile.getAbsolutePath() + fs.getAirExt());
                    }
                    else{
                        throw new WrongFileExtensionException(selectedFile.getName(), "airport");
                    }
                }
                fs.saveAir((Airport)saveItem, selectedFile);
            } catch (NothingToSaveException e) {
                JOptionPane.showMessageDialog(null, "There is no airport to save...", "No Airport Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
 
     
 
}