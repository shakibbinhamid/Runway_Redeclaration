package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import Core.Airport;
import Core.Obstacle;
import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.AirportInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import Exceptions.CannotMakeRunwayException;
import Exceptions.UnrecognisedAirfieldIntifierException;
import Exceptions.VariableDeclarationException;

/**
 * 
 * @author Stefan
 * @Editor Stefan Shakib
 */
public class ViewSide extends AbstractView{
	/**
	 * [X] Version 0: Nada Complete
	 * [X] Version 1: Points 
	 * [ ] Version 2: Rotate Points 
	 * [ ] Version 3: Scale/Zoom 
	 * [ ] Version 4: Pan by focus points 
	 */
	private static final long serialVersionUID = 1L;



	public static void main(String[] args){
		try {
			JFrame f = new JFrame("SideView Test");
			AirportInterface port = new Airport("Jim International");
			port.addNewAirfield(90, 'L', new double[] {3902,3902,3902,3596}, new double[] {3884,3884,3962,3884});

			AirfieldInterface air = port.getAirfield(port.getAirfieldNames().get(0));
			DeclaredRunwayInterface runway = air.getSmallAngledRunway();

			air.addObstacle(new Obstacle("Jim",5,13), 0, 3884);

			System.out.println(air);

			f.setContentPane(new ViewSide(air, runway));

			f.pack();
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize(600, 250);


		} catch (CannotMakeRunwayException | VariableDeclarationException e) {
		} catch (UnrecognisedAirfieldIntifierException e) {
		}
	}

	public static int HEIGHT_OF_RUNWAY = 1; /* in m */
	public static int PIXEL_BUFFER = 40;
	public static double PERCENTAGE_OF_SKY = 0.5; /* in % */
	public static double PERCENTAGE_AIR_SPACER = 1.0; /* in % */


	public final static Color SKY_COLOUR = Color.cyan;
	public final static Color RUNWAY_COLOUR = Color.GRAY;


	public ViewSide(AirfieldInterface airfield, DeclaredRunwayInterface runway){
		super(airfield, runway);

	}



	//======[ Drawing ]=====================================================================================================
	@Override
	protected void drawImage(Graphics2D g2) {
		drawBackground(g2);
		drawRunway(g2);
		drawDistances(g2);

		if(getAirfield().hasObstacle())
			drawObsacle(g2);
		
		drawScale(g2);
	}

	//----[ Specific Distance ]-------------------------------------------------------------------

	/** Draw grass and blue sky */
	private void drawBackground(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();

		//The extra on each side in case of future panning and zooming
		double width = getAirfield().getTotalWidth();

		double topOfGrass = vertToRunway();
		double heightOfGrass = (1-PERCENTAGE_OF_SKY) * largestHeight();

		g2.setColor(GRASS_COLOUR);
		super.drawRectangle_inM(g2, new Point(0d, topOfGrass), new Point(width, topOfGrass+ heightOfGrass), GRASS_COLOUR);

		g2.setColor(SKY_COLOUR);
		super.drawRectangle_inM(g2, new Point(0d, 0d), new Point(width, topOfGrass), SKY_COLOUR);

	}

	/** Grey Strip */
	private void drawRunway(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();

		double y_m = vertToRunway();

		g2.setColor(RUNWAY_COLOUR);
		g2.setStroke(new BasicStroke(3));
		super.drawLine_inM(g2, new Point(leftOfRunaway(), y_m), new Point(rightOfRunway(),y_m));


	}

	/** Red vertical with ALS gradient (if needed) and labels underneath */
	//TODO include text to the labels
	//TODO include a box making the radius on it
	private void drawObsacle(Graphics2D g) {
		if(!getAirfield().hasObstacle()) return;

		Graphics2D g2 = (Graphics2D) g.create();
		double oldDT;
		double locOfObs, heightOfObs;

		if(getRunway().isSmallEnd()){
			oldDT = getAirfield().getDefaultSmallAngledRunway().getDisplacedThreshold();
			locOfObs = leftOfRunaway() + oldDT + getAirfield().getPositionedObstacle().distanceFromSmallEnd();
		}else{
			oldDT = getAirfield().getDefaultLargeAngledRunway().getDisplacedThreshold();
			locOfObs = rightOfRunway() - oldDT - getAirfield().getPositionedObstacle().distanceFromLargeEnd();
		}

		heightOfObs = getAirfield().getPositionedObstacle().getHeight();

		Point topPoint = new Point(locOfObs, vertToRunway()-heightOfObs);

		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(2));
		//Vert red line
		super.drawLine_inM(g2, new Point(locOfObs, vertToRunway()), topPoint);

		//The horiz bit at the top
		Point leftTop = topPoint.offsetXByPixels(-5);
		Point rightTop = topPoint.offsetXByPixels(5);
		super.drawLine_inM(g2, leftTop, rightTop);

		//draws ALS/Blast/RESA Beneath
		drawLargestFactor(g,locOfObs,heightOfObs);
	}

	protected void drawLargestFactor(Graphics2D g, double locOfObs, double heightOfObs){
		double ALS = getRunway().getDescentAngle()*getAirfield().getPositionedObstacle().getHeight();
		double SE = getAirfield().getStripEnd();

		//find largest factor
		if(getRunway().getRESA()+SE > ALS+SE &&  ALS+SE > getAirfield().getBlastAllowance()){
			drawRESA(g, locOfObs, heightOfObs);

		}else if(ALS > getAirfield().getBlastAllowance()){
			drawALS(g, locOfObs, heightOfObs, ALS);

		}else{
			drawBlastProt(g, locOfObs);
		}
	}
	
	private void drawALS(Graphics2D g, double locOfObs, double heightOfObs, double ALS){
		Graphics2D g2 = (Graphics2D) g.create();
		Graphics2D g3 = (Graphics2D) g.create();

		Point obsTop = new Point(locOfObs,vertToRunway()-heightOfObs);
		Point alsPoint = new Point(locOfObs+s()*ALS,this.vertToRunway());

		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(1f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL,
				10.0f, new float[]{5}, 3.0f));

		super.drawLine_inM(g2, obsTop, alsPoint);

		Point start = new Point(locOfObs,vertToRunway()).offsetYByPixels(PIXEL_BUFFER);
		Point end = start.offsetXByM(s()*ALS);

		g3.setColor(MAROON_COLOUR);
		g3.setStroke(new BasicStroke(1f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL,
				10.0f, new float[]{2}, 0.5f));
		//Horiz line
		super.drawLine_inM(g3, start, end);

		//Verticals
		super.drawLine_inM(g3, start.offsetYByPixels(PIXEL_BUFFER/2), start.offsetYByPixels(-PIXEL_BUFFER));
		super.drawLine_inM(g3, end.offsetYByPixels(PIXEL_BUFFER/2), end.offsetYByPixels(-PIXEL_BUFFER));

		//Strip End
		Point endSE = end.offsetXByM(s()*getAirfield().getStripEnd());

		super.drawLine_inM(g3, end, endSE);

		super.drawLine_inM(g3, end.offsetYByPixels(PIXEL_BUFFER/2), end.offsetYByPixels(-PIXEL_BUFFER));
		super.drawLine_inM(g3, endSE.offsetYByPixels(PIXEL_BUFFER/2), endSE.offsetYByPixels(-PIXEL_BUFFER));
	}

	private void drawRESA(Graphics2D g, double locOfObs, double RESA){
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(MAROON_COLOUR);
		g2.setStroke(new BasicStroke(1f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL,
				10.0f, new float[]{2}, 0.5f));

		Point start = new Point(locOfObs,vertToRunway()).offsetYByPixels(PIXEL_BUFFER);
		Point end = start.offsetXByM(s()*RESA);
		//horiz
		super.drawLine_inM(g2, start, end);
		//Verticals
		super.drawLine_inM(g2, start.offsetYByPixels(PIXEL_BUFFER/2), start.offsetYByPixels(-PIXEL_BUFFER));
		super.drawLine_inM(g2, end.offsetYByPixels(PIXEL_BUFFER/2), end.offsetYByPixels(-PIXEL_BUFFER));

		//Strip End
		Point endSE = end.offsetXByM(s()*getAirfield().getStripEnd());

		super.drawLine_inM(g2, end, endSE);

		super.drawLine_inM(g2, end.offsetYByPixels(PIXEL_BUFFER/2), end.offsetYByPixels(-PIXEL_BUFFER));
		super.drawLine_inM(g2, endSE.offsetYByPixels(PIXEL_BUFFER/2), endSE.offsetYByPixels(-PIXEL_BUFFER));



	}

	private void drawBlastProt(Graphics2D g, double locOfObs){
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(MAROON_COLOUR);
		g2.setStroke(new BasicStroke(1f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL,
				10.0f, new float[]{2}, 0.5f));
		
		Point start = new Point(locOfObs,vertToRunway()).offsetYByPixels(PIXEL_BUFFER);
		Point end = start.offsetXByM(s()*getAirfield().getBlastAllowance());

		//horiz
		super.drawLine_inM(g2, start, end);

		//Verticals
		super.drawLine_inM(g2, start.offsetYByPixels(PIXEL_BUFFER/2), start.offsetYByPixels(-PIXEL_BUFFER));
		super.drawLine_inM(g2, end.offsetYByPixels(PIXEL_BUFFER/2), end.offsetYByPixels(-PIXEL_BUFFER));

	}
	
	/** Draws the TORA,ASDA,TODA,LDA */
	private void drawDistances(Graphics2D g) {
		int level = 2; 
		drawDistance(g, "LDA", getRunway().getLDA(), getRunway().getDisplacedThreshold(), level++);
		drawDistance(g, "TORA", getRunway().getTORA(), getRunway().getStartOfRoll(), level++);
		drawDistance(g, "ASDA", getRunway().getASDA(), getRunway().getStartOfRoll(), level++);
		drawDistance(g, "TODA", getRunway().getTODA(), getRunway().getStartOfRoll(), level++);
	}

	private void drawDistance(Graphics2D g, String disName, double length, double begin, int heightLevel){
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(Color.black);
		
		FontMetrics fontMetrics = g2.getFontMetrics();
		int titleLen = fontMetrics.stringWidth(disName);
		System.out.println(titleLen);

		double startX;
		if(getRunway().isSmallEnd()){
			startX = leftOfRunaway() + begin;
		}else{
			startX = rightOfRunway() - begin; 
		}

		double endX = startX+s()*length;
		int pixelOffset = heightLevel*PIXEL_BUFFER;

		Point start = new Point(startX,vertToRunway()).offsetYByPixels(pixelOffset);
		Point end = new Point(endX,vertToRunway()).offsetYByPixels(pixelOffset);

		//These points make the gap for the text
		Point midStart = start.offsetXByM(s()*length/2).offsetXByPixels(s()*-3*titleLen/4);
		Point midEnd   = start.offsetXByM(s()*length/2).offsetXByPixels(s()* 3*titleLen/4);
		
		//Horiz
		super.drawLine_inM(g2, start, midStart);
		super.drawLine_inM(g2, end, midEnd);

		//Verticals
		super.drawLine_inM(g2, start.offsetYByPixels(PIXEL_BUFFER/2), start.offsetYByPixels(-pixelOffset));
		super.drawLine_inM(g2, end.offsetYByPixels(PIXEL_BUFFER/2), end.offsetYByPixels(-pixelOffset));
		
		
		//Placing the text on the left of the two mid points
		Point pWithSmallerX;
		if(midStart.x_m()<midEnd.x_m()){
			pWithSmallerX = midStart;
		}else{
			pWithSmallerX = midEnd;
		}
		super.drawString_inM(g2, disName, pWithSmallerX.offsetXByPixels(titleLen/4));
	}

	/** The line that says how big the image is  */
	private void drawScale(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}
	
	//======[ Common Distance Methods ]=======
	@Override
	protected double largestHeight(){
		double highestPoint = getAirfield().getTotalHeight();
		if(highestPoint < 20) highestPoint = 20;
		double airSpacer = highestPoint*PERCENTAGE_AIR_SPACER;

		double skyHeight = highestPoint + airSpacer;

		double totalHeight = skyHeight / PERCENTAGE_OF_SKY;
		return totalHeight;
	}

	private double leftOfRunaway(){
		if(getRunway().isSmallEnd()){
			return getAirfield().getStripEnd();			
		}else{
			return super.runwayWidth()-getAirfield().getStripEnd()-getAirfield().getDefaultLargeAngledRunway().getTORA();
		}
	}
	private double rightOfRunway(){
		if(getRunway().isSmallEnd()){
			return getAirfield().getStripEnd()+getAirfield().getDefaultSmallAngledRunway().getTORA();			
		}else{
			return super.runwayWidth()-getAirfield().getStripEnd();
		}
	}

	private double vertToRunway(){
		return largestHeight() * PERCENTAGE_OF_SKY;
	}


}
