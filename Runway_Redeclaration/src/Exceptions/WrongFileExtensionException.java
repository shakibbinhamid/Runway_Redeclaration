package Exceptions;
 
public class WrongFileExtensionException extends Exception {
 
    public String getFilename() {
        return filename;
    }
 
    public String getFileType() {
        return fileType;
    }
 
    private String filename, fileType;
     
    public WrongFileExtensionException(String filename, String fileType){
        super(filename + " is not a valid " + fileType + " name.");
        this.filename = filename;
        this.fileType = fileType;
    }
}