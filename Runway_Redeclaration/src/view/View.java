package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;


/**
 * Preferred Size : 505 wide 275 tall
 * @author Shakib
 *
 */
public class View extends JPanel{

	private AirfieldInterface field;
	private DeclaredRunwayInterface run;
	private double b, h, dt1, dt2, s1, s3, s4, s5, s6, s7;
	private String s, ss;
	private double x1, x2;

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

	private static final int ARR_SIZE = 4;

	public View(AirfieldInterface field, DeclaredRunwayInterface run){
		//this.setField(field);
		//this.setRun(run);
	}

	public AirfieldInterface getField() {
		return field;
	}

	public void setField(AirfieldInterface field) {
		this.field = field;
		h = field.getRunwayGirth();
		dt1 = field.getSmallAngledRunway().getDisplacedThreshold();
		dt2 = field.getLargeAngledRunway().getDisplacedThreshold();
		s1 = field.getStripEnd();
		s3 = field.getLongSpacer();
		s4 = field.getShortSpacer();
		s5 = field.getMediumSpacer();
		s6 = field.getShortLength();
		s7 = field.getLongLength();

		s = field.getSmallAngledRunway().getIdentifier();
		ss = field.getLargeAngledRunway().getIdentifier();
	}

	public DeclaredRunwayInterface getRun() {
		return run;
	}

	public void setRun(DeclaredRunwayInterface run) {
		this.run = run;
		b = run.getTORA();
		x1 = b + 2*s1 + SPACE_ON_BOTH_SIDES;
	}

	public static void main(String[] s){

		//AirfieldInterface field = new Airfield();

		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				JPanel pane = new View(null, null);

				JFrame frame = new JFrame();
				frame.setMinimumSize(new Dimension (500,300));
				frame.setContentPane(pane);

				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

				pane.repaint();
			}});
	}

	public void paint(Graphics g){
		int x = this.getWidth();

		doDrawing(g, 3902+120, x, "09L" , "27R", 3902, 110, 3902, 3902, 3596, 306, 0, 60, 150, 75, 105, 150, 300, 0);
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

	private void doDrawing(Graphics g, int x1, int x2, String s, String ss, int tora, int girth, int toda, int asda, int lda, int smalldt, int largedt, int s1, int s3, int s4, int s5, int s6, int s7, int startOfRoll) {
		System.out.println(this.getWidth()+","+this.getHeight());
		tora = scaleToPixels(x1, x2, tora);
		girth = scaleToPixels(x1, x2, girth);
		smalldt = scaleToPixels(x1, x2, smalldt);
		largedt = scaleToPixels(x1, x2, largedt);
		s1 = scaleToPixels(x1, x2, s1);
		s3 = scaleToPixels(x1, x2, s3);
		s4 = scaleToPixels(x1, x2, s4);
		s5 = scaleToPixels(x1, x2, s5);
		s6 = scaleToPixels(x1, x2, s6);
		s7 = scaleToPixels(x1, x2, s7);

		toda = scaleToPixels(x1, x2, toda);
		asda = scaleToPixels(x1, x2, asda);
		lda = scaleToPixels(x1, x2, lda);

		colorFrame(getGraphicsComp(g, grass));
		drawWholeArea(getGraphicsComp(g, purple), tora, s1, s3);
		drawClearedAndGradedArea(getGraphicsComp(g, clearedBlue), tora, s1, s4, s5, s6, s7);
		drawMainRunwayRect(getGraphicsComp(g, Color.gray), tora, girth);
		drawdtLines(getGraphicsComp(g, Color.red), tora, girth, smalldt, largedt);
		drawBarCode(getGraphicsComp(g, Color.white), tora, girth, smalldt, largedt);
		drawCenterLine(getGraphicsComp(g, Color.white), tora, girth, smalldt, largedt);
		drawIdentifier(getGraphicsComp(g, Color.white), s, ss, tora, girth, smalldt, largedt);

		drawAllDim(getGraphicsComp(g, Color.black), tora, girth, toda, asda, lda, smalldt, startOfRoll);
	}

	private int scaleToPixels (int howMuchWantToFit, int inHowMuch, int whatYouAreScaling){
		BigDecimal value = new BigDecimal( whatYouAreScaling * inHowMuch/howMuchWantToFit);
		return value.intValue();
	}

	private void colorFrame(Graphics g){
		g.create().fillRect(0, 0, this.getWidth(), this.getHeight());
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

	private void drawStopandClear(Graphics g, int tora){

	}

	/** Draws all virtual dimensions */
	private void drawAllDim(Graphics g, int tora, int girth, int toda, int asda, int lda, int dt, int startOfRoll){
		int height = girth/2;
		int bumper = 10;
		drawdim(g, tora, girth, "LDA", dt, lda, height + bumper);
		drawdim(g, tora, girth, "TORA", startOfRoll, tora, height + bumper + 1*VIRTUAL_GAP);
		drawdim(g, tora, girth, "ASDA", startOfRoll, asda, height + bumper + 2*VIRTUAL_GAP);
		drawdim(g, tora, girth, "TODA", startOfRoll, toda, height + bumper + 3*VIRTUAL_GAP);

	}

	/** Draws the arrows and labels for the virtual distances  */
	private void drawdim(Graphics g, int tora, int girth, String variableName, int startWhere, int howlong, int howhighUp){
		Graphics2D g2 = (Graphics2D) g.create();
		Graphics2D g3 = (Graphics2D) g.create();

		int textGap = 10*variableName.length();

		//first line vals
		int endX = getWidth()/2 - tora/2 + startWhere;
		int startX = this.getWidth()/2 ;
		int Y = getHeight()/2 - girth/2 - howhighUp;

		drawArrow(g, startX, Y, endX, Y);

		//vertical line to edge of runway
		g2.setStroke(new BasicStroke(0.75f));
		g2.drawLine(endX, Y, endX, getHeight()/2-girth/2);


		//text vals
		g3.setFont(new Font("verdana", Font.PLAIN, 9));
		//48 is for more evenly distributing the text
		g3.drawString(variableName, startX + startX/48, Y+3);

		//Second line vals
		endX += howlong;
		startX += textGap + startX/48;

		drawArrow(g, startX, Y, endX, Y);

		//vertical line to edge of runway
		g2.drawLine(endX, Y, endX, getHeight()/2-girth/2);
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

}