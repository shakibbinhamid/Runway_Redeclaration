import java.io.File;
import java.util.ArrayList;

/**
 * 
 */

/**
 * This class handles the saving and loading of objects
 * @author Jonathan
 *
 */
public class FileSystem {
	
	private String wd;
	private String datDir = "\\dat";
	private String airDir = "\\airports";
	private String objDir = "\\obj";
	private File dat, airports, obstacles;
	
	public FileSystem(){
		wd = System.getProperty("user.dir");
		makedirs();
	}
	
	
	//Ensures the save directories exist
	private void makedirs() {
		dat = new File(wd + datDir);
		airports = new File(dat.getAbsolutePath() + airDir);
		obstacles = new File(dat.getAbsoluteFile() + objDir);
		dat.mkdirs();
		airports.mkdirs();
		obstacles.mkdirs();
	}

	//Returns a list of all files in dat
	public ArrayList<File> listAllFiles(){
		ArrayList<File> files = new ArrayList<File>();
		files.addAll(listAirports());
		files.addAll(listObstacles());
		
		return files;
	}
	
	//Returns a list of all stored airports
	public ArrayList<File> listAirports(){
		ArrayList<File> airportFiles = new ArrayList<File>();
		for(File file : airports.listFiles()){
			airportFiles.add(file);
		}
		return airportFiles;
	}
	
	//Returns a list of all stored obstacles
	public ArrayList<File> listObstacles(){
		ArrayList<File> objFiles = new ArrayList<File>();
		for(File file : obstacles.listFiles()){
			objFiles.add(file);
		}
		return objFiles;
	}

	public void save(Object o){
		
	}
}
