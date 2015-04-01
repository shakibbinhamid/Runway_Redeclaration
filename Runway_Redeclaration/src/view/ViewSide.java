package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;

/**
 * 
 * @author Stefan
 * @Editor Stefan Shakib
 */
public class ViewSide extends JPanel{
	private static final long serialVersionUID = 1L;

	private AirfieldInterface airfield;
	private DeclaredRunwayInterface runway;
	
	private BufferedImage image;
	
	
	private int IMAGE_WIDTH, IMAGE_HEIGHT;
	
	
	
	public ViewSide(AirfieldInterface airfield, DeclaredRunwayInterface runway){
		setAirfield(airfield);
		setRunway(runway);
		
		this.IMAGE_WIDTH = this.IMAGE_HEIGHT = 0;
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		
	}
	
	public AirfieldInterface getAirfield(){ return this.airfield; }
	public void setAirfield(AirfieldInterface newAirfield){
		this.airfield = newAirfield;
		
		//TODO include more
	}
	
	public DeclaredRunwayInterface getRunway(){ return this.runway; }
	public void setRunway(DeclaredRunwayInterface newRunway){
		this.runway = newRunway;
		
		
		//TODO inlcude more
	}
	
	private void rescaleImageSize(){
		this.IMAGE_WIDTH = this.getWidth();
		this.IMAGE_HEIGHT = this.getHeight();
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		rescaleImageSize();
		
		g.drawImage(image, 0, 0, null);
	}
	
	
	

}
