package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

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
	
	
	public static int EXTRA_VERTICAL_SPACE = 50; /*in meters */
	
	
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
	
	//======[ Scaling ]=====================================================================================================
	private void rescaleImageSize(){
		this.IMAGE_WIDTH = this.getWidth();
		this.IMAGE_HEIGHT = this.getHeight();
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	public int Xm_to_pixels(double xm){
		int largestWidth = (int) getAirfield().getTotalWidth();
		return generalScale(largestWidth, IMAGE_WIDTH, xm);
	}
	public int Ym_to_pixels(double ym){
		int largestHeight = (int) getAirfield().getTotalHeight();
		return generalScale(largestHeight, IMAGE_HEIGHT, ym);
	} 
	
	private static int generalScale (double howMuchWantToFit, int inHowMuch, double whatYouAreScaling){
		BigDecimal value = new BigDecimal( whatYouAreScaling * inHowMuch/howMuchWantToFit);
		return value.intValue();
	}
	//===========================================================================================================================

	
	
	//======[ Drawing ]=====================================================================================================
	@Override
	public void paint(Graphics g){
		super.paint(g);
		rescaleImageSize();
		
		drawImage((Graphics2D) g.create());
		
		/* @End */g.drawImage(image, 0, 0, null);
	}
	
	private void drawImage(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}
	//----[ Generic Drawing ]---------------------------------------------------------------------
	private void drawLine_inM(Graphics2D g, double x1m, double y1m, double x2m, double y2m){
		Graphics2D g2 = (Graphics2D) g.create();
		int x1p,y1p,x2p,y2p;
		
		x1p = Xm_to_pixels(x1m);
		y1p = Ym_to_pixels(y1m);
		
		x2p = Xm_to_pixels(x2m);
		y2p = Ym_to_pixels(y2m);
		
		g2.drawLine(x1p, y1p, x2p, y2p);
	}
	
	private void drawRectangle_inM(Graphics2D g, double xm, double ym, double width_m, double height_m, Color fill){
		Graphics2D g2 = (Graphics2D) g.create();
		int xp,yp,width_p,height_p;
		
		xp = Xm_to_pixels(xm);
		yp = Ym_to_pixels(ym);
		
		width_p = Xm_to_pixels(width_m);
		height_p = Ym_to_pixels(height_m);
		
		g2.drawRect(xp, yp, width_p, height_p);
		if(fill != null){
			g2.setColor(fill);
			g2.fillRect(xp, yp, width_p, height_p);
		}
	}
	
	
	private void drawPolygon_inM(Graphics g, double[] xs_m, double[] ys_m, Color fill){
		Graphics2D g2 = (Graphics2D) g.create();
		int[] xs_p, ys_p;
		
		xs_p = new int[xs_m.length];
		for(int xi = 0; xi < xs_m.length; xi++){
			xs_p[xi] = Xm_to_pixels(xs_m[xi]);
		}
		
		ys_p = new int[ys_m.length];
		for(int yi = 0; yi < ys_m.length; yi++){
			ys_p[yi] = Ym_to_pixels(ys_m[yi]);
		}
		
		g2.drawPolygon(xs_p, ys_p, xs_p.length);
		if(fill != null){
			g2.setColor(fill);
			g2.fillPolygon(xs_p, ys_p, xs_p.length);
		}
	}
	
	//----[ Specific Distance ]-------------------------------------------------------------------
	
	
	
	


}
