package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * 
 * This panel shows the central log and the current airport name
 * 
 * @author shakib-binhamid
 * @editor Jonathon
 */
public class LogPanel extends JPanel{
	
	private String label;
	private JLabel airportLabel;
	private Log logPanel;
	
	private static final Color FILE = new Color(128, 0, 0);
	private static final Color CALC = new Color(75, 0, 130);
	private static final Color DEFAULT = Color.WHITE;
	private static final Color ERROR = Color.RED;
	
	public LogPanel(String name){
		this.label = name;
		init();
	}
	
	private void init(){
		
		//this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		this.setLayout(new BorderLayout());
		
		airportLabel = new JLabel("Airport");
		airportLabel.setPreferredSize(new Dimension(300,20));
		this.add(airportLabel, BorderLayout.NORTH);
		
		if(label != null)
			updateLabelText(label);
		
		logPanel = new Log();
		this.add(logPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Update the airport label to the specified name
	 * @param airportName the name of an airport
	 */
	public void updateLabelText(String airportName){
		airportLabel.setText(airportName);
	}
	
	public void notify(String s){
		logPanel.addLog(s);
	}
	
	public void notify(String s, String c){
		switch(c){
		case "file":
			notify(s, FILE);
			break;
		case "calc":
			notify(s, CALC);
			break;
		default:
			notify(s, DEFAULT);
		}
	}
	
	public void notify(String s, Color c){
		logPanel.addLog(s, c);
	}

	/**
	 * The log text area is contained here
	 * @author shakib-binhamid
	 * @editor Jonathon
	 */
	private class Log extends JPanel{
		private JScrollPane scroll;
		private JTextPane text;
		
		private Log(){
			
			text = new JTextPane();
			scroll = new JScrollPane(text);
			
			init();
		}
		
		private void init(){
			this.setLayout(new BorderLayout());
			text.setBackground(new Color(191, 239, 255));
			text.setEditable(false);
			
			this.add(scroll, BorderLayout.CENTER);
		}
		
		/*
		 * adds a log to the text area
		 */
		private void addLog(String s){
			addLog(s, DEFAULT);
		}
		
		private void appendToPane(String msg, Color c){

	        StyleContext sc = StyleContext.getDefaultStyleContext();
	        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

	        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
	        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

	        int len = text.getDocument().getLength();
	        text.setFont(new Font("verdana", Font.PLAIN, 12));
	        text.setCaretPosition(len);
	        text.setCharacterAttributes(aset, false);
	        text.replaceSelection(msg);
	    }

		
		private void addLog(String s, Color c){
			text.setEditable(true);
			appendToPane(s, c);
			appendToPane("\n", c);
			text.setEditable(false);
		}
	}
}


