package view.helpPanel;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import io.FileSystem;

public class AboutPanel extends JFrame{

	private static final String aboutDir = "./about.txt";
	private static final String licenseDir = "./ccl.txt";
	
	private String aboutText, licenseText;
	private JLabel html, about;
	
	public static void main(String[] args) {
		new AboutPanel();

	}
	
	public AboutPanel(){
		FileSystem fs = new FileSystem();
		aboutText = fs.getTextFromFile(new File(aboutDir));
		licenseText = fs.getTextFromFile(new File(licenseDir));
		
		html = new JLabel();
		html.setText(licenseText);
		
		html.setAlignmentX(CENTER_ALIGNMENT);
		html.setAlignmentY(CENTER_ALIGNMENT);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(1,1));
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.add(html);
		this.add(panel);
		
		this.setTitle("About - " + aboutText);
		this.setSize(500, 200);
		this.setVisible(true);
		
	}

}
