package view;

import java.awt.BasicStroke;
import java.awt.Color;
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
			JFrame f = new JFrame("Test");
			AirportInterface port = new Airport("Jim International");
			port.addNewAirfield(90, 'L', new double[] {3902,3902,3902,3596}, new double[] {3884,3884,3962,3884});

			AirfieldInterface air = port.getAirfield(port.getAirfieldNames().get(0));
			DeclaredRunwayInterface runway = air.getSmallAngledRunway();

			air.addObstacle(new Obstacle("Jim",5,13), 50, 3500);

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
	public static double PERCENTAGE_OF_SKY = 0.5; /* in % */

	public final static Color SKY_COLOUR = Color.cyan;
	public final static Color RUNWAY_COLOUR = Color.GRAY;


	public ViewSide(AirfieldInterface airfield, DeclaredRunwayInterface runway){
		super(airfield, runway);

	}



	//======[ Drawing ]=====================================================================================================
	@Override
	protected void drawImage(Graphics2D g2) {
		// TODO Determine more sections

		drawBackground(g2);
		drawRunway(g2);
		drawDistances(g2);

		if(getAirfield().hasObstacle())
			drawObsacle(g2);
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
		this.drawRectangle_inM(g2, new Point(0d, topOfGrass), new Point(width, topOfGrass+ heightOfGrass), GRASS_COLOUR);

		g2.setColor(SKY_COLOUR);
		this.drawRectangle_inM(g2, new Point(0d, 0d), new Point(width, topOfGrass), SKY_COLOUR);
	}

	/** Grey Strip */
	private void drawRunway(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		double start = startOfRunway();
		double width = getRunway().getTORA();

		double y_m = vertToRunway();

		g2.setColor(RUNWAY_COLOUR);
		g2.setStroke(new BasicStroke(3));
		this.drawLine_inM(g2, new Point(start, y_m), 
				new Point(start+width, y_m));
	}


	private void drawObsacle(Graphics2D g) {
		if(!getAirfield().hasObstacle()) return;

		Graphics2D g2 = (Graphics2D) g.create();

		double DT = getRunway().getDisplacedThreshold();

		double locOfObs;
		double heightOfObs;

		if(getRunway().isSmallEnd()){
			locOfObs = DT + getAirfield().getPositionedObstacle().distanceFromSmallEnd();
		}else{
			double farSide = startOfRunway()+getRunway().getTORA();
			locOfObs = farSide - DT - getAirfield().getPositionedObstacle().distanceFromLargeEnd();
		}

		heightOfObs = getAirfield().getPositionedObstacle().getHeight();

		Point topPoint = new Point(locOfObs, vertToRunway()-heightOfObs);

		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(2));
		super.drawLine_inM(g2, new Point(locOfObs, vertToRunway()), 
				topPoint);

		Point leftTop = topPoint.offsetXByPixels(-5);
		Point rightTop = topPoint.offsetXByPixels(5);

		super.drawLine_inM(g2, leftTop, rightTop);

		drawLargestFactor(g,locOfObs,vertToRunway()-heightOfObs);
	}

	protected void drawLargestFactor(Graphics2D g, double locOfObs, double topOfObs){
		Graphics2D g2 = (Graphics2D) g.create();


		double largestFactor; 
		String factorName;

		double ALS = getRunway().getAscentAngle()*getAirfield().getPositionedObstacle().getHeight();
		double SE = getAirfield().getStripEnd();

		//find largest factor
		if(getRunway().getRESA()+SE > ALS+SE &&  ALS+SE > getAirfield().getBlastAllowance()){
			drawRESA(g, locOfObs, topOfObs);

		}else if(ALS > getAirfield().getBlastAllowance()){
			drawALS(g, locOfObs, topOfObs, ALS);

		}else{
			drawBlastProt(g, locOfObs, topOfObs);
		}
	}

	private void drawALS(Graphics2D g, double locOfObs, double topOfObs, double ALS){
		Graphics2D g2 = (Graphics2D) g.create();

		Point obsTop = new Point(locOfObs,topOfObs);
		Point alsPoint = new Point(locOfObs+ALS,this.vertToRunway());

		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(1f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL,
				10.0f, new float[]{5}, 3.0f));

		super.drawLine_inM(g2, obsTop, alsPoint);

		//TODO draw ALS Underneath
		//TODO draw strip end underneath
	}

	private void drawRESA(Graphics2D g, double locOfObs, double topOfObs){
		//TODO draw RESA underneath
		//TODO draw Strip end underneath
	}

	private void drawBlastProt(Graphics2D g, double locOfObs, double topOfObs){
		//TODO draw blast prot underneath
	}

	private void drawDistances(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		double heightOfObs = getAirfield().getPositionedObstacle().getHeight();



	}


	//======[ Common Distance Methods ]=======
	@Override
	protected double largestHeight(){
		double highestPoint = getAirfield().getTotalHeight();
		double airSpacer = highestPoint/4;

		double skyHeight = highestPoint + airSpacer;

		double totalHeight = skyHeight / PERCENTAGE_OF_SKY;
		return totalHeight;
	}

	private double startOfRunway(){
		return getAirfield().getTotalWidth()/2 - getRunway().getTORA()/2;
	}

	private double vertToRunway(){
		return largestHeight() * PERCENTAGE_OF_SKY;
	}


}
