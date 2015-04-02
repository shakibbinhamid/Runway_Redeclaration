package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;

/**
 * [] Version 1: Points 
 * [] Version 2: Rotate Points 
 * [] Version 3: Scale/Zoom 
 * [] Version 4: Pan by focus points 
 * 
 * 
 * We use Points so that they can be translated!
 * Rule of thumb: int -> pixels
 * 				  double -> m 
 * Using Points we can be sure of this!
 * 
 * @author Stefan
 *
 */
public abstract class AbstractView extends JPanel {
	/**
	 * [X] Version 0: Nada Complete
	 * [ ] Version 1: Points 
	 * [ ] Version 2: Rotate Points 
	 * [ ] Version 3: Scale/Zoom 
	 * [ ] Version 4: Pan by focus points 
	 */
	private static final long serialVersionUID = 0L;

	private AirfieldInterface airfield;
	private DeclaredRunwayInterface runway;

	private BufferedImage image;

	private int IMAGE_WIDTH, IMAGE_HEIGHT;

	public AbstractView (AirfieldInterface airfield, DeclaredRunwayInterface runway){
		setAirfield(airfield);
		setRunway(runway);

		this.IMAGE_WIDTH = this.IMAGE_HEIGHT = 10;
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

	}

	//======[ Core Objects ]=====================================================================================================
	public AirfieldInterface getAirfield(){ return this.airfield; }
	public void setAirfield(AirfieldInterface newAirfield){
		this.airfield = newAirfield;

		//TODO !IF NEEDED! add the special values, **shouldn't be needed**
	}

	public DeclaredRunwayInterface getRunway(){ return this.runway; }
	public void setRunway(DeclaredRunwayInterface newRunway){
		this.runway = newRunway;

		//TODO !IF NEEDED! add the special values **shouldn't be needed**
	}
	
	public BufferedImage getImage(){
		return this.image;
	}
	//===========================================================================================================================



	//======[ Scaling ]=====================================================================================================
	private void rescaleImageSize(){
		this.IMAGE_WIDTH = this.getWidth();
		this.IMAGE_HEIGHT = this.getHeight();
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	}

	protected static double pixelsToMeters(int yourPixels, double meters, int pixels){
		return yourPixels * pixels / meters;
	}

	protected static int metersToPixels(double yourMeters, double meters, int pixels){
		return (int) (yourMeters * meters/pixels);
	}

	/*@Deprecated
	 * protected static int generalScale (double howMuchWantToFit, int inHowMuch, double whatYouAreScaling){
		BigDecimal value = new BigDecimal( whatYouAreScaling * inHowMuch/howMuchWantToFit);
		return value.intValue();
	}*/

	//----[ M to Pix ]----------------------------------------------------------------------------------------------------------
	public int Xm_to_pixels(double xm){
		double largestWidth = (int) getAirfield().getTotalWidth();
		int xPix = metersToPixels(xm, largestWidth, IMAGE_WIDTH);

		System.out.println("X: "+xm+"m   to  "+xPix+"pixels");
		return xPix;
	}
	public int Ym_to_pixels(double ym){
		double largestHeight = largestHeight();
		int yPix = metersToPixels(ym, largestHeight, IMAGE_HEIGHT);

		System.out.println("Y: "+ym+"m   to  "+yPix+"pixels");
		return yPix;
	} 

	//----[ Pix to M ]----------------------------------------------------------------------------------------------------------

	public double Xpix_to_m(int xp){
		int largestWidth = (int) getAirfield().getTotalWidth();
		double xm = pixelsToMeters(xp, largestWidth, IMAGE_WIDTH);

		System.out.println("X: "+xp+"pix   to "+xm+"m ");
		return xm;

	}
	public double Ypix_to_m(int yp){
		double largestHeight = largestHeight();
		double ym = pixelsToMeters(yp, largestHeight, IMAGE_WIDTH);

		System.out.println("Y: "+yp+"pix   to "+ym+"m ");
		return ym;
	}

	//===========================================================================================================================


	
	//======[ Drawing the image ]================================================================================================
	@Override
	public void paint(Graphics g){
		super.paint(g);
		rescaleImageSize();
		
		drawImage(getImage().createGraphics());

		/* @End */g.drawImage(getImage(), 0, 0, null);
	}

	protected abstract void drawImage(Graphics2D g);
	
	
	//----[ Generic Drawing ]---------------------------------------------------------------------
		protected void drawLine_inM(Graphics2D g, Point p1, Point p2){
			Graphics2D g2 = (Graphics2D) g.create();

			g2.drawLine(p1.x_pix(), p1.y_pix(), 
					    p2.x_pix(), p2.y_pix());
	}

		protected void drawRectangle_inM(Graphics2D g, Point start, Point size, Color fill){
			Graphics2D g2 = (Graphics2D) g.create();

			g2.drawRect(start.x_pix(), start.y_pix(),
					    size.x_pix(), size.y_pix());
			
			if(fill != null){
				g2.setColor(fill);
				g2.fillRect(start.x_pix(), start.y_pix(), 
						    size.x_pix(), size.y_pix());
			}
		}


		protected void drawPolygon_inM(Graphics g, Point[] points, Color fill){
			Graphics2D g2 = (Graphics2D) g.create();
			int[] xs_p, ys_p;

			xs_p = new int[points.length];
			ys_p = new int[points.length];

			int i = 0;
			for(Point point : points){
				xs_p[i] = point.x_pix();
				ys_p[i] = point.y_pix();
				
				i++;
			}

			g2.drawPolygon(xs_p, ys_p, xs_p.length);
			if(fill != null){
				g2.setColor(fill);
				g2.fillPolygon(xs_p, ys_p, xs_p.length);
			}
		}

	//===========================================================================================================================



	//======[ Misc/Distances ]=====================================================================================================

	protected abstract double largestHeight();


	class Point{
		private double xm, ym;

		public Point(double x, double y){
			this.xm = x;
			this.ym = y;
		}

		public Point(int x, int y, boolean pixelsIntended){
			if(pixelsIntended){
				this.xm = Xpix_to_m(x);
				this.ym = Ypix_to_m(y);

			}else{
				this.xm = x;
				this.ym = y;
			}
		}

		public double x_m(){ return this.xm; }
		public int x_pix() { return Xm_to_pixels(this.xm); }
		
		public double y_m(){ return this.ym; }
		public int y_pix() { return Ym_to_pixels(this.ym); }

	}

}
