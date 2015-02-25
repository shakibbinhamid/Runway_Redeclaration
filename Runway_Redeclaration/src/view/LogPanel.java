package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

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
	
	private Color file, calc, defaultc;
	
	public LogPanel(String name){
		this.label = name;
		file = Color.blue;
		calc = Color.green;
		defaultc = Color.black;
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
		{
			notify(s, file);
			
			break;
		}
		case "calc":
		{
			notify(s, calc);
			break;
		}
		default:
		{
			notify(s, defaultc);
		}
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
		private JTextArea text;
		private Document doc;
		
		private Log(){
			
			text = new JTextArea();
			scroll = new JScrollPane(text);
			doc = text.getDocument();
			
			init();
		}
		
		private void init(){
			this.setLayout(new BorderLayout());
			
			text.setEditable(false);
			text.setLineWrap(true);
			
			this.add(scroll, BorderLayout.CENTER);
		}
		
		/*
		 * adds a log to the text area
		 */
		private void addLog(String s){
			addLog(s, defaultc);
		}
		
		private void addLog(String s, Color c){
			/*StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
			text.setCharacterAttributes(aset, false); */
			text.setForeground(c);
			try {
			      doc.insertString(doc.getLength(), "\n\n" + s, null);
			      scroll.repaint();
			      
			} catch(BadLocationException exc) {
			      exc.printStackTrace();
			}
			text.setForeground(Color.black);
			//aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, defaultc);
		}
	}
}


