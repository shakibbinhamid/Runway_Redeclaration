package exceptions;


public class NothingToSaveException extends Exception {
	private static final long serialVersionUID = 5420358412062326106L;

	public NothingToSaveException(){
		super("There is nothing to save!");
	}
}
