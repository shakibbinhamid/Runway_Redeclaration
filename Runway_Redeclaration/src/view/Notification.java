package view;


public class Notification {
	static TopFrame frame;
	
	public static void setFrame(TopFrame newFrame){
		frame = newFrame;
	}

	public static void notify(String s){
		//frame.getLogPanel().notify("\n" + s);
	}
}
