package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import coreInterfaces.AirfieldInterface;
import coreInterfaces.DeclaredRunwayInterface;

/**
 * [X] Version 1: Points 
 * [/] Version 2: Rotate Points 
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
	 * [/] Version 2: Rotate Points 
	 * [ ] Version 3: Scale/Zoom 
	 * [ ] Version 4: Pan by focus points 
	 */
	private static final long serialVersionUID = 15L;

	private AirfieldInterface airfield;
	private DeclaredRunwayInterface runway;

	private BufferedImage image;

	private int IMAGE_WIDTH, IMAGE_HEIGHT;

	public final static Color GRASS_COLOUR = new Color(95,245,22);
	public final static Color MAROON_COLOUR = new Color(136,0,21);
	public final static Color DIMENSION_COLOR = Color.BLACK;
	public final static Color VERY_VERY_transparentRed = new Color(255, 0, 0, 50);
	public final static Color ALS_SHADE_COLOR = VERY_VERY_transparentRed;
	public final static Color GLASS_COLOR = new Color(255,255,255,0);


	public final static Font DIMENSION_FONT = new Font("Dialog", Font.PLAIN, 12);

	public AbstractView (AirfieldInterface airfield, DeclaredRunwayInterface runway){
		setAirfield(airfield);
		setRunway(runway);

		this.IMAGE_WIDTH = this.IMAGE_HEIGHT = 10;
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		
		this.transformingAngle = (short) 0;
	}

	//======[ Core Objects ]=====================================================================================================
	public AirfieldInterface getAirfield(){ return this.airfield; }
	public void setAirfield(AirfieldInterface newAirfield){
		this.airfield = newAirfield;

	}

	public DeclaredRunwayInterface getRunway(){ return this.runway; }
	public void setRunway(DeclaredRunwayInterface newRunway){
		this.runway = newRunway;

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

	//----[ M to Pix ]----------------------------------------------------------------------------------------------------------
	public int Xm_to_pixels(double xm){
		int xPix = metersToPixels(xm, this.runwayWidth(), IMAGE_WIDTH);
		return xPix;
	}
	public int Ym_to_pixels(double ym){
		double largestHeight = largestHeight();
		int yPix = metersToPixels(ym, largestHeight, IMAGE_HEIGHT);
		return yPix;//*/
//		return Xm_to_pixels(ym);
	} 

	//----[ Pix to M ]----------------------------------------------------------------------------------------------------------

	public double Xpix_to_m(int xp){
		double xm = pixelsToMeters(xp, this.runwayWidth(), IMAGE_WIDTH);
		return xm;
	}
	public double Ypix_to_m(int yp){
		double ym = pixelsToMeters(yp, largestHeight(), IMAGE_WIDTH);
		return ym;
	}

	//===========================================================================================================================


	//======[ Rotation Methods ]================================================================================================

	protected final Point getPivot(){
		double middleX = this.runwayWidth()/2;
		double middleY = largestHeight()/2;
		return new Point(middleX,middleY);
	}

	/** In degrees */
	private short transformingAngle;
	
	public double getRotationTransformationAngle_Rad(){
		return Math.toRadians(getRotationTransformationAngle_Deg()); 
	}
	public short getRotationTransformationAngle_Deg(){
		return transformingAngle;
	}
	public void setRotationTransformationAngle_Deg(short rotation){
		while(rotation < 0){
			rotation += 180;
		}
		this.transformingAngle = (short) (rotation % 380); 
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

	protected void drawRectangle_inM(Graphics2D g, Point start, double width, double height, Color fill){
		Point[] fourCorners = new Point[4];
		fourCorners[0] = start;
		fourCorners[1] = start.offsetXByM(width);
		fourCorners[2] = start.offsetXByM(width).offsetYByM(height);
		fourCorners[3] = start.offsetYByM(height);

		this.drawPolygon_inM(g, fourCorners, fill);
		
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
		double defTODA;
		if(getRunway().isSmallEnd()){
			defTODA = getAirfield().getDefaultSmallAngledRunway().getTODA();
		}else{
			defTODA = getAirfield().getDefaultLargeAngledRunway().getTODA();
		}
		return getAirfield().getStripEnd()+defTODA+getAirfield().getStripEnd();
	}

	protected int s(){ 
		if(getRunway().isSmallEnd()) return 1;
		return -1;
	}

	/** 
	 * Everything on the abstractView is drawn via points, so they can be translated
	 * 
	 * 
	 * Note: AngleFromNorth is like a queued angle, the point without applying a rotation
	 * 		 Some internal points use the result of  the rotation hence the angle on them 
	 * 		 is 0 (degrees).
	 * Note: 90Degrees is horizontal and normal
	 * 
	 * @author Stefan
	 *
	 */
	class Point{
		/** In m */
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

		/** These are used internally */
		private double core_Xm(){ return this.xm; }
		/** These are used internally */
		private double core_Ym(){ return this.ym; }

		public double x_m(){ return rotateXm(); }
		public int x_pix() { return Xm_to_pixels(this.x_m()); }

		public double y_m(){ return rotateYm(); }
		public int y_pix() { return Ym_to_pixels(this.y_m()); }


		public Point offsetXByPixels(int xPix){
			return new Point(core_Xm() + Xpix_to_m(xPix), core_Ym());
		}
		public Point offsetYByPixels(int yPix){
			return new Point(core_Xm(),core_Ym()+Ypix_to_m(yPix));
		}

		public Point offsetXByM(double xm){
			return new Point(core_Xm() + xm, core_Ym());
		}
		public Point offsetYByM(double ym){
			return new Point(core_Xm(), core_Ym()+ym);
		}


		private double x_M_RelativeToPivot(){
			return core_Xm()-getPivot().core_Xm();
		}
		private double y_M_RelativeToPivot(){
			return  core_Ym()-getPivot().core_Ym();
		}

		private double rotateXm(){
			return rotateMe().core_Xm();
		}
		private double rotateYm(){
			return rotateMe().core_Ym();
		}

		/** Returns the point after applying the rotation,
		 *  as the rotation is applied the angle is now 0 */
		protected Point rotateMe(){
			Point relative = new Point(x_M_RelativeToPivot(),y_M_RelativeToPivot());

			double xbuf = relative.core_Xm()*Math.cos(getRotationTransformationAngle_Rad())
					     -relative.core_Ym()*Math.sin(getRotationTransformationAngle_Rad());

			double ybuf = relative.core_Xm()*Math.sin(getRotationTransformationAngle_Rad())
					     +relative.core_Ym()*Math.cos(getRotationTransformationAngle_Rad());

			Point result =  getPivot().offsetXByM(xbuf).offsetYByM(ybuf);
			
			
			
			System.out.println("Rotate("+getRotationTransformationAngle_Deg()+"): "+core_Xm()+","+core_Ym()+" -> "+result.core_Xm()+","+result.core_Ym());
			if(result.core_Xm() != core_Xm() || result.core_Ym() != core_Ym()){
				System.out.println("^---> Change: "+(result.core_Xm() - core_Xm()) +"   "+(result.core_Ym() - core_Ym()));
			}
			return result;
		}



	}

}
