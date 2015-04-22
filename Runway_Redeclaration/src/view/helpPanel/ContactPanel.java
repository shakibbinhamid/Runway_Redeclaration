package view.helpPanel;

import java.awt.FlowLayout;
import java.io.File;

import io.FileSystem;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ContactPanel extends JFrame{
	
	private static final String contactDir = "./help/contact.txt";
	private static final String title = "Contact";
	
	private JTextArea contactLabel;

	public static void main(String[] args) {
		new ContactPanel();

	}
	
	public ContactPanel(){
		FileSystem fs = new FileSystem();
		JPanel panel = new JPanel();
		contactLabel = new JTextArea(fs.getTextFromFile(new File(contactDir)));
		contactLabel.setEditable(false);
		panel.setLayout(new FlowLayout());
		panel.add(contactLabel);
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		this.add(panel);
		this.pack();
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
