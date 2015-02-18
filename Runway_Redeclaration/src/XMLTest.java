import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import Core.Obstacle;

public class XMLTest {

	public static void main(String[] args) {
		Obstacle o = new Obstacle("Brian", 12, 13);
		FileSystem fs = new FileSystem();
		System.out.println(fs.saveObs(o));
		
		ArrayList<File> files = fs.listObstacles();
		System.out.println("Files:");
		for(File file : files){
			System.out.println(file.getName());
		}
		
		String brian = files.get(0).getName();
		System.out.println("\nAttempting to load " + brian + "...\n");
		
		Obstacle o2 = fs.loadObs(brian);
		
		System.out.println("Obstacle loaded. \nName: " + o2.getName() + "\nHeight: " + o2.getHeight() + "\nRadius: " + o2.getRadius());
	}

	static String readFile(String path) 
			throws IOException 
			{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
			}

}
