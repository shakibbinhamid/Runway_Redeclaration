package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.math.BigDecimal;

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
	
	private AirfieldInterface field;
	private DeclaredRunwayInterface run;
	private int defTora, girth, s1, s3, s4, s5, s6, s7, asda, toda, lda, startOfRoll, stopway, clearway;
	private int smallDT, largeDT, currentDT;
	private String s, ss;
	private int totalWidth;

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
		girth = (int) field.getRunwayGirth();
		smallDT = (int) field.getSmallAngledRunway().getDisplacedThreshold();
		largeDT = (int) field.getLargeAngledRunway().getDisplacedThreshold();
		s1 = (int) field.getStripEnd();
		s3 = (int) field.getLongSpacer();
		s4 = (int) field.getShortSpacer();
		s5 = (int) field.getMediumSpacer();
		s6 = (int) field.getShortLength();
		s7 = (int) field.getLongLength();

		s = field.getSmallAngledRunway().getIdentifier();
		ss = field.getLargeAngledRunway().getIdentifier();
	}

	public DeclaredRunwayInterface getRunway() {
		return run;
	}

	public void setRunway(DeclaredRunwayInterface runway) {
		this.run = runway;
		
		if (runway.isSmallEnd())
			defTora = (int) field.getDefaultSmallAngledRunway().getTORA();
		else
			defTora = (int) field.getDefaultLargeAngledRunway().getTORA();
		
		totalWidth = (int) this.field.getTotalWidth();
		asda = (int) getRunway().getASDA();
		toda = (int) getRunway().getTODA();
		lda = (int) getRunway().getLDA();
		startOfRoll = (int) getRunway().getStartOfRoll();
		currentDT = (int) getRunway().getDisplacedThreshold();
		stopway = (int) getRunway().getStopway();
		clearway = (int) getRunway().getClearway();
	}

	public static void main(String[] s){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				
				AirportInterface port = new Airport("Jim International");
				AirfieldInterface air = null;
				DeclaredRunwayInterface runway = null;
				try {
					port.addNewAirfield(90, 'L', new double[] {3902,3902,3902,3596}, new double[] {3884,3900,3962,3884});
					air = port.getAirfield(port.getAirfieldNames().get(0));
							
					air.addObstacle(new Obstacle("Box",12,0), -50, 3646);//TODO I added an obstacle!
				
					runway = air.getSmallAngledRunway();
					
					System.out.println("TORA: "+ runway.getTORA());
					System.out.println("TODA: "+ runway.getTODA());
					System.out.println("ASDA: "+ runway.getASDA());
					System.out.println("LDA: "+ runway.getLDA());
					
					System.out.println("ROLL: "+ runway.getStartOfRoll());
					System.out.println("DT: "+ runway.getDisplacedThreshold());
					
					
				} catch (CannotMakeRunwayException | VariableDeclarationException e) {
					e.printStackTrace();
				} catch (UnrecognisedAirfieldIntifierException e) {
					e.printStackTrace();
				}
				JPanel pane = new View(air, runway);

				JFrame frame = new JFrame();
				frame.setMinimumSize(new Dimension (500,300));
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

	public void paint(Graphics g){
		doDrawing(g);
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

	private void doDrawing(Graphics g){
		doDrawing(g, totalWidth, this.getWidth(), s, ss, defTora, girth, toda, asda, lda, smallDT, largeDT, currentDT, stopway, clearway, s1, s3, s4, s5, s6, s7, startOfRoll);
	}
	
	private void doDrawing(Graphics g, int x1, int x2, String s, String ss, 
			int tora, int girth, int toda, int asda, int lda, int smalldt, int largedt, int currentDT, int stopway, int clearway,
			int s1, int s3, int s4, int s5, int s6, int s7, int startOfRoll) {
		
		System.out.println("total  "+totalWidth);
		System.out.println("SoR  "+startOfRoll);
		
		
		tora = scaleToPixels(tora);
		girth = scaleToPixels(girth);
		smalldt = scaleToPixels(smalldt);
		largedt = scaleToPixels(largedt);
		currentDT = scaleToPixels(currentDT);
		
		s1 = scaleToPixels(s1);
		s3 = scaleToPixels(s3);
		s4 = scaleToPixels(s4);
		s5 = scaleToPixels(s5);
		s6 = scaleToPixels(s6);
		s7 = scaleToPixels(s7);

		toda = scaleToPixels(x1, x2, toda);
		asda = scaleToPixels(x1, x2, asda);
		lda = scaleToPixels(x1, x2, lda);
		stopway = scaleToPixels(x1, x2, stopway);
		clearway = scaleToPixels(x1, x2, clearway);
//		System.out.println(stopway); //TODO remove these
//		System.out.println(clearway);

		Graphics2D g2 = (Graphics2D) g.create();
		
		colorFrame(getGraphicsComp(g2, grass));
		drawWholeArea(getGraphicsComp(g2, purple), tora, s1, s3);
		drawClearedAndGradedArea(getGraphicsComp(g2, clearedBlue), tora, s1, s4, s5, s6, s7);
		drawMainRunwayRect(getGraphicsComp(g2, Color.gray), tora, girth);
		drawClearway(getGraphicsComp(g2, transparentYellow), tora, girth, direction(), clearway);
		drawStopway(getGraphicsComp(g2, transparentRed), tora, girth, direction(), stopway);
		drawdtLines(getGraphicsComp(g2, Color.red), tora, girth, smalldt, largedt);
		drawBarCode(getGraphicsComp(g2, Color.white), tora, girth, smalldt, largedt);
		drawCenterLine(getGraphicsComp(g2, Color.white), tora, girth, smalldt, largedt);
		drawIdentifier(getGraphicsComp(g2, Color.white), s, ss, tora, girth, smalldt, largedt);
		drawAllDim(getGraphicsComp(g2, Color.black), direction(), tora, girth, toda, asda, lda, currentDT, startOfRoll);
		drawDirection(getGraphicsComp(g2, Color.RED), "Landing and TakeOff Direction: "+ run.getIdentifier(), girth);

		drawScale(getGraphicsComp(g2, Color.black), girth, x1, x2, 500);
		drawFatArrow(getGraphicsComp(g2, Color.RED));
		drawObstacle(g2);
	}
	
	private int scaleToPixels(int dim){
		return scaleToPixels(totalWidth, this.getWidth(), dim);
	}

	private int scaleToPixels (int howMuchWantToFit, int inHowMuch, int whatYouAreScaling){
		BigDecimal value = new BigDecimal( whatYouAreScaling * inHowMuch/howMuchWantToFit);
		return value.intValue();
	}

	private int pixelsToScale (int howMuchMeters, int howManyPixels, int whatInPixels){
		return new BigDecimal ( whatInPixels * howMuchMeters/howManyPixels ).intValue();
	}
	
	private void colorFrame(Graphics g){
		g.create().fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	private void drawScale(Graphics g, int girth, int howMuchWantToFit, int inHowMuch, int howMuchWantToView){
		Graphics2D g2 = (Graphics2D) g.create();
	
		int howMuchInPixels = scaleToPixels(howMuchWantToFit, inHowMuch, howMuchWantToView);
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(10, getHeight()/2 + girth * 5, 10 + howMuchInPixels, getHeight()/2 + girth * 5);
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
	private void drawClearedAndGradedArea(Graphics g, int tora, int s1, int s4, int s5, int s6, int s7){
		Graphics2D g2  = (Graphics2D) g.create();

		int X1 = this.getWidth()/2 - tora/2  -s1;	int X12 = X1;
		int X2 = X1 + s1 + s6;						int X11 = X2;
		int X3 = X1 + s1 + s7;						int X10 = X3;

		int X6 = this.getWidth()/2 + tora/2 + s1;	int X7 = X6;
		int X5 = X6 - s1 - s6;						int X8 = X5;
		int X4 = X6 - s1 - s7;						int X9 = X4;

		int Y1 = this.getHeight()/2 - s4;		int Y12 = Y1 + 2*s4;
		int Y2 = Y1;							int Y11 = Y12;
		int Y3 = this.getHeight()/2 - s5;		int Y10 = Y3 + 2*s5;

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
	private void drawMainRunwayRect(Graphics g, int tora, int girth){
		Graphics2D g2  = (Graphics2D) g.create();

		int startX = this.getWidth()/2 - tora/2;
		int startY = this.getHeight()/2 - girth/2;

		g2.fillRect(startX, startY, tora, girth);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(0.5f));
		g2.drawRect(startX, startY, tora, girth);
	}

	/** Displaced Threshold lines (red verticals originally) */
	private void drawdtLines(Graphics g, int tora, int girth, int dt1, int dt2){
		Graphics2D g2  = (Graphics2D) g.create();

		int	startX = this.getWidth()/2 	- tora/2 + dt1;
		int startY = this.getHeight()/2 - girth/2;
		int endX = startX;
		int endY = startY + girth;

		g2.drawLine(startX, startY, endX, endY);

		startX = this.getWidth()/2 + tora/2 - dt2;
		startY = this.getHeight()/2 - girth/2;
		endX = startX;
		endY = startY + girth;

		g2.drawLine(startX, startY, endX, endY);
	}

	/** ... obvious */
	private void drawBarCode(Graphics g, int tora, int girth, int smalldt, int largedt){
		Graphics2D g2 = (Graphics2D) g.create();

		final BasicStroke thick = new BasicStroke(girth/20f);
		g2.setStroke(thick);

		int dtToBar = tora/DT_TO_BARCODE_LENGTH_RATIO_TO_TORA;
		int bar = tora/TORA_TO_BAR_CODE_RATIO;

		int startX = getWidth()/2 - tora/2 + smalldt + dtToBar;
		int startY = getHeight()/2 - girth/2;
		int endX = startX + bar;

		//draws 5 lines
		for (int i=0; i<4; i++){
			startY = startY + girth/5;
			g2.drawLine(startX, startY, endX, startY);
		}

		startX = getWidth()/2 + tora/2 - largedt - dtToBar;
		startY = getHeight()/2 - girth/2;
		endX = startX - bar;

		//draws 5 lines
		for (int i=0; i<4; i++){
			startY = startY + girth/5;
			g2.drawLine(startX, startY, endX, startY);
		}
	}

	private void drawCenterLine(Graphics g, int tora, int girth, int smalldt, int largedt){
		Graphics2D g2 = (Graphics2D) g.create();

		final float dash1[] = {tora/TORA_TO_DASH_RATIO};
		final BasicStroke dashed =
				new BasicStroke(girth/25f,
						BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER,
						10.0f, dash1, 0.0f);
		g2.setStroke(dashed);

		int dtToBar = tora/DT_TO_BARCODE_LENGTH_RATIO_TO_TORA;
		int bar = tora/TORA_TO_BAR_CODE_RATIO;

		int startX = this.getWidth()/2 - tora/2 + smalldt + dtToBar + bar + 20;
		int startY = this.getHeight()/2;
		int endX = this.getWidth()/2 + tora/2 - largedt - dtToBar - bar - 20;
		int endY = startY;

		g2.drawLine(startX, startY, endX, endY);
	}

	/** Puts the 09L etc on each side of the runway  */
	private void drawIdentifier(Graphics g, String s1, String s2, int tora, int girth, int dt1, int dt2){
		Graphics2D g2 = (Graphics2D) g.create();

		final BasicStroke thick =
				new BasicStroke(2);
		g2.setStroke(thick);
		int fontsize = girth/2;
		/*if (fontsize<8) fontsize=8;*/

		g2.setFont(new Font("verdana", Font.PLAIN, fontsize));

		int dtToBar = tora/DT_TO_BARCODE_LENGTH_RATIO_TO_TORA;
		int bar = tora/TORA_TO_BAR_CODE_RATIO;

		AffineTransform at = new AffineTransform();
		AffineTransform old = g2.getTransform();
		at.setToRotation(Math.PI / 2.0);
		g2.setTransform(at);
		g2.drawString(s1, getHeight()/2 - girth/2, -(getWidth()/2 - tora/2 + dt1 + dtToBar + bar + 5));

		AffineTransform at2 = new AffineTransform();
		at2.setToRotation(-Math.PI / 2.0);
		g2.setTransform(at2);
		g2.drawString(s2, -getHeight()/2 - girth/2, (getWidth()/2 + tora/2 - dt2 - dtToBar - bar - 5) );
		g2.setTransform(old);
	}

	private void drawStopway(Graphics g, int tora, int girth, int direction, int stop){
		
		Graphics2D g2 = (Graphics2D) g.create();
		
		int startX, startY;
		if(direction == 1)
			startX = getWidth()/2 + tora/2;
		else
			startX = getWidth()/2 - tora/2 - stop;
		startY = getHeight()/2 - girth/2;
		
		g2.fillRect(startX, startY, stop, girth);
	}
	
	private void drawClearway(Graphics g, int tora, int girth, int direction, int clear){
		
		Graphics2D g2 = (Graphics2D) g.create();
		
		int startX, startY;
		if(direction == 1)
			startX = getWidth()/2 + tora/2;
		else
			startX = getWidth()/2 - tora/2 - clear;
		startY = getHeight()/2 - girth;
		g2.fillRect(startX, startY, clear, girth*2);
	}

	/** Draws all virtual dimensions */
	private void drawAllDim(Graphics g, int direction, int tora, int girth, int toda, int asda, int lda, int dt, int startOfRoll){
		int height = girth/2;
		int bumper = 20;
		drawdim(g, direction, tora, girth, "LDA", dt, lda, height + bumper);
		drawdim(g, direction, tora, girth, "TORA", startOfRoll, tora, height + bumper + 1*VIRTUAL_GAP);
		drawdim(g, direction, tora, girth, "ASDA", startOfRoll, asda, height + bumper + 2*VIRTUAL_GAP);
		drawdim(g, direction, tora, girth, "TODA", startOfRoll, toda, height + bumper + 3*VIRTUAL_GAP);
	}

	/** Draws the arrows and labels for the virtual distances  */
	private void drawdim(Graphics g, int direction, int tora, int girth, String variableName, int startWhere, int howlong, int howhighUp){
		Graphics2D g2 = (Graphics2D) g.create();//for arrows
		Graphics2D g3 = (Graphics2D) g.create();//for text

		Font font = new Font("verdana", Font.PLAIN, 10);
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
		
		Y = getHeight()/2 - girth/2 - howhighUp;

		drawArrow(g, startX, Y, endX, Y);

		//vertical line to edge of runway
		g2.setStroke(new BasicStroke(0.75f));
		g2.drawLine(endX, Y, endX, getHeight()/2-girth/2);
		
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
		g2.drawLine(endX, Y, endX, getHeight()/2-girth/2);
	}
	
	private void drawDirection(Graphics g, String s, int girth){
		Graphics2D g2 = (Graphics2D) g.create();
		
		Font font = new Font("verdana", Font.BOLD+Font.ITALIC, 16);
		FontMetrics fontMetrics = g.getFontMetrics(font);
		int titleLen = fontMetrics.stringWidth(s);
		g2.setFont(font);
		g2.drawString(s, (getWidth() / 2) - (titleLen / 2), getHeight()/2 + girth*5);
	}

	private void drawFatArrow(Graphics g){
		final double GOING_LEFT = -Math.PI/2;
		final double GOING_RIGHT = Math.PI/2;
		
		int x = getWidth()/2;
		int y = 4*getHeight()/5;
		
		if(getRunway().isSmallEnd()){
			drawArrowAround(g, x, y, GOING_RIGHT, g.getColor(), Color.BLACK);
		}else{
			drawArrowAround(g, x, y, GOING_LEFT, g.getColor(), Color.BLACK);
		}
	}
	
	private void drawArrowAround(Graphics g, int pointX, int pointY, double angleInPi, Color fill, Color outline){
		Graphics2D g2 = (Graphics2D) g.create();
		int length = 70; int radius = 15;
		
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
	
	private void drawObstacle(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();

		PositionedObstacle obj = (PositionedObstacle) getField().getPositionedObstacle();
		if (obj==null) return;
		
		//distance is from the left hand side start of grey tarmac
		double distance;
		if (getRunway().isSmallEnd()){
			distance = getRunway().getDisplacedThreshold() + obj.distanceFromSmallEnd();
		}else{
			distance = defTora - getRunway().getDisplacedThreshold() - obj.distanceFromLargeEnd();
		}
		
		int x = (int) distance;//not scaled
		int y = getHeight()/2;
		
		//TODO figure out how that scales
		
		//=[ For box ]=
		// draw an 'X' at point
		int h = 4;//(int) (getField().getRunwayGirth()/2);//not scaled
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(x+h, y+h, x-h, y-h);
		g2.drawLine(x-h, y+h, x+h, y-h);
		//=[ planes ]=
		// draw cheeky plane
		
		//=[ generic ]=
		int radius = 25;//TODO make relative to the obs and SCALE
		int diamter = radius*2;
		g2.setStroke(new BasicStroke(0.75f));
		g2.setColor(Color.black);;
		g2.drawOval(x-radius, y-radius, diamter, diamter);
		//draw a circle of radius shaded //TODO get shaka's help for that
		
		
	}
	
	
	

}