package view;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import CoreInterfaces.AirportInterface;

public class LogPanel extends JPanel{
	
	private AirportInterface airport;
	private JLabel airportLabel;
	private Log logPanel;
	
	public LogPanel(AirportInterface airport){
		this.airport = airport;
		init();
	}
	
	private void init(){
		
		//this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		this.setLayout(new BorderLayout());
		
		airportLabel = new JLabel("Airport");
		this.add(airportLabel, BorderLayout.NORTH);
		
		if(airport != null)
			setLabelText(airport.getName());
		
		logPanel = new Log();
		this.add(logPanel, BorderLayout.CENTER);
	}
	
	public void setLabelText(String airport){
		airportLabel.setText(airport);
	}
	
	public void makeLog(String s){
		logPanel.addLog(s);
	}

	class Log extends JPanel{
		JScrollPane scroll;
		JEditorPane text;
		
		Log(){
			scroll = new JScrollPane();
			text = new JEditorPane();
			
			init();
		}
		
		void init(){
			this.setLayout(new BorderLayout());
			
			text.setEditable(false);
			
			this.add(scroll, BorderLayout.CENTER);
			scroll.add(text);
		}
		void addLog(String s){
			try {
			      Document doc = text.getDocument();
			      doc.insertString(doc.getLength(), s, null);
			} catch(BadLocationException exc) {
			      exc.printStackTrace();
			}
		}
	}
}


