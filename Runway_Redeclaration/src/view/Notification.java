package view;

import java.awt.Color;


public class Notification {
	static TopFrame frame;
	
	public static void setFrame(TopFrame newFrame){
		frame = newFrame;
	}

	public static void notify(String s){
		frame.getLogPanel().notify(s);
	}
	
	public static void notify(String s, String c){
		frame.getLogPanel().notify(s, c);
	}
	
	public static void notify(String s, Color c){
		frame.getLogPanel().notify(s, c);
	}
}
