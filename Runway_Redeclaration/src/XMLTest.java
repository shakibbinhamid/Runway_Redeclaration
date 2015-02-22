import io.FileSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



import Core.Airport;
import CoreInterfaces.AirportInterface;
import Exceptions.CannotMakeRunwayException;
import Exceptions.ParrallelRunwayException;
import Exceptions.VariableDeclarationException;

public class XMLTest {

	public static void main(String[] args) {
		Airport a = new Airport("Gatwick");
		double[] numbers = {10,11,12,13,14,15,16,17};
		try {
			a.addNewAirfield(78, numbers);
		} catch (ParrallelRunwayException | CannotMakeRunwayException
				| VariableDeclarationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileSystem fs = new FileSystem();
		System.out.println(fs.saveAir(a));
		String dir = "./Gatwick.air.xml";
		try {
			System.out.println(readFile(dir));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AirportInterface a2 = fs.loadAir("Gatwick.air.xml");
	}

	static String readFile(String path) 
			throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
	}

}
