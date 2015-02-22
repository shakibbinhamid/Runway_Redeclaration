package io;
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
	private String datDir = "/dat";
	private String airDir = "/airports";
	private String objDir = "/obs";
	
	private String xmlext = ".xml";
	private String objext = ".obj";
	private String airext = ".air";
	
	private File dat, airports, obstacles;
	
	public static void main(String[] s){
		FileSystem fs = new FileSystem();
	}
	
	public FileSystem(){
		wd = "./";
		makedirs();
	}
	
	//Ensures the save directories exist
	private void makedirs() {
		dat = new File(wd + datDir);
		airports = new File(dat.getAbsolutePath() + airDir);
		obstacles = new File(dat.getAbsoluteFile() + objDir);
		//System.out.println(dat);
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
		String dir = o.getName() + objext + xmlext;
		return XMLSaver.serialise(o, dir);
	}
	
	public Obstacle loadObs(String fileName){
		File obsFile = new File(fileName);
		Obstacle loadedObs = (Obstacle) XMLSaver.deserialise(Obstacle.class, obsFile);
		return loadedObs;
	}
	
	public boolean saveAir(Airport a){
		String dir = a.getName()+airext + xmlext;
		return XMLSaver.serialise(a, dir);
	}
	
	public Airport loadAir(String fileName){
		File airFile = new File(fileName);
		Airport a = (Airport) XMLSaver.deserialise(Airport.class, airFile);
		return a;
	}
	
	//Returns true if chosen file is an airport file
	public boolean checkAir(File chosen) {
		String name = chosen.getName();
		if (name.split("\\.")[1] == "air"){
			return true;
		}
		return false;
	}

	//Returns true if chosen file is an obstacle file
	public boolean checkObs(File chosen) {
		System.out.println(chosen.getAbsolutePath());
		String name = chosen.getName();
		String[] parts = name.split("\\.");
		for(int i = 0; i<parts.length; i++)
			System.out.println(parts[i]);
		if (name.split("\\.")[1] == "obj"){
			return true;
		}
		return false;
	}
}
