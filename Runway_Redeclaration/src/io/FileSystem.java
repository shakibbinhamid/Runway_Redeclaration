package io;
import java.io.File;
import java.util.ArrayList;

import listeners.Notification;
import Core.Airport;
import Core.Obstacle;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.ObstacleInterface;
import CoreInterfaces.Savable;
import Exceptions.NothingToSaveException;

/**
 * Handles the saving and loading of objects
 * @author Jonathan
 *
 */
public class FileSystem {

	private String wd = "./";
	private String datDir = "/dat";
	private String airDir = "/airports";
	private String objDir = "/obs";

	private String xmlext = ".xml";
	private String objext = ".obj";
	private String airext = ".air";

	private File dat, airports, obstacles;

	public FileSystem(){
		makedirs();
	}

	//Ensures the save directories exist
	private void makedirs() {
		dat = new File(wd + datDir);
		airports = new File(dat.getAbsolutePath() + airDir);
		obstacles = new File(dat.getAbsoluteFile() + objDir);

		if(!dat.exists())
			dat.mkdirs();
		if(!airports.exists())
			airports.mkdirs();
		if(!obstacles.exists())
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

	public boolean saveObs(Obstacle o) throws NothingToSaveException{
		return saveObs(o, new File(getObsDir() + o.getName() + getObsExt()));
	}
	
	public boolean saveObs(Obstacle o, File savefile) throws NothingToSaveException{

		checkNull(o);
		String dir = savefile.getAbsolutePath();
		if(XMLSaver.serialise(o, dir)){
			Notification.notify("Saving obstacle to \n" + dir + "...", "file");
			return true;
		}
		else {
			return false;
		}
	}

	public ObstacleInterface loadObs(String fileName){
		File obsFile = new File(wd + datDir + objDir + "/"+fileName);
		loadingNotification(fileName);
		ObstacleInterface loadedObs = (ObstacleInterface) XMLSaver.deserialise(Obstacle.class, obsFile);
		return loadedObs;
	}

	public ObstacleInterface loadObs(File obsFile){
		loadingNotification(obsFile.getName());
		ObstacleInterface loadedObs = (ObstacleInterface) XMLSaver.deserialise(Obstacle.class, obsFile);
		return loadedObs;
	}

	public boolean saveAir(Airport a) throws NothingToSaveException{
		return saveAir(a, new File(getAirDir() + a.getName() + getAirExt()));
	}
	
	public boolean saveAir(Airport a, File savefile) throws NothingToSaveException{
		checkNull(a);
		String dir = savefile.getAbsolutePath();
		if(XMLSaver.serialise(a, dir)){
			Notification.notify("Saving airport to \n" + dir + "...", "file");
			return true;
		}
		else {
			return false;
		}
	}
	
	private void checkNull(Savable a) throws NothingToSaveException {
		if(a == null){
			throw new NothingToSaveException();
		}
	}

	public AirportInterface loadAir(File airFile){
		loadingNotification(airFile.getName());
		AirportInterface a = (AirportInterface) XMLSaver.deserialise(Airport.class, airFile);
		return a;
	}

	private void loadingNotification(String name){
		Notification.notify("Loading " + name + "...", "file");
	}

	//Returns true if chosen file is an airport file
	public boolean checkAir(File chosen) {
		try{
			String name = chosen.getName();
			if (name.split("\\.")[1].equals("air")){
				return true;
			}
		}
		catch (Exception e){

		}
		return false;
	}

	//Returns true if chosen file is an obstacle file
	public boolean checkObs(File chosen) {
		try{
			String name = chosen.getName();
			if (name.split("\\.")[1].equals("obj")){
				return true;
			}
		}
		catch (Exception e){

		}
		return false;
	}
	
	public boolean checkAirExt(File chosen) {
		String dir = chosen.getName();
		String[] exts = dir.split("\\.");
		
		if(exts.length < 3)								return false;
		if(!exts[exts.length - 1].equals("xml")) 		return false;
		if(!exts[exts.length - 2].equals("air")) 		return false;
		
		return true;
		
	}
	
	public boolean checkObsExt(File chosen) {
		String dir = chosen.getName();
		String[] exts = dir.split("\\.");
		
		if(exts.length < 3)								return false;
		if(!exts[exts.length - 1].equals("xml")) 		return false;
		if(!exts[exts.length - 2].equals("obj")) 		return false;
		
		return true;
		
	}
	
	public String getAirDir(){
		return wd + datDir + airDir + "/";
	}
	
	public String getObsDir(){
		return wd + datDir + objDir + "/";
	}
	
	public String getAirExt(){
		return airext + xmlext;
	}
	
	public String getAirExtOnly(){
		return airext;
	}
	
	public String getObsExt(){
		return objext + xmlext;
	}
	
	public String getXMLExt(){
		return xmlext;
	}
}
