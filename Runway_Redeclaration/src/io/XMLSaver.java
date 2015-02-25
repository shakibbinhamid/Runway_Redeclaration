package io;
import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import Core.Airport;
import Core.Obstacle;
import Core.ParrallelRunwayException;
import CoreInterfaces.Savable;
import Exceptions.CannotMakeRunwayException;
import Exceptions.VariableDeclarationException;
/**
 * 
 * Serialises objects with XML
 * 
 * @author Jon
 */

public class XMLSaver {
	
	public static void main(String[] args) {
		Airport a = new Airport("Gatwick");
		double[] dimensions = {1400,160,123,16,34,216,357,124};
		double[] small = {1000, 100, 150, 0};
		double[] big = {1000, 10, 20, 0};

		double[] dimensions2 = {14,16,123,16,34,216,357,124};
		double[] small2 = {1343, 134, 150, 0};
		double[] big2 = {1000, 10, 20, 0};
		Obstacle o = new Obstacle("hello", 2, 3);
		try {
			a.addNewAirfield(65, dimensions, small, big);
			a.addNewAirfield(30, dimensions2, small2, big2);
		} catch (ParrallelRunwayException | CannotMakeRunwayException
				| VariableDeclarationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileSystem fs = new FileSystem();
		fs.saveAir(a);
		fs.saveObs(o);
	}

	public static boolean serialise(Savable object, String dir){
		Serializer serializer = new Persister();
		File result = new File(dir);
		try {
			serializer.write(object, result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Savable deserialise(Class classType, File xmlFile){
		Serializer serializer = new Persister();
		try {
			return serializer.read(classType, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
