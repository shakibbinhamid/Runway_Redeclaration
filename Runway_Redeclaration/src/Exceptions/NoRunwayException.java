package Exceptions;

public class NoRunwayException extends Exception{
	public NoRunwayException(String airfieldIdentifier) {
		super(airfieldIdentifier+" has no declared runways");
	}
}
