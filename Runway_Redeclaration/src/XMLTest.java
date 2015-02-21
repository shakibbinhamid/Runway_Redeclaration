import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import Core.Airport;
import Core.Obstacle;
import Exceptions.CannotMakeRunwayException;
import Exceptions.ParrallelRunwayException;
import Exceptions.VariableDeclarationException;

public class XMLTest {

	public static void main(String[] args) {
		Obstacle o = new Obstacle("Bob", 0, 0);
		FileSystem fs = new FileSystem();
		System.out.println(fs.saveObs(o));
	}

	static String readFile(String path) 
			throws IOException 
			{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
			}

}
