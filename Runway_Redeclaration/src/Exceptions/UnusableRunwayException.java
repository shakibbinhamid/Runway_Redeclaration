package Exceptions;

public class UnusableRunwayException extends Exception {
	private static final long serialVersionUID = 3873902900361987631L;
	private String id, reason;
	
	public UnusableRunwayException(String identifier, String reason){
		super("The declared runway "+identifier+" cannot be used due to "+reason);
		this.id = identifier;
		this.reason = reason;
	}

	public String getIdentifier(){
		return this.id;
	}

	public String getReason(){
		return this.reason;
	}
	
}
