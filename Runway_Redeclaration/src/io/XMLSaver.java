package io;
import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import core.interfaces.Savable;
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
		try {
			serializer.write(object, result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Savable deserialise(@SuppressWarnings("rawtypes") Class classType, File xmlFile){
		Serializer serializer = new Persister();
		try {
			return (Savable) serializer.read(classType, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
