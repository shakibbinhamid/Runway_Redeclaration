package Exceptions;

public class NoRunwaysException extends Exception{
	public NoRunwaysException(String airfieldIdentifier) {
		super(airfieldIdentifier+" has no declared runways");
	}
}
