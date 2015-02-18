import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import Core.Obstacle;



public class XMLTest {

	public static void main(String[] args) {
		Serializer serializer = new Persister();
		Obstacle example = new Obstacle("obj name", 123, 0);
		File result = new File("example.xml");

		try {
			serializer.write(example, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(readFile(result.getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Serializer serializer2 = new Persister();
		Obstacle obj = null;
		try {
			obj = serializer2.read(Obstacle.class, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(obj.toString());

	}
	
	static String readFile(String path) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded);
			}

}
