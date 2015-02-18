import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import Core.Obstacle;
/**
 * 
 * Serialises objects with XML
 * 
 * @author Jon
 */

public class XMLParser {

	public static boolean serialise(Savable object, String name){
		Serializer serializer = new Persister();
		File result = new File(name + ".xml");
		try {
			serializer.write(object, result);
			return true;
		} catch (Exception e) {
			return false;
			e.printStackTrace();
		}
	}
	
	public static Object deserialise(Class classType, File xmlFile){
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
