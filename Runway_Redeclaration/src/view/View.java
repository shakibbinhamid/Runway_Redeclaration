package view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class View extends JLabel{
	
	private BufferedImage view;
	
	public View(){
		try {
			view = ImageIO.read(new File("./icon/Runwaytop.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setIcon(new ImageIcon(view));
		this.setMaximumSize(new Dimension(300,300));
	}

}