package view;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

class SelfCheckingField extends JTextField{
	
	private String regex;
	private DocumentListener listener;
	
	public SelfCheckingField(String re){
		regex = re;
		this.getDocument().addDocumentListener(new MyDocumentListener());
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
				if (doc.getText(0, doc.getLength()).matches(regex))
					setBackground(Color.WHITE);
				else
					setBackground(Color.RED);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
        }
    }
}