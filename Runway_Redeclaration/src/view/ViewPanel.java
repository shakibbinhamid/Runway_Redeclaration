package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;

public class ViewPanel extends JPanel{
	
	private AirfieldInterface field;
	private View view1, view2;

	public ViewPanel(AirfieldInterface field){
		this.field = field;
		view1 = new View();
		view2 = new View();
		init();
	}
	
	private void init(){
		this.setLayout(new GridLayout(2,1));
		
		this.add(view1);
		this.add(view2);
	}
	
	class View extends JPanel{
		
		JLabel viewLabel;
		BufferedImage view;
		
		View(){
			try {
				view = ImageIO.read(new File("./icon/sp-studio-1.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewLabel = new JLabel(new ImageIcon(view));
			init();
		}
		
		void init(){
			this.setLayout(new BorderLayout());
			
			this.add(viewLabel, BorderLayout.CENTER);
		}

	}
}
