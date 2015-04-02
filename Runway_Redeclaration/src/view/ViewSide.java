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
public class ViewSide extends JPanel{

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


	private int IMAGE_WIDTH, IMAGE_HEIGHT;

	public static int HEIGHT_OF_RUNWAY = 1; /* in m */
	public static double PERCENTAGE_OF_SKY = 0.5; /* in % */

	public static Color GRASS_COLOUR = Color.green;
	public static Color SKY_COLOUR = Color.cyan;
	public static Color RUNWAY_COLOUR = Color.GRAY;
	
	
	public ViewSide(AirfieldInterface airfield, DeclaredRunwayInterface runway){
		setAirfield(airfield);
		setRunway(runway);

		this.IMAGE_WIDTH = this.IMAGE_HEIGHT = 10;
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	}

	public AirfieldInterface getAirfield(){ return this.airfield; }
	public void setAirfield(AirfieldInterface newAirfield){
		this.airfield = newAirfield;

		//TODO !IF NEEDED! add the special values
	}

	public DeclaredRunwayInterface getRunway(){ return this.runway; }
	public void setRunway(DeclaredRunwayInterface newRunway){
		this.runway = newRunway;

		//TODO !IF NEEDED! add the special values
	}




	//======[ Scaling ]=====================================================================================================
	private void rescaleImageSize(){
		this.IMAGE_WIDTH = this.getWidth();
		this.IMAGE_HEIGHT = this.getHeight();
		this.image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	}

	public int Xm_to_pixels(double xm){
		int largestWidth = (int) getAirfield().getTotalWidth();
		System.out.println("X: "+xm+"m   to  "+generalScale(largestWidth, IMAGE_WIDTH, xm)+"pixels");
		return generalScale(largestWidth, IMAGE_WIDTH, xm);
	}
	public int Ym_to_pixels(double ym){
		int largestHeight = largestHeight();
		
		System.out.println("Y: "+ym+"m   to  "+generalScale(largestHeight, IMAGE_HEIGHT, ym)+"pixels");
		return generalScale(largestHeight, IMAGE_HEIGHT, ym);
	} 
	
	private static int generalScale (double howMuchWantToFit, int inHowMuch, double whatYouAreScaling){
		BigDecimal value = new BigDecimal( whatYouAreScaling * inHowMuch/howMuchWantToFit);
		return value.intValue();
	}
	//===========================================================================================================================



	//======[ Drawing ]=====================================================================================================
	@Override
	public void paint(Graphics g){
		super.paint(g);
		rescaleImageSize();
		
		drawImage(this.image.createGraphics());

		/* @End */g.drawImage(this.image, 0, 0, null);
	}

	private void drawImage(Graphics2D g2) {
		// TODO Determine more sections

		drawBackground(g2);
		drawRunway(g2);
		drawDistances(g2);

		if(getAirfield().hasObstacle())
			drawObsacle(g2);
	}

	//----[ Generic Drawing ]---------------------------------------------------------------------
	private void drawLine_inM(Graphics2D g, double x1m, double y1m, double x2m, double y2m){
		Graphics2D g2 = (Graphics2D) g.create();
		int x1p,y1p,x2p,y2p;

		x1p = Xm_to_pixels(x1m);
		y1p = Ym_to_pixels(y1m);

		x2p = Xm_to_pixels(x2m);
		y2p = Ym_to_pixels(y2m);

		g2.drawLine(x1p, y1p, x2p, y2p);
}

	private void drawRectangle_inM(Graphics2D g, double xm, double ym, double width_m, double height_m, Color fill){
		Graphics2D g2 = (Graphics2D) g.create();
		int xp,yp,width_p,height_p;

		xp = Xm_to_pixels(xm);
		yp = Ym_to_pixels(ym);

		width_p = Xm_to_pixels(width_m);
		height_p = Ym_to_pixels(height_m);

		g2.drawRect(xp, yp, width_p, height_p);
		if(fill != null){
			System.out.println("Filling in");
			g2.setColor(fill);
			g2.fillRect(xp, yp, width_p, height_p);
		}
	}


	private void drawPolygon_inM(Graphics g, double[] xs_m, double[] ys_m, Color fill){
		Graphics2D g2 = (Graphics2D) g.create();
		int[] xs_p, ys_p;

		xs_p = new int[xs_m.length];
		for(int xi = 0; xi < xs_m.length; xi++){
			xs_p[xi] = Xm_to_pixels(xs_m[xi]);
		}

		ys_p = new int[ys_m.length];
		for(int yi = 0; yi < ys_m.length; yi++){
			ys_p[yi] = Ym_to_pixels(ys_m[yi]);
		}

		g2.drawPolygon(xs_p, ys_p, xs_p.length);
		if(fill != null){
			g2.setColor(fill);
			g2.fillPolygon(xs_p, ys_p, xs_p.length);
		}
	}

	//----[ Specific Distance ]-------------------------------------------------------------------

	/** Draw grass and blue sky */
	private void drawBackground(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		
		//The extra on each side in case of future panning and zooming
		int startX = 0 ;
		int width = (int) (getAirfield().getTotalWidth());

		int topOfGrass = (int) (largestHeight() * PERCENTAGE_OF_SKY);
		int heightOfGrass = (int) ((1-PERCENTAGE_OF_SKY) * largestHeight());

		g2.setColor(GRASS_COLOUR);
		this.drawRectangle_inM(g2, startX, topOfGrass , width, topOfGrass+ heightOfGrass , GRASS_COLOUR);
		
		g2.setColor(SKY_COLOUR);
		this.drawRectangle_inM(g2, startX, 0 , width, topOfGrass, SKY_COLOUR);
	}

	private void drawRunway(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		int start = startOfRunway();
		int width = (int) getRunway().getTORA();
		
		int y_m = (int) (largestHeight()*PERCENTAGE_OF_SKY);
		
		g2.setColor(RUNWAY_COLOUR);
		g2.setStroke(new BasicStroke(3));
		this.drawLine_inM(g2, start, y_m ,start+width, y_m);
	}
	
	
	private void drawObsacle(Graphics2D g) {
		if(!getAirfield().hasObstacle()) return;
		
		Graphics2D g2 = (Graphics2D) g.create();

		
		

	}

	private void drawDistances(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();


	}

	
	//======[ Common Distance Methods ]=======
	private int largestHeight(){
		int largestHeight = (int) getAirfield().getTotalHeight();
		if(largestHeight < 20)
			largestHeight = 20;
		return largestHeight;
	}
	
	private int startOfRunway(){
		return (int) (getAirfield().getTotalWidth()/2 - getRunway().getTORA()/2);
	}
	
	
	

}
