package view.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import core.interfaces.AirfieldInterface;
import core.interfaces.DeclaredRunwayInterface;

/**
 * 
 * @author Stefan
 * @Editor Stefan Shakib-Bin Hamid
 */
public class ViewSide extends AbstractView{
	/**
	 * [X] Version 0: Nada Complete
	 * [X] Version 1: Points 
	 * [X] Version 2: Rotate Points 
	 * [ ] Version 3: Scale/Zoom 
	 * [ ] Version 4: Pan by focus points 
	 */
	private static final long serialVersionUID = 2L;

	public static int HEIGHT_OF_RUNWAY = 1; /* in m */
	public static final double PERCENTAGE_OF_SKY = 0.5; /* in % */
	public static double PERCENTAGE_AIR_SPACER = 1.0; /* in % */
	

	public ViewSide(AirfieldInterface airfield, DeclaredRunwayInterface runway){
		super(airfield, runway, "Side View", false, false);
	}

	protected double init_LEFT_SIDE_BUFFER_M(){ return 0d; }
	protected double init_RIGHT_SIDE_BUFFER_M(){ return 0d; }
	protected double init_TOP_SIDE_BUFFER_M(){ return 0d; }
	protected double init_BOTTOM_SIDE_BUFFER_M(){ return 0d; }

	//======[ Drawing ]=====================================================================================================
	@Override
	protected void drawImage(Graphics2D g2) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		
		g2.setRenderingHints(rh);
		
		drawBackground(g2);
		drawRunway(g2);
		drawClearwayAndStopway(g2);
		drawDistances(g2);

		if(getAirfield().hasObstacle())
			drawObsacle(g2);

		drawThesholds(g2);
		drawIdentifiers(g2,vertToRunway()/2,leftOfRunway(),rightOfRunway(),false);
		drawFatArrow(g2);
	}

	//----[ Specific Distance ]-------------------------------------------------------------------

	/** Draw grass and blue sky */
	private void drawBackground(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();

		//The extra on each side in case of future panning and zooming
		int topOfGrass, heightOfGrass, heightOfSky;
		heightOfSky = topOfGrass = (int) (PERCENTAGE_OF_SKY * getImage().getHeight());
		heightOfGrass = (int) ((1-PERCENTAGE_OF_SKY) * getImage().getHeight());

		//We draw in raw pixels so that the grass is unaffected by rotation
		g2.setColor(SKY_COLOUR);
		g2.fillRect(0, 0, getImage().getWidth(), heightOfSky);
		g2.drawRect(0, 0, getImage().getWidth(), heightOfSky);

		g2.setColor(GRASS_COLOUR);
		g2.fillRect(0, topOfGrass, getImage().getWidth(), heightOfGrass);
		g2.drawRect(0, topOfGrass, getImage().getWidth(), heightOfGrass);
	}

	/** Grey Strip */
	private void drawRunway(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();

		double y_m = vertToRunway();

		g2.setColor(RUNWAY_COLOUR);
		g2.setStroke(new BasicStroke(3));
		super.drawLine_inM(g2, new Point(leftOfRunway(), y_m), new Point(rightOfRunway(),y_m));
	}

	/** Red vertical with ALS gradient (if needed) and labels underneath */
	private void drawObsacle(Graphics2D g) {
		if(!getAirfield().hasObstacle()) return;

		Graphics2D g2 = (Graphics2D) g.create();
		double oldDT;
		double locOfObs, heightOfObs;

		if(getRunway().isSmallEnd()){
			oldDT = getAirfield().getDefaultSmallAngledRunway().getDisplacedThreshold();
			locOfObs = leftOfRunway() + oldDT + getAirfield().getPositionedObstacle().distanceFromSmallEnd();
		}else{
			oldDT = getAirfield().getDefaultLargeAngledRunway().getDisplacedThreshold();
			locOfObs = rightOfRunway() - oldDT - getAirfield().getPositionedObstacle().distanceFromLargeEnd();
		}

		heightOfObs = getAirfield().getPositionedObstacle().getHeight();

		Point topPoint = new Point(locOfObs, vertToRunway()-heightOfObs);

		//draw a box around the object
		double radius = getAirfield().getPositionedObstacle().getRadius();
		Point topLeft = topPoint.offsetXByM(-radius);
		g2.setColor(OBSTACLE_RADIUS_COLOR);
		super.drawRectangle_inM(g2, topLeft, radius*2, heightOfObs, getTransparant(g2.getColor(),100));
		
		
		//Vert red line
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(2));
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

		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(1f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL,
				10.0f, new float[]{5}, 3.0f));

		g3.setColor(MAROON_COLOUR);
		g3.setStroke(new BasicStroke(1f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL,
				10.0f, new float[]{2}, 0.5f));
		
		//if the object is off the runway then the ALS slop needs to point inwards!
		//This handles that!
		int o = 1;
		if(getRunway().isSmallEnd() 
				&& 
				getAirfield().getPositionedObstacle().distanceFromSmallEnd() > getAirfield().getPositionedObstacle().distanceFromLargeEnd()){
			//Then the obs is off the right hand side
			o = -1;
			
		}else if(!getRunway().isSmallEnd()
				&&
				getAirfield().getPositionedObstacle().distanceFromSmallEnd() < getAirfield().getPositionedObstacle().distanceFromLargeEnd()){
			//Then the obs is off the left hand side
			o = -1;
		}

		Point obsTop = new Point(locOfObs,vertToRunway()-heightOfObs);
		Point alsPoint = new Point(locOfObs+o*s()*ALS,this.vertToRunway());
		Point alsPointOther = new Point(locOfObs-o*s()*ALS,this.vertToRunway());

		super.drawLine_inM(g2, obsTop, alsPoint);
		super.drawLine_inM(g2, obsTop, alsPointOther);

		Point start = new Point(locOfObs,vertToRunway()).offsetYByPixels(PIXEL_BUFFER);
		Point end = start.offsetXByM(o*s()*ALS);

		//Shaded Triangle
		g2.setColor(GLASS_COLOR);
		super.drawPolygon_inM(g2, new Point[] {obsTop,new Point(locOfObs,vertToRunway()),alsPoint}, ALS_SHADE_COLOR);
		super.drawPolygon_inM(g2, new Point[] {obsTop,new Point(locOfObs,vertToRunway()),alsPointOther}, ALS_SHADE_COLOR);

		//Horiz line bellow
		super.drawLine_inM(g3, start, end);

		//Verticals
		super.drawLine_inM(g3, start.offsetYByPixels(PIXEL_BUFFER/2), start.offsetYByPixels(-PIXEL_BUFFER));
		super.drawLine_inM(g3, end.offsetYByPixels(PIXEL_BUFFER/2), end.offsetYByPixels(-PIXEL_BUFFER));

		//Strip End
		Point endSE = end.offsetXByM(o*s()*getAirfield().getStripEnd());

		super.drawLine_inM(g3, end, endSE);

		super.drawLine_inM(g3, end.offsetYByPixels(PIXEL_BUFFER/2), end.offsetYByPixels(-PIXEL_BUFFER));
		super.drawLine_inM(g3, endSE.offsetYByPixels(PIXEL_BUFFER/2), endSE.offsetYByPixels(-PIXEL_BUFFER));

		//Text
		Point textStart = start.offsetYByPixels(PIXEL_BUFFER/2).offsetXByPixels(2);
		super.drawString_inM(g3, "ALS", textStart);

		//Ratio Text
		Graphics2D g4 = (Graphics2D) g.create();
		g4.setColor(DIMENSION_COLOR);
		g4.setFont(DISTANCE_FONT);

		Point midDiagonal = new Point(locOfObs+o*s()*ALS/2, vertToRunway()-heightOfObs/2).offsetYByPixels(-10);
		super.drawString_inM(g4, "1:"+(int) getRunway().getDescentAngle() , midDiagonal);
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

		//Text
		Point textStart = start.offsetYByPixels(PIXEL_BUFFER/2).offsetXByPixels(2);
		super.drawString_inM(g2, "ALS", textStart);
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

		//Text
		Point textStart = start.offsetYByPixels(PIXEL_BUFFER/2).offsetXByPixels(2);
		super.drawString_inM(g2, "Blast Prot", textStart);
	}

	/** Draws the TORA,ASDA,TODA,LDA */
	private void drawDistances(Graphics2D g) {
		int level = 1;
		drawDistance(g, "LDA", getRunway().getLDA(), getRunway().getDisplacedThreshold(), level++, vertToRunway());
		drawDistance(g, "TORA", getRunway().getTORA(), getRunway().getStartOfRoll(), level++, vertToRunway());
		drawDistance(g, "ASDA", getRunway().getASDA(), getRunway().getStartOfRoll(), level++, vertToRunway());
		drawDistance(g, "TODA", getRunway().getTODA(), getRunway().getStartOfRoll(), level++, vertToRunway());
	}

	private void drawThesholds(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		Point leftThreshold = new Point(this.leftOfRunway()+getAirfield().getSmallAngledRunway().getDisplacedThreshold(),vertToRunway());
		Point rightThreshold = new Point(this.rightOfRunway()-getAirfield().getLargeAngledRunway().getDisplacedThreshold(),vertToRunway());

		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.RED);
		super.drawLine_inM(g2, leftThreshold, leftThreshold);
		super.drawLine_inM(g2, rightThreshold, rightThreshold);
	}
	
	private void drawFatArrow(Graphics2D g){
		double fullHeight = vertToRunway()/PERCENTAGE_OF_SKY;
		double width = runwayWidth()/10;
		double startX =  runwayWidth()/2+width/2;
		
		double grassTallness = fullHeight*(1-PERCENTAGE_OF_SKY);
		double remainingGrass = grassTallness-Ypix_to_m(PIXEL_BUFFER*5);
		double startY = fullHeight-remainingGrass/2;
		
		
		super.drawArrowAround(g, new Point(startX, startY), width, !getRunway().isSmallEnd(), Color.RED, MAROON_COLOUR);
	}
	
	private void drawClearwayAndStopway(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();

		Point start;

		if(getRunway().isSmallEnd()){
			start = new Point(rightOfRunway(),vertToRunway());
		}else{
			start = new Point(leftOfRunway(),vertToRunway());
		}

		if(getRunway().getClearway()>0){

			g2.setColor(CLEARWAY_COLOR);
			super.drawRectangle_inM(g2, start, s()*getRunway().getClearway(),-2*largestHeight()/40 , getTransparant(g2.getColor(), 200));

			if(getRunway().getStopway()>0){
				g2.setColor(STOPWAY_COLOR);
				super.drawRectangle_inM(g2, start,s()*getRunway().getStopway(),-largestHeight()/40, getTransparant(g2.getColor(), 150));
			}
		}
	}

	//======[ Common Distance Methods ]===================================================================
	@Override
	protected double largestHeight(){
		double highestPoint = getAirfield().getTotalHeight();
		if(highestPoint < 20) highestPoint = 20;
		double airSpacer = highestPoint*PERCENTAGE_AIR_SPACER;

		double skyHeight = highestPoint + airSpacer;

		double totalHeight = skyHeight / PERCENTAGE_OF_SKY;
		return totalHeight;
	}
	
	@Override
	protected double leftOfRunway(){
		if(getRunway().isSmallEnd()){
			return getAirfield().getStripEnd();			
		}else{
			return super.runwayWidth()-getAirfield().getStripEnd()-getAirfield().getDefaultLargeAngledRunway().getTORA();
		}
	}
	
	@Override
	protected double rightOfRunway(){
		if(getRunway().isSmallEnd()){
			return getAirfield().getStripEnd()+getAirfield().getDefaultSmallAngledRunway().getTORA();			
		}else{
			return super.runwayWidth()-getAirfield().getStripEnd();
		}
	}
	
	@Override
	protected double vertToRunway(){
		return largestHeight() * PERCENTAGE_OF_SKY;
	}

	@Override
	protected int[] getScaleLocation() {
		int middleX = getSUB_IMAGE_WIDTH()/2-Xm_to_pixels(metresToScale())/2;
		return new int[]{middleX,10};
	}

	@Override
	protected boolean doesScalePointDown() {
		return true;
	}

	@Override
	protected double metresToScale() {
		return 1000d;
	}

}
