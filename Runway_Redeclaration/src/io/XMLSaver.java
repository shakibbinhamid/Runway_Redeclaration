package io;
import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


import CoreInterfaces.Savable;
/**
 * 
 * Serialises objects with XML
 * 
 * @author Jon
 */

public class XMLSaver {

	public static boolean serialise(Savable object, String dir){
		Serializer serializer = new Persister();
		File result = new File(dir);
		System.out.println(result.getAbsolutePath());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
