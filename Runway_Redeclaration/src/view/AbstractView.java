package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	protected boolean sameScaleAsX; 

	private int IMAGE_WIDTH, IMAGE_HEIGHT;

	public final static Color GRASS_COLOUR = new Color(95,245,22);
	public final static Color MAROON_COLOUR = new Color(136,0,21);
	public final static Color DIMENSION_COLOR = Color.BLACK;
	public final static Color VERY_VERY_transparentRed = new Color(255, 0, 0, 50);
	public final static Color ALS_SHADE_COLOR = VERY_VERY_transparentRed;
	public final static Color GLASS_COLOR = new Color(255,255,255,0);
	public final static Color RUNWAY_COLOUR = Color.GRAY;

	public static int PIXEL_BUFFER = 40;

	public final static Font DIMENSION_FONT = new Font("Dialog", Font.PLAIN, 12);

	public final static Color IDENTIFIER_COLOR = Color.BLACK;
	public final static String IDENTIFIER_FONT = "verdana";
	public final static int IDENTIFIER_STYLE = Font.BOLD;

	public AbstractView (AirfieldInterface airfield, DeclaredRunwayInterface runway){
		setAirfield(airfield);
		setRunway(runway);

		this.IMAGE_WIDTH = this.IMAGE_HEIGHT = 10;
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

		this.transformingAngle = (short) 0;
		this.sameScaleAsX = false;
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
		if(this.sameScaleAsX) return Xm_to_pixels(ym);

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
		if(this.sameScaleAsX) return Xpix_to_m(yp);

		double ym = pixelsToMeters(yp, largestHeight(), IMAGE_WIDTH);
		return ym;
	}

	/** total height of arrow = */
	protected void drawArrowAround(Graphics g, double pointX, double pointY, double width, boolean pointLeft, Color fill, Color outline){
		Graphics2D g2 = (Graphics2D) g.create();
		double radius = 3*width/8; 
		double length = width;


		radius = Ypix_to_m(Xm_to_pixels(radius));

		int m = 1;
		if(pointLeft) m = -1;

		double midX = pointX-m*length/2;
		double backX = pointX-m*length;
		double eithBack = pointX-m*7*length/8;

		//There is an issue with scaling the y axis

		double thirdG = pointY+radius/3;		double negthirdG = pointY-radius/3;
		double thirdG2 = pointY+2*radius/3;		double negthirdG2 = pointY-2*radius/3;
		double halfG = pointY+radius; 			double neghalfG = pointY-radius;

		double[] xes =  {pointX, midX,  midX,   backX,   eithBack, backX,      midX,      midX};
		double[] yses = {pointY, halfG, thirdG, thirdG2, pointY,   negthirdG2, negthirdG, neghalfG};

		Point[] points = new Point[xes.length];
		for(int i = 0; i < xes.length; i++){
			points[i] = new Point(xes[i],yses[i]);
		}

		g2.setColor(outline);
		g2.setStroke(new BasicStroke(0.35f));
		this.drawPolygon_inM(g2, points, fill);
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
	public final void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g.create();
		super.paint(g2);
		rescaleImageSize();

		drawImage(getImage().createGraphics());


		/* @End */g2.drawImage(getImage(), 0, 0, null);
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

		Point midLeft = topLeft.offsetYByPixels(3*g2.getFontMetrics().getHeight()/4);

		g2.drawString(text, midLeft.x_pix(), midLeft.y_pix());
	}
	//----[ Specific Drawings ]-----------------------------------------------------------------------------
	protected void drawIdentifiers(Graphics2D g, double yHeight, double leftTextPos, double rightTextPos) {
		Graphics2D g2 = (Graphics2D) g.create();

		String leftNum = getAirfield().getSmallAngledRunway().getIdentifier().substring(0,2);
		String leftSide = ""+getAirfield().getSmallAngledRunway().getSideLetter();

		String rightNum = getAirfield().getLargeAngledRunway().getIdentifier().substring(0,2);
		String rightSide = ""+getAirfield().getLargeAngledRunway().getSideLetter();

		int fontSize = 10;
		Font resized = new Font(IDENTIFIER_FONT,IDENTIFIER_STYLE,fontSize);
		g2.setFont(resized);
		g2.setColor(IDENTIFIER_COLOR);

		int fontPixelHeight = g2.getFontMetrics().getHeight();
		int rightPixelWidth = g2.getFontMetrics().stringWidth(rightNum);
		int qtrWdith = rightPixelWidth/4;

		double yPos = yHeight/2;

		Point leftPos = new Point(leftTextPos,yPos);
		Point rightPos = new Point(rightTextPos,yPos).offsetXByPixels(-rightPixelWidth);

		//Draw left identifier with side letter beneath
		drawString_inM(g2, leftNum, leftPos);
		drawString_inM(g2, leftSide, leftPos.offsetYByPixels(fontPixelHeight*2).offsetXByPixels(qtrWdith));

		//Draw right identifier with side letter beneath
		drawString_inM(g2, rightNum, rightPos);
		drawString_inM(g2, rightSide, rightPos.offsetYByPixels(fontPixelHeight*2).offsetXByPixels(qtrWdith));
	}


	/** The line that says how big the image is  */
	protected void drawScale(Graphics2D g2, double xStartOfScale, double yStartOfScale, double metresToScale) {
		int bumpHeight = 30;//pixels

		Point scaleStart = new Point(xStartOfScale,yStartOfScale);
		Point scaleEnd = scaleStart.offsetXByM(metresToScale);
		Point scaleMid = scaleStart.offsetXByM(metresToScale/2);

		g2.setColor(Color.BLACK);

		//skinny mid vert
		this.drawLine_inM(g2, scaleMid, scaleMid.offsetYByPixels(bumpHeight/2));

		g2.setStroke(new BasicStroke(2f));

		//fat horiz
		this.drawLine_inM(g2, scaleStart, scaleEnd);

		//fat verts
		this.drawLine_inM(g2, scaleStart, scaleStart.offsetYByPixels(bumpHeight));
		this.drawLine_inM(g2, scaleEnd, scaleEnd.offsetYByPixels(bumpHeight));

		//text labels
		int fontHeightPix = g2.getFontMetrics().getHeight();
		int disWdith = g2.getFontMetrics().stringWidth(""+(int) metresToScale+"m");
		int zeroWidth = g2.getFontMetrics().stringWidth("0m");

		Point bellowStartBump = scaleStart.offsetYByPixels(fontHeightPix*2+bumpHeight).offsetXByPixels(-zeroWidth/2);
		Point bellowEndBump = scaleEnd.offsetYByPixels(fontHeightPix*2+bumpHeight).offsetXByPixels(-disWdith/2);

		this.drawString_inM(g2, "0m", bellowStartBump);
		this.drawString_inM(g2, ""+(int)metresToScale+"m", bellowEndBump);
	}

	protected void drawDistance(Graphics2D g, String disName, double length, double begin, int heightLevel, double basicHeight){
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(DIMENSION_COLOR);

		FontMetrics fontMetrics = g2.getFontMetrics();
		int titleLen = fontMetrics.stringWidth(disName);

		double startX;
		if(getRunway().isSmallEnd()){
			startX = leftOfRunaway() + begin;
		}else{
			startX = rightOfRunway() - begin; 
		}

		double endX = startX+s()*length;
		int pixelOffset = heightLevel*PIXEL_BUFFER;

		Point start = new Point(startX,basicHeight).offsetYByPixels(pixelOffset);
		Point end = new Point(endX,basicHeight).offsetYByPixels(pixelOffset);

		//These points make the gap for the text
		Point midStart = start.offsetXByM(s()*length/2).offsetXByPixels(s()*-3*titleLen/4);
		Point midEnd   = start.offsetXByM(s()*length/2).offsetXByPixels(s()* 3*titleLen/4);

		//Horiz
		this.drawLine_inM(g2, start, midStart);
		this.drawLine_inM(g2, end, midEnd);

		//Verticals
		this.drawLine_inM(g2, start.offsetYByPixels(0), start.offsetYByPixels(-pixelOffset));
		this.drawLine_inM(g2, end.offsetYByPixels(0), end.offsetYByPixels(-pixelOffset));


		//Placing the text on the left of the two mid points
		Point pWithSmallerX;
		if(midStart.x_m()<midEnd.x_m()){
			pWithSmallerX = midStart;
		}else{
			pWithSmallerX = midEnd;
		}
		this.drawString_inM(g2, disName, pWithSmallerX.offsetXByPixels(titleLen/4));
	}

	//===========================================================================================================================



	//======[ Misc/Distances ]=====================================================================================================

	public void save(String fullpath, String ext) throws IOException{
		ImageIO.write(image, ext, new File(fullpath));
	}

	protected abstract double largestHeight();

	protected abstract double vertToRunway();

	protected abstract double leftOfRunaway();

	protected abstract double rightOfRunway();

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


			if(getRotationTransformationAngle_Deg()!=0){
				System.out.println("Rotate("+getRotationTransformationAngle_Deg()+"): "+core_Xm()+","+core_Ym()+" -> "+result.core_Xm()+","+result.core_Ym());
				if(result.core_Xm() != core_Xm() || result.core_Ym() != core_Ym()){
					System.out.println("^---> Change: "+(result.core_Xm() - core_Xm()) +"   "+(result.core_Ym() - core_Ym()));
				}
			}
			return result;
		}



	}

}
