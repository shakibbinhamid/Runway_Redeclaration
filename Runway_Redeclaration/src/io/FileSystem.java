package io;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Core.Airport;
import Core.Obstacle;
import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.ObstacleInterface;
import Exceptions.CannotMakeRunwayException;
import Exceptions.NothingToSaveException;
import Exceptions.UnrecognisedAirfieldIntifierException;
import Exceptions.VariableDeclarationException;
import view.Notification;
import view.View;

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

	public boolean saveObs(Obstacle o){
		String dir = wd + datDir + objDir + "/" + o.getName() + objext + xmlext;
		return XMLSaver.serialise(o, dir);
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
		if(a == null){
			throw new NothingToSaveException();
		}
		String dir = wd + datDir + airDir + "/" + a.getName()+airext + xmlext;
		Notification.notify("Saving airport to " + dir + "...");
		return XMLSaver.serialise(a, dir);
	}

	public AirportInterface loadAir(String fileName){
		File airFile = new File(wd + datDir + airDir + "/" + fileName);
		//loadingNotification(fileName);
		AirportInterface a = (AirportInterface) XMLSaver.deserialise(Airport.class, airFile);
		return a;
	}

	public AirportInterface loadAir(File airFile){
		loadingNotification(airFile.getName());
		AirportInterface a = (AirportInterface) XMLSaver.deserialise(Airport.class, airFile);
		return a;
	}

	private void loadingNotification(String name){
		Notification.notify("Loading " + name + "...");
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
}
