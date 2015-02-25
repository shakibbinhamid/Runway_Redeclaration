package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

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
	
	public void updateLabelText(String airport){
		airportLabel.setText(airport);
	}
	
	public void makeLog(String s){
		logPanel.addLog(s);
	}
	
	public void notify(String s){
		logPanel.addLog(s);
	}
	
	public void notify(String s, String c){
		switch(c){
		case "file":
		{
			System.out.println("File colour...");
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


