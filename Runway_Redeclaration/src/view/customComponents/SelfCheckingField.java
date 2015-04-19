package view.customComponents;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * This class (extends JTextField) is a self checking text field that goes red if the text contained does not match its regex
 * So, it comes preloaded with a DocumentListener
 * @author Shakib-Bin Hamid
 * @see {@link JTextField}
 * @see {@link DocumentListener}
 */
public class SelfCheckingField extends JTextField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String regex;
	
	private static final Color INPUT_ERROR = new Color(255, 69, 0);
	
	public SelfCheckingField(String re){
		setRegex(re);
		this.getDocument().addDocumentListener(new MyDocumentListener());
	}
	
	/**
	 * Returns the current regex
	 * @return regex
	 */
	public String getRegex(){
		return regex;
	}
	
	/**
	 * Sets the regex to the given one
	 * @param re new regex
	 */
	public void setRegex(String re){
		this.regex = re;
	}
	
	private class MyDocumentListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            updateColor(e, "inserted into");
        }
        public void removeUpdate(DocumentEvent e) {
            updateColor(e, "removed from");
        }
        public void changedUpdate(DocumentEvent e) {}

        public void updateColor(DocumentEvent e, String action) {
            Document doc = (Document)e.getDocument();
            try {
				if (doc.getLength() == 0)
					setBackground(Color.WHITE);
				else if(doc.getText(0, doc.getLength()).trim().matches(regex))
					setBackground(Color.WHITE);
				else
					setBackground(INPUT_ERROR);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
        }
    }
}