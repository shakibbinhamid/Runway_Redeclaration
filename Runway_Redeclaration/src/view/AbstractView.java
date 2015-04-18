package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import coreInterfaces.AirfieldInterface;
import coreInterfaces.DeclaredRunwayInterface;

/**
 * [X] Version 1: Points 
 * [X] Version 2: Rotate Points 
 * [X] Version 3: Scale/Zoom 
 * [X] Version 4: Pan by focus points 
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
public abstract class AbstractView extends JPanel implements ChangeListener {
	/**
	 * [X] Version 0: Nada Complete
	 * [X] Version 1: Points 
	 * [X] Version 2: Rotate Points 
	 * [X] Version 3: Scale/Zoom 
	 * [X] Version 4: Pan by focus points 
	 */
	private static final long serialVersionUID = 35L;

	private AirfieldInterface airfield;
	private DeclaredRunwayInterface runway;

	private BufferedImage image;

	public final static Color GRASS_COLOUR = new Color(95,245,22);
	public final static Color MAROON_COLOUR = new Color(136,0,21);
	public final static Color DIMENSION_COLOR = Color.BLACK;
	public final static Color VERY_VERY_transparentRed = new Color(255, 0, 0, 50);
	public final static Color ALS_SHADE_COLOR = VERY_VERY_transparentRed;
	public final static Color GLASS_COLOR = new Color(255,255,255,0);
	public final static Color RUNWAY_COLOUR = Color.GRAY;
	public static final Color CLEARWAY_COLOR = Color.YELLOW;
	public static final Color STOPWAY_COLOR = Color.RED;
	private static final Color PLANE_COLOUR = new Color(175,175,175);

	//For Distances
	public int PIXEL_BUFFER = 15;
	public double DIMENSION_GAP = 0;
	public double INITIAL_BUFFER = 0;
	public final static Font DISTANCE_FONT = new Font("Dialog", Font.PLAIN, 12);

	//For identifiers
	public Color IDENTIFIER_COLOR = Color.BLACK;
	public final static String IDENTIFIER_FONT = "verdana";
	public final static int IDENTIFIER_STYLE = Font.BOLD;

	//	private JScrollPane scroll;
	private ViewPort_withOverlay runwayView;
	private JLabel label;

	private double zoomingFactor;
	private static final double ZOOM_SCALE_INCREMENT = 0.02;

	protected boolean allowRotation;

	private int IMAGE_WIDTH, IMAGE_HEIGHT;
	private boolean sameScaleAsX;
	
	private int toolSelected;
	
	private ToolBar toolbar;
	
	private static BufferedImage iselect, izoomIn, izoomOut, irotateClockwise, irotateAnti, izoomRefresh;
	
	private JToggleButton select, zoomIn, zoomOut;
	
	private JButton zoomRefresh, rotateClockwise, rotateAnti;
	
	public static final int SELECTION = 0;
	public static final int ZOOM_IN = 1;
	public static final int ZOOM_OUT = 2;
	public static final int ROTATE_CLOCKWISE = 3;
	public static final int ROTATE_ANTI_CLOCKWISE = 4;


	public AbstractView (AirfieldInterface airfield, DeclaredRunwayInterface runway, 
			String title, boolean roatationEnabled, boolean identicalScaling)
	{
		setAirfield(airfield);
		setRunway(runway);

		this.IMAGE_WIDTH = this.IMAGE_HEIGHT = 10;
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

		this.transformingAngle = (short) 0;
		this.allowRotation = roatationEnabled;
		this.sameScaleAsX = identicalScaling;

		this.setBorder(BorderFactory.createTitledBorder(title));
		init();

		this.zoomingFactor = 1;

	}

	private void init(){

		this.setLayout(new BorderLayout());

		izoomIn = readImage("/zoomin.gif");
		izoomOut = readImage("/zoomout.gif");
		izoomRefresh = readImage("/refresh.gif");
		irotateClockwise = readImage("/clock.png");
		irotateAnti = readImage("/anticlock.png");
		iselect = readImage("/pointer.png");
		
		select = new JToggleButton(new ImageIcon(iselect));
		zoomIn = new JToggleButton(new ImageIcon(izoomIn));
		zoomOut = new JToggleButton(new ImageIcon(izoomOut));
		rotateClockwise = new JButton(new ImageIcon(irotateClockwise));
		rotateAnti = new JButton(new ImageIcon(irotateAnti));

		toolbar = new ToolBar(JToolBar.VERTICAL);
		toolbar.setFloatable(true);
		toolbar.setRollover(true);
		
		toolbar.addButtonGroup("ZOOMING_TOOLS", new ButtonGroup(), Arrays.asList(new JToggleButton[]{select, zoomIn, zoomOut}));
		
		toolbar.addSeparator();
		
		if(allowRotation){
			toolbar.add(rotateClockwise);
			toolbar.add(rotateAnti);
		}
		
		zoomRefresh = new JButton(new ImageIcon(izoomRefresh));
		
		toolbar.add(zoomRefresh);
		
		this.add(toolbar, BorderLayout.EAST);
		
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTool(SELECTION);
			}
		});
		zoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTool(ZOOM_IN);
			}
		});
		zoomRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetAll();
			}
		});
		zoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTool(ZOOM_OUT);
			}
		});
		rotateClockwise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				incrementRotation(true);
			}
		});
		rotateAnti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				incrementRotation(false);
			}
		});
		
		zoomIn.addItemListener(new ChangeCursorListener(izoomIn));
		zoomOut.addItemListener(new ChangeCursorListener(izoomOut));

		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);

		runwayView = new ViewPort_withOverlay();
		runwayView.setView(label);

		//Allows panning
		MouseAdapter ma = new HandScrollListener();
		runwayView.addMouseMotionListener(ma);
		runwayView.addMouseListener(ma);

		//Allows MouseWheel zooming
		ZoomController zC = new ZoomController();
		this.runwayView.addMouseWheelListener(zC);
		
		runwayView.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				switch(getSelectedTool()){
				case SELECTION:
					break;
				case ZOOM_IN:
					zoomIn();
					break;
				case ZOOM_OUT:
					zoomOut();
					break;
				}
			}
		});

		this.add(runwayView,BorderLayout.CENTER);
	}
	
	class ChangeCursorListener implements ItemListener{
		
		private BufferedImage image;

		public ChangeCursorListener(BufferedImage image) {
			super();
			this.image = image;
		}

		@Override
		public void itemStateChanged(ItemEvent itemEvent) {
			int state = itemEvent.getStateChange();
			if (state == ItemEvent.SELECTED) {
				changeViewCursor(image);
			} else {
				runwayView.setCursor(Cursor.getDefaultCursor());
			}
		}
	}
	
	private void changeViewCursor(BufferedImage image){
		Cursor cursor = createTransparentCursor(20,image);
		runwayView.setCursor(cursor);
	}
	
	public static synchronized Cursor createTransparentCursor(final int size, BufferedImage image ) {
		final java.awt.Point hotSpot = new java.awt.Point( size / 2, size / 2 );
		return Toolkit.getDefaultToolkit().createCustomCursor( image, hotSpot, "Trans" );
	}
	
	private static BufferedImage readImage(String path){
		try {
			return ImageIO.read(AbstractView.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int setTool(int tool){
		this.toolSelected = tool;
		return this.toolSelected;
	}
	
	public int getSelectedTool(){
		return this.toolSelected;
	}

	protected JLabel getLabel(){
		return this.label;
	}

	@Override
	public void stateChanged(ChangeEvent e) {  
		repaint();
	} 

	private void resetAll() {
		resetRotation();
		resetZoom();
		rescaleImageSize();
		resetLocation();
		resetLocation();
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

	//----[ Image ]------------------------------------------------------------------------------------------
	public BufferedImage getImage(){
		return this.image;
	}
	protected int getIMAGE_WIDTH() { return this.IMAGE_WIDTH;  }
	protected int getIMAGE_HEIGHT(){ return this.IMAGE_HEIGHT; }

	protected int getZOOMED_IMAGE_WIDTH() { return (int) (this.runwayView.getWidth()*zoomingFactor);  }
	protected int getZOOMED_IMAGE_HEIGHT(){ return (int) (this.runwayView.getHeight()*zoomingFactor); }

	protected int getSUB_IMAGE_WIDTH() { return this.runwayView.getWidth();  }
	protected int getSUB_IMAGE_HEIGHT(){ return this.runwayView.getHeight(); }

	//===========================================================================================================================



	//======[ Scaling ]=====================================================================================================
	/** Ensures the IMAGE_*dimensions* are correct for this round of painting,
	 *  Resets the image to a blank canvas making it easier to use */
	private void rescaleImageSize(){
		this.IMAGE_WIDTH = getZOOMED_IMAGE_WIDTH();
		this.IMAGE_HEIGHT = getZOOMED_IMAGE_HEIGHT();

		/* Width is always greater than length so when we rotate 90deg,
		 *  the height of the image must be at least that of the default width
		 *  as the height must show the previous full width
		 */
		if(this.sameScaleAsX){
			this.image = new BufferedImage(IMAGE_WIDTH*2, IMAGE_WIDTH*2, BufferedImage.TYPE_3BYTE_BGR);
		}else{
			this.image = new BufferedImage(IMAGE_WIDTH*2, IMAGE_HEIGHT*2, BufferedImage.TYPE_3BYTE_BGR);
		}
	}

	protected static double pixelsToMeters(int yourPixels, double meters, int pixels){
		return yourPixels *  meters/pixels;
	}

	protected static int metersToPixels(double yourMeters, double meters, int pixels){
		return (int) (yourMeters * pixels/meters);
	}

	//----[ M to Pix ]----------------------------------------------------------------------------------------------------------
	public int Xm_to_pixels(double xm){
		int xPix = metersToPixels(xm, runwayWidth(), getZOOMED_IMAGE_WIDTH());
		return xPix;
	}
	public int Ym_to_pixels(double ym){
		if(this.sameScaleAsX) return Xm_to_pixels(ym);

		int yPix = metersToPixels(ym, largestHeight(), getZOOMED_IMAGE_HEIGHT());
		return yPix;
	} 

	//----[ Pix to M ]----------------------------------------------------------------------------------------------------------

	public double Xpix_to_m(int xp){
		double xm = pixelsToMeters(xp, runwayWidth(), getZOOMED_IMAGE_WIDTH());
		return xm;
	}
	public double Ypix_to_m(int yp){
		if(this.sameScaleAsX) return Xpix_to_m(yp);

		double ym = pixelsToMeters(yp, largestHeight(), getZOOMED_IMAGE_HEIGHT());
		return ym;
	}
	//===========================================================================================================================


	//======[ Rotation Methods ]================================================================================================
	protected final Point getPivot(){
		double middleX = runwayWidth()/2;
		double middleY = largestHeight()/2;
		return new Point(middleX,middleY);
	}

	/** In degrees */
	private short transformingAngle = 0;

	public double getRotationTransformationAngle_Rad(){
		return Math.toRadians(getRotationTransformationAngle_Deg()); 
	}
	public short getRotationTransformationAngle_Deg(){
		return transformingAngle;
	}
	public void setRotationTransformationAngle_Deg(short rotation){
		while(rotation < 0){
			rotation += 360;
		}
		this.transformingAngle = (short) (rotation % 360); 
		repaint();
		revalidate();
	}

	protected static final boolean CLOCKWISE = true;
	protected static final boolean ANTI_CLOCKWISE = false;
	protected static final short ROTATION_INCREMENT = 10;

	protected void incrementRotation(boolean clockwise){
		int m = clockwise? 1 : -1;

		setRotationTransformationAngle_Deg((short) (getRotationTransformationAngle_Deg()+m*ROTATION_INCREMENT));
	}
	public void resetRotation(){
		setRotationTransformationAngle_Deg((short)0);
	}
	//===========================================================================================================================

	//======[ Zooming & Panning Methods ]================================================================================================

	public double getZoomingFactor(){
		return this.zoomingFactor;
	}
	public void setZoomTo(double z){
		if(z<0.5) return;
		this.zoomingFactor = z;
		repaintMe();
	}
	public void increaseZoomBy(double littleZoom){
		int previousWidth = getImage().getWidth();
		int previousHeight = getImage().getHeight();
		
		setZoomTo(this.zoomingFactor+littleZoom);
		
		moveViewportWithZoom(previousWidth,previousHeight);
	}
	private void moveViewportWithZoom(int previousWidth, int previousHeight) {
		java.awt.Point centrePoint = runwayView.getViewPosition();
		int deltaX = getImage().getWidth()-previousWidth;
		int deltaY = getImage().getHeight()-previousHeight;

		centrePoint.translate(deltaX/2, deltaY/2);
		getLabel().scrollRectToVisible(new Rectangle(centrePoint, runwayView.getSize()));
	}

	public void zoomIn(){
		increaseZoomBy(+ZOOM_SCALE_INCREMENT);
	}
	public void zoomOut(){
		increaseZoomBy(-ZOOM_SCALE_INCREMENT);
	}
	public void resetZoom(){
		setZoomTo(1);
	}


	//----[ Listener Controllers ]---------------------------------------------------------------------
	protected void repaintMe(){
		repaint();
		revalidate();
	}

	class ZoomController implements MouseWheelListener{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int whellRotations = -e.getWheelRotation();
			increaseZoomBy(whellRotations*0.1);
		}
	}

	//===========================================================================================================================

	//====[ Panning ]=================================================================================================================
	/** Panning */
	private  class HandScrollListener extends MouseAdapter {

		private final java.awt.Point pp = new java.awt.Point();

		@Override 
		public void mouseDragged(MouseEvent e) {
			java.awt.Point cp = e.getPoint();
			java.awt.Point vp = runwayView.getViewPosition();

			vp.translate(pp.x-cp.x, pp.y-cp.y);


			getLabel().scrollRectToVisible(new Rectangle(vp, runwayView.getSize()));

			pp.setLocation(cp);
			repaint();
		}
		@Override 
		public void mousePressed(MouseEvent e) {
			pp.setLocation(e.getPoint());
		}
	}

	public void resetLocation(){
		int centreX = getImage().getWidth()/2-runwayView.getWidth()/2;
		int centreY;
		if(sameScaleAsX){
			centreY = getImage().getHeight()/2-runwayView.getHeight();
		}else{
			centreY = getImage().getHeight()/2-runwayView.getHeight()/2;
		}
		java.awt.Point centrePoint = new java.awt.Point(centreX, centreY);


		getLabel().scrollRectToVisible(new Rectangle(centrePoint, runwayView.getSize()));
		repaintMe();
	}
	//===========================================================================================================================


	//======[ Drawing the image ]================================================================================================
	boolean firstPaint = true;

	@Override
	public final void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g.create();
		
		rescaleImageSize();
		drawImage(getImage().createGraphics());
		label.setIcon(new ImageIcon(getImage()));
		super.paintComponent(g2);

		if(firstPaint){
			this.firstPaint = false;
			resetAll();
			repaint();
		}
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
		Color previous = g2.getColor();
		if(fill != null){
			g2.setColor(fill);
			g2.fillPolygon(xs_p, ys_p, xs_p.length);
		}
		g2.setColor(previous);
		g2.drawPolygon(xs_p, ys_p, xs_p.length);

	}

	protected void drawString_inM(Graphics2D g, String text, Point topLeft){
		Graphics2D g2 = (Graphics2D) g.create();

		Point midLeft = topLeft.offsetYByPixels(0);//5*g2.getFontMetrics().getHeight()/16);
		Point midmid = midLeft.offsetXByPixels(g2.getFontMetrics().stringWidth(text)/2);
		g2.translate(midmid.x_pix(), midmid.y_pix());
		g2.rotate(getRotationTransformationAngle_Rad());

		int extra = 1;
		if(getRotationTransformationAngle_Deg()>89) extra=4;
		if(getRotationTransformationAngle_Deg()>271) extra=1;
		g2.drawString(text, -extra*g2.getFontMetrics().stringWidth(text)/2, 0);
	}

	protected void drawString_inM(Graphics2D g, String text, Point topLeft, double rotation){
		Graphics2D g2 = (Graphics2D) g.create();

		Point midLeft = topLeft.offsetYByPixels(3*g2.getFontMetrics().getHeight()/16);
		Point midmid = midLeft.offsetXByPixels(g2.getFontMetrics().stringWidth(text)/2);
		g2.translate(midmid.x_pix(), midmid.y_pix());
		g2.rotate(getRotationTransformationAngle_Rad()+rotation);
//		int m = (rotation<0)? 1 : -1;
//		g2.translate(m, 0);

		g2.drawString(text, -g2.getFontMetrics().stringWidth(text)/2, 0);
	}

	protected void drawCircle_inM(Graphics2D g, Point centre, double radius, Color fill){
		Graphics2D g2 = (Graphics2D) g.create();

		//		centre = centre.offsetXByM(-radius).offsetYByM(-radius);
		int xOffset = -Xm_to_pixels(radius);
		int yOffset = -Ym_to_pixels(radius);

		Color previous = g2.getColor();
		if(fill != null){
			g2.setColor(fill);
			g2.fillOval(centre.x_pix()+xOffset, centre.y_pix()+yOffset, Xm_to_pixels(2*radius), Ym_to_pixels(2*radius));
			g2.setColor(previous);
		}
		g2.drawOval(centre.x_pix()+xOffset, centre.y_pix()+yOffset, Xm_to_pixels(2*radius), Ym_to_pixels(2*radius));
	}


	//----[ Specific Drawings ]-----------------------------------------------------------------------------
	protected void drawIdentifiers(Graphics2D g, double yHeight, double leftTextPos, double rightTextPos, boolean rotate) {
		Graphics2D g2 = (Graphics2D) g.create();

		String leftNum = getAirfield().getSmallAngledRunway().getIdentifier().substring(0,2);
		String leftSide = ""+getAirfield().getSmallAngledRunway().getSideLetter();

		String rightNum = getAirfield().getLargeAngledRunway().getIdentifier().substring(0,2);
		String rightSide = ""+getAirfield().getLargeAngledRunway().getSideLetter();

		int fontSize = Xm_to_pixels(5*getAirfield().getRunwayGirth()/8)-4;
		Font resized = new Font(IDENTIFIER_FONT,IDENTIFIER_STYLE,fontSize);
		while(g2.getFontMetrics(resized).stringWidth(rightNum) > Xm_to_pixels(getAirfield().getRunwayGirth())){
			fontSize--;
			resized = new Font(IDENTIFIER_FONT,IDENTIFIER_STYLE,fontSize);
		}
		g2.setFont(resized);
		g2.setColor(IDENTIFIER_COLOR);

		int fontPixelHeight = g2.getFontMetrics().getHeight();
		int rightPixelWidth = g2.getFontMetrics().stringWidth(rightNum);
		//Used to centre the letter
		int qtrWdith = rotate? 0 : 1*rightPixelWidth/4;

		//a rotator helper 
		Point leftPos, rightPos;
		leftPos = new Point(leftTextPos,yHeight);
		rightPos = new Point(rightTextPos,yHeight).offsetXByPixels(-rightPixelWidth);


		if(rotate){
			drawString_inM(g2, leftNum, leftPos, Math.PI / 2.0);
			drawString_inM(g2, leftSide, leftPos.offsetXByPixels(-fontPixelHeight).offsetYByPixels(qtrWdith), Math.PI / 2.0);
			drawString_inM(g2, rightNum, rightPos, -Math.PI / 2.0);
			drawString_inM(g2, rightSide, rightPos.offsetXByPixels(fontPixelHeight).offsetYByPixels(qtrWdith), -Math.PI / 2.0);
		}else{
			drawString_inM(g2, leftNum, leftPos);
			drawString_inM(g2, leftSide, leftPos.offsetYByPixels(fontPixelHeight).offsetXByPixels(qtrWdith));
			drawString_inM(g2, rightNum, rightPos);
			drawString_inM(g2, rightSide, rightPos.offsetYByPixels(fontPixelHeight).offsetXByPixels(qtrWdith));

		}
	}

	protected void drawDistance(Graphics2D g, String disName, double length, double begin, int heightLevel, double basicHeight){
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(DIMENSION_COLOR);

		FontMetrics fontMetrics = g2.getFontMetrics();
		int titleLen = fontMetrics.stringWidth(disName);

		double startX;
		if(getRunway().isSmallEnd()){
			startX = leftOfRunway() + begin;
		}else{
			startX = rightOfRunway() - begin; 
		}

		double endX = startX+s()*length;
		int pixelOffset = heightLevel*PIXEL_BUFFER;

		Point start = new Point(startX,basicHeight).offsetYByPixels(pixelOffset).offsetYByM(-INITIAL_BUFFER);
		Point end = new Point(endX,basicHeight).offsetYByPixels(pixelOffset).offsetYByM(-INITIAL_BUFFER);

		//These points make the gap for the text
		Point midStart = start.offsetXByM(s()*length/2).offsetXByPixels(s()*-3*titleLen/4);
		Point midEnd   = start.offsetXByM(s()*length/2).offsetXByPixels(s()* 3*titleLen/4);

		//Horiz
		this.drawLine_inM(g2, start, midStart);
		this.drawLine_inM(g2, end, midEnd);

		//Verticals
		this.drawLine_inM(g2, start.offsetYByPixels(0), start.offsetYByPixels(-pixelOffset-Ym_to_pixels(DIMENSION_GAP-INITIAL_BUFFER)));
		this.drawLine_inM(g2, end.offsetYByPixels(0), end.offsetYByPixels(-pixelOffset-Ym_to_pixels(DIMENSION_GAP-INITIAL_BUFFER)));


		//Placing the text on the left of the two mid points
		Point pWithSmallerX;
		if(midStart.x_m()<midEnd.x_m()){
			pWithSmallerX = midStart;
		}else{
			pWithSmallerX = midEnd;
		}
		this.drawString_inM(g2, disName, pWithSmallerX.offsetXByPixels(titleLen/4));
	}

	/** total height of arrow = */
	protected void drawArrowAround(Graphics g, Point start, double width, boolean pointLeft, Color fill, Color outline){
		Graphics2D g2 = (Graphics2D) g.create();
		int m = 1;
		if(pointLeft) m = -1;

		double radius = 1*width/4; //height
		double length = width;


		radius = Ypix_to_m(Xm_to_pixels(radius));
		double pointX = start.core_Xm()+m*width;
		double pointY = start.core_Ym();

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

	protected void drawPlane(Graphics2D g, double radius, Point centre, boolean pointLeft) {
		Graphics2D g2 = (Graphics2D) g.create();

		int m = pointLeft? 1 : -1;

		double x = centre.core_Xm();
		double y = centre.core_Ym();

		x = x - m*radius;
		radius *= 1.7;
		//half width => sort of single wing span
		double hwidth = radius*11/19;

		double xUn = radius/19; double yUn = hwidth/12;

		double hOneX = 		x+m*xUn/2;
		double twoX = 			x+m*2*xUn;
		double wingStartX=  (x+m*7*xUn);
		double wingCurveX = 	x+m*11*xUn;
		double wingEndX =  (x+m*11.5*xUn);
		double wingEndX2 = 	x+m*13*xUn;
		double dipStartX = 	x+m*14*xUn;
		double dipBotX = 		x+m*15*xUn;
		double tailStartX = 	x+m*17*xUn;
		double tailEndX = 		x+m*19*xUn;
		double buttX =   (x+m*17.5*xUn);

		double hOneY = 		y+yUn/2;
		double oneY = 			y+yUn;
		double wingCurveY = (y+10.5*yUn);
		double wingEndY =(y+11*xUn);
		double tailY = 		y+4*yUn;

		double nhOneY = 			y-yUn/2;
		double noneY = 			y-yUn;
		double nwingCurveY = 	(y-10.5*yUn);
		double nwingEndY =  (y-11*xUn);
		double ntailY = 			y-4*yUn;

		double[] xes  = {x, hOneX, twoX, wingStartX, wingCurveX, wingEndX, wingEndX2, wingCurveX, dipStartX, dipBotX, tailStartX, tailEndX, tailStartX, buttX, /* other side */ tailStartX, tailEndX, tailStartX, dipBotX, dipStartX, wingCurveX, wingEndX2, wingEndX, wingCurveX, wingStartX, twoX, hOneX};  

		double[] yses = {y, hOneY, oneY, oneY,       wingCurveY, wingEndY, wingEndY,  oneY, 	   oneY,      hOneY,   tailY, 	   tailY,    hOneY,      y, /* other side */ nhOneY, ntailY, ntailY, nhOneY, noneY, noneY, nwingEndY, nwingEndY, nwingCurveY, noneY, noneY, nhOneY};


		Point[] points = new Point[xes.length];
		for(int i = 0; i < xes.length; i++){
			points[i] = new Point(xes[i],yses[i]);
		}
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(0.15f));
		drawPolygon_inM(g2, points, PLANE_COLOUR);
	}
	//===========================================================================================================================



	//======[ Misc/Distances ]=====================================================================================================

	public void save(String fullpath, String ext) throws IOException{
		ImageIO.write(image, ext, new File(fullpath));
	}

	public static final Color getTransparant(Color color, int transparency){
		return new Color(color.getRed(),color.getGreen(),color.getBlue(),transparency);
	}

	protected abstract double largestHeight();

	protected abstract double vertToRunway();

	protected abstract double leftOfRunway();

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


	//----[ Point Class ]---------------------------------------------------------------------------------------
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
		public double y_m(){ return rotateYm(); }

		//Goto rescaleImage() to see why we add getFULL_IMAGE_WIDTH()/2
		public int x_pix() { return Xm_to_pixels(this.x_m())+getZOOMED_IMAGE_WIDTH()/2; }
		public int y_pix() {
			if(sameScaleAsX){ 
				return Ym_to_pixels(this.y_m())+getZOOMED_IMAGE_WIDTH()/2; 
			}else{
				return Ym_to_pixels(this.y_m())+getZOOMED_IMAGE_HEIGHT()/2; 
			}
		}


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


			//			if(getRotationTransformationAngle_Deg()!=0){
			//				System.out.println("Rotate("+getRotationTransformationAngle_Deg()+"): "+core_Xm()+","+core_Ym()+" -> "+result.core_Xm()+","+result.core_Ym());
			//				if(result.core_Xm() != core_Xm() || result.core_Ym() != core_Ym()){
			//					System.out.println("^---> Change: "+(result.core_Xm() - core_Xm()) +"   "+(result.core_Ym() - core_Ym()));
			//				}
			//			}
			return result;
		}
	}

	protected abstract int[] getScaleLocation();
	protected abstract boolean doesScalePointDown();
	protected abstract double metresToScale();

	class ViewPort_withOverlay extends JViewport{
		private static final long serialVersionUID = 1L;

		public ViewPort_withOverlay(){
			super();
			setOpaque(false);
		}

		@Override
		public void paint(Graphics g){
			super.paint(g);
			Graphics2D g_prime = (Graphics2D) g.create();
			paintOverlays(g_prime);
		}

		@Override
		public void scrollRectToVisible(Rectangle aRect){
			super.scrollRectToVisible(aRect);
		}

		private void paintOverlays(Graphics2D g){
			int x = getScaleLocation()[0];
			int y = getScaleLocation()[1];
			drawScale(g, x,y, metresToScale(), doesScalePointDown());

			if(allowRotation){
				drawCompass(g);
			}
		}

		private void drawCompass(Graphics2D g) {
			// TODO Auto-generated method stub

		}



		/** The line that says how big the image is  */
		protected void drawScale(Graphics2D g, int xStart, int yStart, double metresToScale, boolean valuesBelow) {
			Graphics2D g2 = (Graphics2D) g.create();

			//the bumpHeight is the verticals on each end and mid
			int bumpHeight = 10;
			int m = valuesBelow? -1 : 1;

			int pixelatedMeteresToScale = Xm_to_pixels(metresToScale);

			int scaleEnd = xStart + pixelatedMeteresToScale;
			int scaleMid = xStart + pixelatedMeteresToScale/2;

			g2.setColor(Color.BLACK);

			//skinny mid vert
			g2.drawLine(scaleMid, yStart, scaleMid, yStart-m*bumpHeight/2);

			g2.setStroke(new BasicStroke(2f));

			//fat horiz
			g2.drawLine(xStart, yStart, scaleEnd, yStart);

			//fat verts
			g2.drawLine(xStart, yStart, xStart, yStart-m*bumpHeight);
			g2.drawLine(scaleEnd, yStart, scaleEnd, yStart-m*bumpHeight);

			//text labels
			int fontHeightPix = g2.getFontMetrics().getHeight();
			int disWdith = g2.getFontMetrics().stringWidth(""+(int) metresToScale+"m");
			int zeroWidth = g2.getFontMetrics().stringWidth("0m");

			//a little multiplier that ensures 
			int j = (m==1)? 1: 3;

			int xZero = xStart-zeroWidth/2;
			int yZero = yStart-m*(j*fontHeightPix/4+bumpHeight);
			g2.drawString("0m", xZero, yZero);

			int xValue = scaleEnd-disWdith/2;
			int yValue = yZero;
			g2.drawString(""+(int)metresToScale+"m", xValue, yValue);
		}

	}

}
