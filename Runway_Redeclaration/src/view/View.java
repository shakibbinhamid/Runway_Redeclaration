package view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class View extends JLabel{
	
	public View(){

		this.setIcon(new ImageIcon(TopFrame.class.getResource("/Runwaytop.jpg")));
		this.setMaximumSize(new Dimension(300,300));
	}

}