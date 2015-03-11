package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
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
	private JPanel logs;
	private Log fileLogPanel;
	private Log calcLogPanel;

	private TopFrame parent;
	private File devLog;

	private static final Color FILE = new Color(128, 0, 0);
	private static final Color CALC = new Color(75, 0, 130);
	private static final Color DEFAULT = Color.WHITE;
	private static final Color ERROR = Color.RED;

	public LogPanel(String name, TopFrame top){
		this.label = name;
		this.parent = top;
		this.devLog = this.makeFile();
		init();
	}

	private void init(){

		//this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

		this.setLayout(new BorderLayout());

		airportLabel = new JLabel("Airport");
		airportLabel.setFont(new Font("verdana", Font.ITALIC, 15));
		airportLabel.setHorizontalAlignment(SwingConstants.CENTER);
		airportLabel.setPreferredSize(new Dimension(300,20));
		this.add(airportLabel, BorderLayout.NORTH);

		if(label != null)
			updateLabelText(label);
		
		logs = new JPanel();
		logs.setLayout(new BorderLayout());
		this.add(logs, BorderLayout.CENTER);

		fileLogPanel = new Log();
		logs.add(fileLogPanel, BorderLayout.NORTH);
		
		calcLogPanel = new Log();
		logs.add(calcLogPanel, BorderLayout.CENTER);
	}

	/**
	 * Update the airport label to the specified name
	 * @param airportName the name of an airport
	 */
	public void updateLabelText(String airportName){
		airportLabel.setText(airportName);
	}

	public void notify(String s, String c){
		if(this.parent.isUserHelpingDeveloper()){ 
			this.saveLogToDeveloper(s);
		}
		switch(c){
		case "file":
			notifyFile(s);
			break;
		case "calc":
			notifyCalc(s);
			break;
		default:
			notifyDefault(s);;
		}
	}
	
	private File makeFile(){
		int i = 0;
		File f = new File("./dat/devLog"+i);
		while(f.exists() && !f.isDirectory()){
			f = new File("./dat/devLog"+i);
			i++;
		}
		 try {
			this.devWriter = new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}
	
	BufferedWriter devWriter; 
	private void saveLogToDeveloper(String info){
		try {
		    devWriter.write(info);
		    devWriter.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void finalize(){
		try {
			devWriter.flush();
			devWriter.close();
		} catch (IOException e) {
		}
	}
	
	private void notifyDefault(String s){
		fileLogPanel.addLog(s, DEFAULT);
	}
	
	private void notifyFile(String s){
		fileLogPanel.addLog(s, FILE);
	}
	
	private void notifyCalc(String s){
		calcLogPanel.addLog(s, CALC);
	}
	
	public Component getfileTextPane(){
		return fileLogPanel.text;
	}
	
	public JTextPane getCalcTextPane(){
		return calcLogPanel.text;
	}
	
	public void clearFileTextPane(){
		fileLogPanel.text.setText("");
	}
	
	public void clearCalcTextPane(){
		calcLogPanel.text.setText("");
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
			appendToPane(wordWrap(s, 40), c);
			appendToPane("\n", c);
			text.setEditable(false);
		}

		char[] splitChars = new char[] { ' ', '-', '\t' };

		private String wordWrap(String str, int width){
			String[] words = explode(str, splitChars);

			int curLineLength = 0;
			StringBuilder strBuilder = new StringBuilder();
			for(int i = 0; i < words.length; i += 1){
				String word = words[i];
				if (curLineLength + word.length() > width){
					if (curLineLength > 0){
						strBuilder.append("\n");
						curLineLength = 0;
					}

					while (word.length() > width){
						strBuilder.append(word.substring(0, width - 1));
						word = word.substring(width - 1);

						strBuilder.append("\n");
					}

					word = word.trim();
				}
				strBuilder.append(word);
				curLineLength += word.length();
			}

			return strBuilder.toString();
		}

		private String[] explode(String str, char[] splitChars){
			List<String> parts = new ArrayList<String>();
			int startIndex = 0;
			while (true){
				int index = str.indexOf(new String(splitChars), startIndex);

				if (index == -1){
					parts.add(str.substring(startIndex));
					return parts.toArray(new String[parts.size()]);
				}

				String word = str.substring(startIndex, index - startIndex);
				char nextChar = str.substring(index, 1).toCharArray()[0];
				if (Character.isWhitespace(nextChar)){
					parts.add(word);
					parts.add(new Character(nextChar).toString());
				}
				else{
					parts.add(word + nextChar);
				}

				startIndex = index + 1;
			}
		}
	}
}


