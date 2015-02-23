package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class LogPanel extends JPanel{
	
	private String label;
	private JLabel airportLabel;
	private Log logPanel;
	
	public LogPanel(String name){
		this.label = name;
		init();
	}
	
	private void init(){
		
		//this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		this.setLayout(new BorderLayout());
		
		airportLabel = new JLabel("Airport");
		airportLabel.setPreferredSize(new Dimension(200,20));
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

	private class Log extends JPanel{
		private JScrollPane scroll;
		private JEditorPane text;
		private Document doc;
		
		private Log(){
			scroll = new JScrollPane();
			text = new JEditorPane();
			doc = text.getDocument();
			
			init();
		}
		
		private void init(){
			this.setLayout(new BorderLayout());
			
			text.setEditable(false);
			
			this.add(scroll, BorderLayout.CENTER);
			scroll.add(text);
		}
		private void addLog(String s){
			try {
			      doc.insertString(doc.getLength(), s, null);
			      scroll.repaint();
			      
			      System.out.println(text.getText());
			} catch(BadLocationException exc) {
			      exc.printStackTrace();
			}
		}
	}
}


