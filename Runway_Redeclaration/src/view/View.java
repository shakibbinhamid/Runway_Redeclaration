package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Core.Airport;
import Core.Obstacle;
import Core.PositionedObstacle;
import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import Exceptions.CannotMakeRunwayException;
import Exceptions.UnrecognisedAirfieldIntifierException;
import Exceptions.VariableDeclarationException;


/**
 * Preferred Size : 505 wide 275 tall
 * @author Shakib
 * @Editor Shakib Stefan
 *
 */
public class View extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private AirfieldInterface field;
	private DeclaredRunwayInterface run;
	
	private int defGirth, stripEnd, longSpacer, shortSpacer, mediumSpacer, shortLength, longLength;
	private int defTora, defSmallDT, defLargeDT, defStopway, defClearway, defTotalWidth;
	private int tora, asda, toda, lda, dt, startOfRoll;
	private String s, ss;

	private static int SPACE_ON_BOTH_SIDES = 20;
	private static int DT_TO_BARCODE_LENGTH_RATIO_TO_TORA = 100;
	private static int TORA_TO_DASH_RATIO = 100;
	private static int GIRTH_TO_FONT_RATIO = 2;
	private static int TORA_TO_BAR_CODE_RATIO = 20;
	private static int TORA_TO_CLEARWAY_RATIO = 10;
	private static int VIRTUAL_GAP = 20;

	private static Color purple = new Color(128,128,255);
	private static Color grass = new Color(95,245,22);
	private static Color clearedBlue = new Color(0,128,255);
	private static Color transparentYellow = new Color(255, 255, 0, 200);
	private static Color transparentRed = new Color(255, 0, 0, 150);
	private static Color VERYtransparentRed = new Color(255, 0, 0, 100);
	private static Color VERY_VERY_transparentRed = new Color(255, 0, 0, 50);

	private static final int ARR_SIZE = 4;

	public View(AirfieldInterface field, DeclaredRunwayInterface run){
		this.setField(field);
		this.setRunway(run);
	}

	public AirfieldInterface getField() {
		return field;
	}

	public void setField(AirfieldInterface field) {
		this.field = field;
		
		defGirth = (int) field.getRunwayGirth();
		defSmallDT = (int) field.getDefaultSmallAngledRunway().getDisplacedThreshold();
		defLargeDT = (int) field.getDefaultLargeAngledRunway().getDisplacedThreshold();
		stripEnd = (int) field.getStripEnd();
		longSpacer = (int) field.getLongSpacer();
		shortSpacer = (int) field.getShortSpacer();
		mediumSpacer = (int) field.getMediumSpacer();
		shortLength = (int) field.getShortLength();
		longLength = (int) field.getLongLength();

		s = field.getDefaultSmallAngledRunway().getIdentifier();
		ss = field.getDefaultLargeAngledRunway().getIdentifier();
	}

	public DeclaredRunwayInterface getRunway() {
		return run;
	}

	public void setRunway(DeclaredRunwayInterface runway) {
		this.run = runway;
		DeclaredRunwayInterface defaultR = runway.isSmallEnd() ? field.getDefaultSmallAngledRunway() : field.getDefaultLargeAngledRunway();
		
		defTora = (int) defaultR.getTORA();
		defTotalWidth = (int) this.field.getTotalWidth();
		defStopway = (int) defaultR.getStopway();
		defClearway = (int) defaultR.getClearway();
		
		tora = (int) getRunway().getTORA();
		asda = (int) getRunway().getASDA();
		toda = (int) getRunway().getTODA();
		lda = (int) getRunway().getLDA();
		startOfRoll = (int) getRunway().getStartOfRoll();
		dt = (int) getRunway().getDisplacedThreshold();
	}

	public static void main(String[] s){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				
				AirportInterface port = new Airport("Jim International");
				AirfieldInterface air = null;
				DeclaredRunwayInterface runway = null;
				try {
					port.addNewAirfield(90, 'L', new double[] {3902,3902,3902,3596}, new double[] {3884,3884,3962,3884});
					air = port.getAirfield(port.getAirfieldNames().get(0));
					runway = air.getSmallAngledRunway();
					air.addObstacle(new Obstacle("A600",100,12), -50, 3646);//TODO I added an obstacle!
					
					System.out.println("TORA: "+ runway.getTORA());
					System.out.println("TODA: "+ runway.getTODA());
					System.out.println("ASDA: "+ runway.getASDA());
					System.out.println("LDA: "+ runway.getLDA());
					
					System.out.println("ROLL: "+ runway.getStartOfRoll());
					System.out.println("DT: "+ runway.getDisplacedThreshold());
					System.out.println("TOTAL: "+ air.getTotalWidth());
					
					
				} catch (CannotMakeRunwayException | VariableDeclarationException e) {
					e.printStackTrace();
				} catch (UnrecognisedAirfieldIntifierException e) {
					e.printStackTrace();
				}
				JPanel pane = new View(air, runway);
				
				JFrame frame = new JFrame();
				frame.setMinimumSize(new Dimension (600,500));
				frame.setContentPane(pane);
				frame.setTitle("Testing");
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

				pane.repaint();
				
				
			}});
		try {
			Thread.sleep(60*1000);//60 secs
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void save(String fullpath) throws IOException{
		ImageIO.write(image, "PNG", new File(fullpath));
	}

	public void paint(Graphics g){
		super.paint(g);
		
		System.out.println("TORA: "+ tora);
		System.out.println("TODA: "+ toda);
		System.out.println("ASDA: "+ asda);
		System.out.println("LDA: "+ lda);
		
		System.out.println("ROLL: "+ startOfRoll);
		System.out.println("DT: "+ dt);
		System.out.println("TOTAL: "+ defTotalWidth);
		
		image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		doDrawing();
		g.drawImage(image, 0, 0, null);
	}

	private Graphics getGraphicsComp(Graphics g, Color c){
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(c);

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);
		return g2d;
	}
	
	private int direction(){
		return getRunway().isSmallEnd() ? 1:-1;
	}

	private void doDrawing(){
		Graphics2D g = (Graphics2D) image.getGraphics();
		doDrawing(g, s, ss,
				defTora, defGirth, defSmallDT, defLargeDT, defStopway, defClearway,
				stripEnd, longSpacer, shortSpacer, mediumSpacer, shortLength, longLength,
				tora, toda, asda, lda, dt, startOfRoll);
	}
	
	private void doDrawing(Graphics g,String s, String ss, 
			int defTora, int defGirth, int defSmalldt, int defLargedt, int defStopway, int defClearway,
			int stripEnd, int longSpacer, int shortSpacer, int mediumSpacer, int shortLength, int longLength,
			int tora, int toda, int asda, int lda, int dt, int startOfRoll) {

		defTora = scaleToPixels(defTora);
		defGirth = scaleToPixels(defGirth);
		defSmalldt = scaleToPixels(defSmalldt);
		defLargedt = scaleToPixels(defLargedt);
		defStopway = scaleToPixels(defStopway);
		defClearway = scaleToPixels(defClearway);
		
		stripEnd = scaleToPixels(stripEnd);
		longSpacer = scaleToPixels(longSpacer);
		shortSpacer = scaleToPixels(shortSpacer);
		mediumSpacer = scaleToPixels(mediumSpacer);
		shortLength = scaleToPixels(shortLength);
		longLength = scaleToPixels(longLength);

		tora = scaleToPixels(tora);
		toda = scaleToPixels(toda);
		asda = scaleToPixels(asda);
		lda = scaleToPixels(lda);
		dt = scaleToPixels(dt);
		startOfRoll = scaleToPixels(startOfRoll);

		Graphics2D g2 = (Graphics2D) g.create();
		//TODO find me tag
		colorFrame(getGraphicsComp(g2, grass));
		drawWholeArea(getGraphicsComp(g2, purple), defTora, stripEnd, longSpacer);
		drawClearedAndGradedArea(getGraphicsComp(g2, clearedBlue), defTora, stripEnd, shortSpacer, mediumSpacer, shortLength, longLength);
		drawMainRunwayRect(getGraphicsComp(g2, Color.gray), defTora, defGirth);
		drawClearway(getGraphicsComp(g2, transparentYellow), defTora, defGirth, direction(), defClearway);
		drawStopway(getGraphicsComp(g2, transparentRed), defTora, defGirth, direction(), defStopway);
		drawdtLines(getGraphicsComp(g2, Color.red), defTora, defGirth, defSmalldt, defLargedt);
		drawBarCode(getGraphicsComp(g2, Color.white), defTora, defGirth, defSmalldt, defLargedt);
		drawCenterLine(getGraphicsComp(g2, Color.white), defTora, defGirth, defSmalldt, defLargedt);
		drawIdentifier(getGraphicsComp(g2, Color.white), s, ss, defTora, defGirth, defSmalldt, defLargedt);
		drawAllDim(getGraphicsComp(g2, Color.black), direction(), defTora, defGirth, tora, toda, asda, lda, dt, startOfRoll);
		drawDirection(getGraphicsComp(g2, Color.RED), "Landing and TakeOff Direction: "+ run.getIdentifier(), defGirth);

		drawScale(getGraphicsComp(g2, Color.black), defGirth, 500);
		drawFatArrow(getGraphicsComp(g2, Color.RED), defGirth);
		drawObstacle(g2,defSmalldt,defLargedt,defTora,defGirth);
	}
	
	private int scaleToPixels(int dim){
		return scaleToPixels(defTotalWidth, this.getWidth(), dim);
	}

	private int scaleToPixels (int howMuchWantToFit, int inHowMuch, int whatYouAreScaling){
		BigDecimal value = new BigDecimal( whatYouAreScaling * inHowMuch/howMuchWantToFit);
		return value.intValue();
	}

	private int pixelsToScale (int howMuchMeters, int howManyPixels, int whatInPixels){
		return new BigDecimal ( whatInPixels * howMuchMeters/howManyPixels ).intValue();
	}
	
	private int pixelsToScale(int pixels){
		return pixelsToScale(defTotalWidth, this.getWidth(), pixels);
	}
	
	private void colorFrame(Graphics g){
		g.create().fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	private void drawScale(Graphics g, int defGirth, int howMuchWantToView){
		Graphics2D g2 = (Graphics2D) g.create();
	
		Font font = new Font("verdana", Font.PLAIN, 15);
		FontMetrics fontMetrics = g2.getFontMetrics(font);
		int initLen = fontMetrics.stringWidth("0");
		int finalLen = fontMetrics.stringWidth(String.valueOf(howMuchWantToView)); 
		g2.setFont(font);
		
		int howMuchInPixels = scaleToPixels(howMuchWantToView);
		int startX = 10;
		int Y = getHeight()/2 + defGirth * 5;
		int endX = startX + howMuchInPixels;
		
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(startX, Y, endX, Y);
		g2.drawLine(startX, Y, startX, Y - 5);
		g2.drawLine(endX, Y, endX, Y - 5);
		
		g2.drawString("0", startX - initLen/2, Y - font.getSize());
		g2.drawString(String.valueOf(howMuchWantToView), endX - finalLen/2, Y - font.getSize());

	}

	/** Draw purple rectangle */
	private void drawWholeArea(Graphics g, int tora, int stripSide, int longSpacer){
		Graphics2D g2 = (Graphics2D) g.create();

		int startX = this.getWidth()/2 - tora/2 - stripSide;
		int startY = this.getHeight()/2 - longSpacer;

		int width = tora + 2*stripSide;
		int height = 2*longSpacer;

		g2.fillRect(startX, startY, width, height);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(0.5f));
		g2.drawRect(startX, startY, width, height);
	}

	/** Blue region in diagram  */
	private void drawClearedAndGradedArea(Graphics g, int tora, int stripEnd, int shortSpacer, int mediumSpacer, int shortLength, int longLength){
		Graphics2D g2  = (Graphics2D) g.create();

		int X1 = this.getWidth()/2 - tora/2  -stripEnd;	int X12 = X1;
		int X2 = X1 + stripEnd + shortLength;						int X11 = X2;
		int X3 = X1 + stripEnd + longLength;						int X10 = X3;

		int X6 = this.getWidth()/2 + tora/2 + stripEnd;	int X7 = X6;
		int X5 = X6 - stripEnd - shortLength;						int X8 = X5;
		int X4 = X6 - stripEnd - longLength;						int X9 = X4;

		int Y1 = this.getHeight()/2 - shortSpacer;		int Y12 = Y1 + 2*shortSpacer;
		int Y2 = Y1;							int Y11 = Y12;
		int Y3 = this.getHeight()/2 - mediumSpacer;		int Y10 = Y3 + 2*mediumSpacer;

		int Y4 = Y3;							int Y7 = Y12;
		int Y5 = Y1;							int Y8 = Y12;
		int Y6 = Y1;							int Y9 = Y10;

		//together theses are the points of the 12 sided polygon that we recognise
		int[] x = new int[]{X1,X2,X3,X4,X5,X6,X7,X8,X9,X10,X11,X12};
		int[] y = new int[]{Y1,Y2,Y3,Y4,Y5,Y6,Y7,Y8,Y9,Y10,Y11,Y12};

		g2.fillPolygon (x, y, 12);
		//outline
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(0.5f));
		g2.drawPolygon (x, y, 12); 
	}

	/** The grey long rectangle duh */
	private void drawMainRunwayRect(Graphics g, int tora, int defGirth){
		Graphics2D g2  = (Graphics2D) g.create();

		int startX = this.getWidth()/2 - tora/2;
		int startY = this.getHeight()/2 - defGirth/2;

		g2.fillRect(startX, startY, tora, defGirth);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(0.5f));
		g2.drawRect(startX, startY, tora, defGirth);
	}

	/** Displaced Threshold lines (red verticals originally) */
	private void drawdtLines(Graphics g, int tora, int defGirth, int dt1, int dt2){
		Graphics2D g2  = (Graphics2D) g.create();

		int	startX = this.getWidth()/2 	- tora/2 + dt1;
		int startY = this.getHeight()/2 - defGirth/2;
		int endX = startX;
		int endY = startY + defGirth;

		g2.drawLine(startX, startY, endX, endY);

		startX = this.getWidth()/2 + tora/2 - dt2;
		startY = this.getHeight()/2 - defGirth/2;
		endX = startX;
		endY = startY + defGirth;

		g2.drawLine(startX, startY, endX, endY);
	}

	/** ... obvious */
	private void drawBarCode(Graphics g, int tora, int defGirth, int smalldt, int largedt){
		Graphics2D g2 = (Graphics2D) g.create();

		final BasicStroke thick = new BasicStroke(defGirth/20f);
		g2.setStroke(thick);

		int dtToBar = tora/DT_TO_BARCODE_LENGTH_RATIO_TO_TORA;
		int bar = tora/TORA_TO_BAR_CODE_RATIO;

		int startX = getWidth()/2 - tora/2 + smalldt + dtToBar;
		int startY = getHeight()/2 - defGirth/2;
		int endX = startX + bar;

		//draws 5 lines
		for (int i=0; i<4; i++){
			startY = startY + defGirth/5;
			g2.drawLine(startX, startY, endX, startY);
		}

		startX = getWidth()/2 + tora/2 - largedt - dtToBar;
		startY = getHeight()/2 - defGirth/2;
		endX = startX - bar;

		//draws 5 lines
		for (int i=0; i<4; i++){
			startY = startY + defGirth/5;
			g2.drawLine(startX, startY, endX, startY);
		}
	}

	private void drawCenterLine(Graphics g, int tora, int defGirth, int smalldt, int largedt){
		Graphics2D g2 = (Graphics2D) g.create();

		final float dash1[] = {tora/TORA_TO_DASH_RATIO};
		final BasicStroke dashed =
				new BasicStroke(defGirth/25f,
						BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER,
						10.0f, dash1, 0.0f);
		g2.setStroke(dashed);

		int dtToBar = tora/DT_TO_BARCODE_LENGTH_RATIO_TO_TORA;
		int bar = tora/TORA_TO_BAR_CODE_RATIO;

		int startX = this.getWidth()/2 - tora/2 + smalldt + dtToBar + bar + scaleToPixels(200);
		int startY = this.getHeight()/2;
		int endX = this.getWidth()/2 + tora/2 - largedt - dtToBar - bar - scaleToPixels(200);
		int endY = startY;

		g2.drawLine(startX, startY, endX, endY);
	}

	/** Puts the 09L etc on each side of the runway  */
	private void drawIdentifier(Graphics g, String stripEnd, String s2, int tora, int defGirth, int dt1, int dt2){
		Graphics2D g2 = (Graphics2D) g.create();
		
		String regex = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";

		String leftangle = stripEnd.split(regex)[0];
		String leftletter = stripEnd.split(regex)[1];
		
		String rightangle = s2.split(regex)[0];
		String rightletter = s2.split(regex)[1];
		
		Font font = new Font("verdana", Font.BOLD, defGirth * 3/4);
		FontMetrics fontMetrics = g2.getFontMetrics(font);
		int leftangleLen = fontMetrics.stringWidth(leftangle);
		int rightangleLen = fontMetrics.stringWidth(rightangle);
		
		int leftletterLen = fontMetrics.stringWidth(leftletter);
		int leftletterHeight = fontMetrics.getAscent()-fontMetrics.getDescent();
		
		int rightletterLen = fontMetrics.stringWidth(rightletter);
		int rightletterHeight = fontMetrics.getAscent()-fontMetrics.getDescent();
		g2.setFont(font);

		final BasicStroke thick =
				new BasicStroke(2);
		g2.setStroke(thick);

		int dtToBar = tora/DT_TO_BARCODE_LENGTH_RATIO_TO_TORA;
		int bar = tora/TORA_TO_BAR_CODE_RATIO;
		int textGap = Math.max(rightletterHeight,leftletterHeight)/3;
		AffineTransform at = new AffineTransform();
		AffineTransform old = g2.getTransform();
		at.setToRotation(Math.PI / 2.0);
		g2.setTransform(at);
		g2.drawString(leftangle, getHeight()/2 - leftangleLen/2, -(getWidth()/2 - tora/2 + dt1 + dtToBar + bar + 5 + leftletterHeight+textGap));
		g2.drawString(leftletter, getHeight()/2 - leftletterLen/2, -(getWidth()/2 - tora/2 + dt1 + dtToBar + bar + 5 + leftletterHeight/10));

		AffineTransform at2 = new AffineTransform();
		at2.setToRotation(-Math.PI / 2.0);
		g2.setTransform(at2);
		g2.drawString(rightangle, -getHeight()/2 - rightangleLen/2, (getWidth()/2 + tora/2 - dt2 - dtToBar - bar - 5 - rightletterLen-textGap) );
		g2.drawString(rightletter, -getHeight()/2 - rightletterLen/2, (getWidth()/2 + tora/2 - dt2 - dtToBar - bar - 5 - rightletterHeight/10));
		g2.setTransform(old);
	}

	private void drawStopway(Graphics g, int tora, int defGirth, int direction, int stop){
		
		Graphics2D g2 = (Graphics2D) g.create();
		
		int startX, startY;
		if(direction == 1)
			startX = getWidth()/2 + tora/2;
		else
			startX = getWidth()/2 - tora/2 - stop;
		startY = getHeight()/2 - defGirth/2;
		
		g2.fillRect(startX, startY, stop, defGirth);
	}
	
	private void drawClearway(Graphics g, int tora, int defGirth, int direction, int clear){
		
		Graphics2D g2 = (Graphics2D) g.create();
		
		int startX, startY;
		if(direction == 1)
			startX = getWidth()/2 + tora/2;
		else
			startX = getWidth()/2 - tora/2 - clear;
		startY = getHeight()/2 - defGirth;
		g2.fillRect(startX, startY, clear, defGirth*2);
	}

	/** Draws all virtual dimensions */
	private void drawAllDim(Graphics g, int direction, int defTora, int defGirth, int tora, int toda, int asda, int lda, int dt, int startOfRoll){
		int height = defGirth/2;
		int bumper = 20;
		drawdim(g, direction, defTora, defGirth, "LDA", dt, lda, height + bumper);
		drawdim(g, direction, defTora, defGirth, "TORA", startOfRoll, tora, height + bumper + 1*VIRTUAL_GAP);
		drawdim(g, direction, defTora, defGirth, "ASDA", startOfRoll, asda, height + bumper + 2*VIRTUAL_GAP);
		drawdim(g, direction, defTora, defGirth, "TODA", startOfRoll, toda, height + bumper + 3*VIRTUAL_GAP);
	}

	/** Draws the arrows and labels for the virtual distances  */
	private void drawdim(Graphics g, int direction, int tora, int defGirth, String variableName, int startWhere, int howlong, int howhighUp){
		Graphics2D g2 = (Graphics2D) g.create();//for arrows
		Graphics2D g3 = (Graphics2D) g.create();//for text

		Font font = new Font("verdana", Font.BOLD, 10);
		FontMetrics fontMetrics = g3.getFontMetrics(font);
		int titleLen = fontMetrics.stringWidth(variableName);
		g3.setFont(font);

		int startX = 0, endX = 0, Y = 0;
		
		//first line vals
		if(direction == 1){
			endX = getWidth()/2 - tora/2 + startWhere;
			startX = this.getWidth()/2 - titleLen/2 ;
		}else{
			endX = getWidth()/2 + tora/2 - startWhere;
			startX = getWidth()/2 + titleLen/2;
		}
		
		Y = getHeight()/2 - defGirth/2 - howhighUp;

		drawArrow(g, startX, Y, endX, Y);

		//vertical line to edge of runway
		g2.setStroke(new BasicStroke(0.75f));
		g2.drawLine(endX, Y, endX, getHeight()/2-defGirth/2);
		
		//-----------------------------------------------------
		if(direction == 1)
			g3.drawString(variableName, startX, Y+3);
		else
			g3.drawString(variableName, startX - titleLen, Y+3);
		//------------------------------------------------------

		//Second line vals
		if (direction == 1){
			endX += howlong;
			startX += titleLen;
		} else{
			endX -= howlong;
			startX -= titleLen;
		}

		drawArrow(g, startX, Y, endX, Y);

		//vertical line to edge of runway
		g2.drawLine(endX, Y, endX, getHeight()/2-defGirth/2);
	}
	
	private void drawDirection(Graphics g, String s, int defGirth){
		Graphics2D g2 = (Graphics2D) g.create();
		
		Font font = new Font("verdana", Font.BOLD+Font.ITALIC, 16);
		FontMetrics fontMetrics = g.getFontMetrics(font);
		int titleLen = fontMetrics.stringWidth(s);
		g2.setFont(font);
		g2.drawString(s, (getWidth() / 2) - (titleLen / 2), 7*getHeight()/10+ defGirth*5);
	}

	private void drawFatArrow(Graphics g, int defGirth){
		final double GOING_LEFT = -Math.PI/2;
		final double GOING_RIGHT = Math.PI/2;
		
		int x = getWidth()/2;
		int y = 3*getHeight()/5 + defGirth*3;
		
		int planeDirection;
		if(getRunway().isSmallEnd()){
			drawArrowAround(g, x, y, defGirth, GOING_RIGHT, g.getColor(), Color.BLACK);
			planeDirection = -1;
			
		}else{
			drawArrowAround(g, x, y, defGirth, GOING_LEFT, g.getColor(), Color.BLACK);
			planeDirection = 1;
		}
		int planeWingSpan = pixelsToScale(getHeight()/10);
		int planex = x-planeDirection*defGirth*3;
		int planey = y;
		drawPlane(g, planeWingSpan, planex, planey, planeDirection);
	}
	
	private void drawArrowAround(Graphics g, int pointX, int pointY, int runwayGirth ,double angleInPi, Color fill, Color outline){
		Graphics2D g2 = (Graphics2D) g.create();
		int radius = 7*runwayGirth/8; int length = 14*radius/3;
		
		int m = 1;
		if(angleInPi<0) m = -1;
		
		int midX = pointX-m*length/2;
		int backX = pointX-m*length;
		int eithBack = pointX-m*7*length/8;
		
		int thirdG = pointY+radius/3;		int negthirdG = pointY-radius/3;
		int thirdG2 = pointY+2*radius/3;	int negthirdG2 = pointY-2*radius/3;
		int halfG = pointY+radius; 			int neghalfG = pointY-radius;
		
		int[] xes =  {pointX, midX,  midX,   backX,   eithBack, backX,      midX,      midX};
		int[] yses = {pointY, halfG, thirdG, thirdG2, pointY,   negthirdG2, negthirdG, neghalfG};
		
		g2.setColor(fill);
		g2.fillPolygon(xes, yses, xes.length);
		g2.setColor(outline);
		g2.setStroke(new BasicStroke(0.35f));
		g2.drawPolygon(xes, yses, xes.length);
	}
	
	/** Whole single arrow */
	private void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g2 = (Graphics2D) g1.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx*dx + dy*dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g2.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g2.drawLine(0, 0, len, 0);
		g2.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
				new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
	}
	
	private void drawObstacle(Graphics2D g, int defSmalldt,int defLargedt, int defTora, double defGirth ) {
		Graphics2D g2 = (Graphics2D) g.create();
		Graphics2D g3 = (Graphics2D) g.create();

		PositionedObstacle obj = (PositionedObstacle) getField().getPositionedObstacle();
		if (obj==null) return;

		int x;
		int y = getHeight()/2;
		if (getRunway().isSmallEnd()){
			int dToLeftSide = this.getWidth()/2 - defTora/2;
			x = dToLeftSide + defSmalldt + scaleToPixels( (int) obj.distanceFromSmallEnd());
		}else{
			int dToRightSide = this.getWidth()/2 + defTora/2;
			x = dToRightSide - defLargedt - scaleToPixels((int)obj.distanceFromLargeEnd());
		}


		//====[ With Radius ]=========
		int radius = scaleToPixels((int) obj.getRadius());
		int diamter = radius*2;
		//Body
		g2.setColor(VERYtransparentRed);
		g2.fillOval(x-radius, y-radius, diamter, diamter);
		//Rim
		g2.setColor(transparentRed);
		g2.setStroke(new BasicStroke(0.75f));
		g2.drawOval(x-radius, y-radius, diamter, diamter);

		//-----[ RESA/ALS/Blast ]-----
		int largestFactor; 
		String factorName;
		double ALS = getRunway().getAscentAngle()*getField().getPositionedObstacle().getHeight();
		System.out.println("RESA: "+getRunway().getRESA()+"   ALS: "+ALS+"    Blast: "+getField().getBlastAllowance());

		//find largest factor
		if(getRunway().getRESA() > ALS &&  ALS > getField().getBlastAllowance()){
			largestFactor = scaleToPixels((int) getRunway().getRESA());
			factorName = "RESA";
			
		}else if(ALS > getField().getBlastAllowance()){
			largestFactor = scaleToPixels((int) ALS);
			factorName = "ALS";

		}else{
			largestFactor = scaleToPixels((int) getField().getBlastAllowance());
			factorName = "Blast Zone";

		}
		int maxRadius = radius + largestFactor;

		//Body
		g3.setColor(VERY_VERY_transparentRed);
		g3.fillOval(x-maxRadius, y-maxRadius, maxRadius*2, maxRadius*2);
		//Rim
		g3.setColor(transparentRed);
		g3.drawOval(x-maxRadius, y-maxRadius, maxRadius*2, maxRadius*2);
		
		drawLargestFactorOnCircle(g, factorName, x, y-maxRadius, Color.BLACK);


		//=[ planes ]=
		// draw cheeky plane
		Graphics2D g4 = (Graphics2D) g.create();
		if (getField().getPositionedObstacle().getName().matches("[a-zA-Z][0-9]+")) {
			System.out.print("PLANES PLANES PLANES PLANES");
			drawPlane(g4, (int) obj.getRadius(), x, y, -1);
		}
		//=====[ No Radius ]=======
		// draw an 'X' at point
		int h = (int) (defGirth/6);//not scaled
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(x+h, y+h, x-h, y-h);
		g2.drawLine(x-h, y+h, x+h, y-h);

	}
	private void drawLargestFactorOnCircle(Graphics g, String factor ,int startBoxX, int startBoxY, Color textColor){
		Graphics2D g2 = (Graphics2D) g.create();
		
		g2.setColor(textColor);
		int size = 10;
		g2.setFont(new Font("verdana", Font.BOLD, size));
		startBoxX -= g.getFontMetrics().stringWidth(factor)/2;
		startBoxY -= g.getFontMetrics().getHeight()/4;
		
		g2.drawString(factor, startBoxX, startBoxY);
		
		
	}
	
	/**
	 * 
	 * @param direction 1 = heading left -1 = heading right
	 */
	private void drawPlane(Graphics g, int radius, int x, int y, double direction) {
		System.out.println("PLANES TIME");
		Graphics2D g2 = (Graphics2D) g.create();
		int m = (int) direction;

		
		
		radius = scaleToPixels(radius);
		x = x - m*7*radius/8;
		radius *= 2;
		//half width => sort of single wing span
		int hwidth = radius*12/19;

		int xUn = radius/19; int yUn = hwidth/12;

		int hOneX = 		x+m*xUn/2;
		int twoX = 			x+m*2*xUn;
		int wingStartX=(int)(x+m*7*xUn);
		int wingCurveX = 	x+m*11*xUn;
		int wingEndX =(int)(x+m*11.5*xUn);
		int wingEndX2 = 	x+m*13*xUn;
		int dipStartX = 	x+m*14*xUn;
		int dipBotX = 		x+m*15*xUn;
		int tailStartX = 	x+m*17*xUn;
		int tailEndX = 		x+m*19*xUn;
		int buttX =  (int) (x+m*17.5*xUn);

		int hOneY = 		y+yUn/2;
		int oneY = 			y+yUn;
		int wingCurveY =(int) (y+10.5*yUn);
		int wingEndY =(int)(y+11*xUn);
		int tailY = 		y+4*yUn;

		int nhOneY = 			y-yUn/2;
		int noneY = 			y-yUn;
		int nwingCurveY = 	(int)(y-10.5*yUn);
		int nwingEndY =(int)   (y-11*xUn);
		int ntailY = 			y-4*yUn;

		int[] xes  = {x, hOneX, twoX, wingStartX, wingCurveX, wingEndX, wingEndX2, wingCurveX, dipStartX, dipBotX, tailStartX, tailEndX, tailStartX, buttX, /* other side */ tailStartX, tailEndX, tailStartX, dipBotX, dipStartX, wingCurveX, wingEndX2, wingEndX, wingCurveX, wingStartX, twoX, hOneX};  

		int[] yses = {y, hOneY, oneY, oneY,       wingCurveY, wingEndY, wingEndY,  oneY, 	   oneY,      hOneY,   tailY, 	   tailY,    hOneY,      y, /* other side */ nhOneY, ntailY, ntailY, nhOneY, noneY, noneY, nwingEndY, nwingEndY, nwingCurveY, noneY, noneY, nhOneY};

		g2.setColor(Color.gray);
		g2.fillPolygon(xes, yses, xes.length);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(0.15f));
		g2.drawPolygon(xes, yses, xes.length);
	}
}
