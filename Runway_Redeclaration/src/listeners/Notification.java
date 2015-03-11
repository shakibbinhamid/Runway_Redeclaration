package listeners;

import view.TopFrame;


public class Notification {
	static TopFrame frame;
	 
	public static void setFrame(TopFrame newFrame){
		frame = newFrame;
	}
	
	public static void notify(String s, String c){
		frame.getLogPanel().notify(s, c);
	}

}
