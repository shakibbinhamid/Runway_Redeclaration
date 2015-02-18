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
		
	}

	static String readFile(String path) 
			throws IOException 
			{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
			}

}
