package notification;
/**
 * This is a backbone of a Notification
 * A Notification has a title, details.
 * It also has a type.
 * @author shakib-bin hamid
 *
 */
public class Notification {
	
	public static final String FILE = "file";
	public static final String CALC = "calc";
	public static final String ERROR = "error";
	public static final String DEFAULT = "default";
	
	private String title;
	private String details;
	private String type;
	
	private boolean read;
	
	public Notification(String title, String details, String type){
		setTitle(title);
		setDetails(details);
		setType(type);
	}
	
	public void read(){
		read = true;
	}
	
	public void unread(){
		read = false;
	}
	
	public boolean hasRead(){
		return read;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
