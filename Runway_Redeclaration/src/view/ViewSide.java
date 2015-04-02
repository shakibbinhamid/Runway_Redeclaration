package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Core.Airport;
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

	public static void main(String[] args){
		try {
			JFrame f = new JFrame("Test");
			AirportInterface port = new Airport("Jim International");
			port.addNewAirfield(90, 'L', new double[] {3902,3902,3902,3596}, new double[] {3884,3884,3962,3884});
			
			AirfieldInterface air = port.getAirfield(port.getAirfieldNames().get(0));
			DeclaredRunwayInterface runway = air.getSmallAngledRunway();

			
			System.out.println(air);
			
			f.setContentPane(new ViewSide(air, runway));
			
			f.pack();
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize(300, 100);


		} catch (CannotMakeRunwayException | VariableDeclarationException e) {
		} catch (UnrecognisedAirfieldIntifierException e) {
		}
	}



	private static final long serialVersionUID = 1L;

	private AirfieldInterface airfield;
	private DeclaredRunwayInterface runway;

	private BufferedImage image;

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

		double topOfGrass = largestHeight() * PERCENTAGE_OF_SKY;
		double heightOfGrass = (1-PERCENTAGE_OF_SKY) * largestHeight();

		g2.setColor(GRASS_COLOUR);
		this.drawRectangle_inM(g2, new Point(0d, topOfGrass), new Point(width, topOfGrass+ heightOfGrass), GRASS_COLOUR);
		
		g2.setColor(SKY_COLOUR);
		this.drawRectangle_inM(g2, new Point(0d, 0d), new Point(width, topOfGrass), SKY_COLOUR);
	}

	private void drawRunway(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		double start = startOfRunway();
		double width = getRunway().getTORA();
		
		double y_m = largestHeight()*PERCENTAGE_OF_SKY;
		
		g2.setColor(RUNWAY_COLOUR);
		g2.setStroke(new BasicStroke(3));
		this.drawLine_inM(g2, new Point(start, y_m), 
							  new Point(start+width, y_m));
	}
	
	
	private void drawObsacle(Graphics2D g) {
		if(!getAirfield().hasObstacle()) return;
		
		Graphics2D g2 = (Graphics2D) g.create();

		
		

	}

	private void drawDistances(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();


	}

	
	//======[ Common Distance Methods ]=======
	@Override
	protected double largestHeight(){
		double largestHeight = getAirfield().getTotalHeight();
		if(largestHeight < 20)
			largestHeight = 20;
		return largestHeight;
	}
	
	private int startOfRunway(){
		return (int) (getAirfield().getTotalWidth()/2 - getRunway().getTORA()/2);
	}
	
	
	

}
