package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;

/**
 * [X] Version 1: Points 
 * [ ] Version 2: Rotate Points 
 * [ ] Version 3: Scale/Zoom 
 * [ ] Version 4: Pan by focus points 
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
	 * [X] Version 1: Points 
	 * [ ] Version 2: Rotate Points 
	 * [ ] Version 3: Scale/Zoom 
	 * [ ] Version 4: Pan by focus points 
	 */
	private static final long serialVersionUID = 1L;

	private AirfieldInterface airfield;
	private DeclaredRunwayInterface runway;

	private BufferedImage image;

	private int IMAGE_WIDTH, IMAGE_HEIGHT;

	public final static Color GRASS_COLOUR = new Color(95,245,22);

	public final static Font DIMENSION_FONT = new Font("Dialog", Font.PLAIN, 12);
	
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
	/** Ensures the IMAGE_*dimensions* are correct for this round of painting,
	 *  Resets the image to a blank canvas making it easier to use */
	private void rescaleImageSize(){
		this.IMAGE_WIDTH = this.getWidth();
		this.IMAGE_HEIGHT = this.getHeight();
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	}

	protected static double pixelsToMeters(int yourPixels, double meters, int pixels){
		return yourPixels *  meters/pixels;
	}

	protected static int metersToPixels(double yourMeters, double meters, int pixels){
		return (int) (yourMeters * pixels/meters);
	}

	/*@Deprecated
	 * protected static int generalScale (double howMuchWantToFit, int inHowMuch, double whatYouAreScaling){
		BigDecimal value = new BigDecimal( whatYouAreScaling * inHowMuch/howMuchWantToFit);
		return value.intValue();
	}*/

	//----[ M to Pix ]----------------------------------------------------------------------------------------------------------
	public int Xm_to_pixels(double xm){
		int xPix = metersToPixels(xm, this.runwayWidth(), IMAGE_WIDTH);
		return xPix;
	}
	public int Ym_to_pixels(double ym){
		double largestHeight = largestHeight();
		int yPix = metersToPixels(ym, largestHeight, IMAGE_HEIGHT);
		return yPix;
	} 

	//----[ Pix to M ]----------------------------------------------------------------------------------------------------------

	public double Xpix_to_m(int xp){
		double xm = pixelsToMeters(xp, this.runwayWidth(), IMAGE_WIDTH);
		return xm;

	}
	public double Ypix_to_m(int yp){
		double largestHeight = largestHeight();
		double ym = pixelsToMeters(yp, largestHeight, IMAGE_WIDTH);
		return ym;
	}

	//===========================================================================================================================


	//======[ Rotation Methods ]================================================================================================

	private Point getPivot(){
		double middleX = getAirfield().getTotalWidth()/2;
		double middleY = largestHeight()/2;
		return new Point(middleX,middleY);
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

	protected void drawString_inM(Graphics2D g, String text, Point topLeft){
		Graphics2D g2 = (Graphics2D) g.create();
		
		g2.setFont(DIMENSION_FONT);
		Point midLeft = topLeft.offsetYByPixels(3*g2.getFontMetrics().getHeight()/4);
		
		g2.drawString(text, midLeft.x_pix(), midLeft.y_pix());
	}

	//===========================================================================================================================



	//======[ Misc/Distances ]=====================================================================================================

	protected abstract double largestHeight();

	protected double runwayWidth(){
		double defTORA;
		if(getRunway().isSmallEnd()){
			defTORA = getAirfield().getDefaultSmallAngledRunway().getTODA();
		}else{
			defTORA = getAirfield().getDefaultLargeAngledRunway().getTODA();
		}
		return getAirfield().getStripEnd()+defTORA+getAirfield().getStripEnd();
	}
	
	protected int s(){ 
		if(getRunway().isSmallEnd()) return 1;
		return -1;
	}


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



		public Point offsetXByPixels(int xPix){
			return new Point(x_m() + Xpix_to_m(xPix),y_m());
		}
		public Point offsetYByPixels(int yPix){
			return new Point(x_m(),y_m()+Ypix_to_m(yPix));
		}

		public Point offsetXByM(double xm){
			return new Point(x_m() + xm, y_m());
		}
		public Point offsetYByM(double ym){
			return new Point(x_m(), y_m()+ym);
		}

		public Point add(Point p){
			return new Point(x_m()+p.x_m(), y_m()+p.y_m());
		}
		public Point minus(Point p){
			return new Point(x_m()-p.x_m(), y_m()-p.y_m());
		}

		private double xRelativeToPivot(){
			//TODO complete later
			return 0d;
		}
		private double yRelativeToPivot(){
			//TODO complete later
			return 0d;
		}
	}

}
