import java.io.File;
import java.util.ArrayList;

import Core.Airport;
import Core.Obstacle;

/**
 * Handles the saving and loading of objects
 * @author Jonathan
 *
 */
public class FileSystem {
	
	private String wd;
	private String datDir = "\\dat";
	private String airDir = "\\airports";
	private String objDir = "\\obj";
	private String sub = "\\";
	private String xmlext = ".xml";
	private String objext = ".obj";
	private String airext = ".air";
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

	public boolean saveObs(Obstacle o){
		String dir = wd + datDir + objDir + sub + o.getName() + objext + xmlext;
		return XMLSaver.serialise(o, dir);
	}
	
	public Obstacle loadObs(String fileName){
		File obsFile = new File(wd + datDir + objDir + sub + fileName);
		Obstacle loadedObs = (Obstacle) XMLSaver.deserialise(Obstacle.class, obsFile);
		return loadedObs;
	}
	
	public boolean saveAir(Airport a){
		String dir = wd + datDir + airDir + sub + a.getName() + airext + xmlext;
		return XMLSaver.serialise(a, dir);
	}
}
