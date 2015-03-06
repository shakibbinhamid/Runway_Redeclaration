package Core;

import org.simpleframework.xml.Element;

import view.Notification;
import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.PositionedObstacleInterface;
import Exceptions.VariableDeclarationException;

/**
 * When an airfield is made, the air traffic controller will declare a 'runway' which the planes will use to
 * take off and land on. This is a region of lets say tarmac that will be used. Any time an obstacle is on the 
 * tarmac strip the runway needs to be redeclared. This is creating a new RedeclaredRunway rather than mutating it.
 * 
 * It consists of 4 main attributes: 
 * 	-TORA
 *  -ASDA
 *  -TODA
 *  -LDA
 *  
 * We call them inert as they are not dependent on other variables once declared
 *  All other attributes are calculated and not inert, as they depend on these 4 variables.
 *  
 * @author Shakib
 * @Editor Stefan Shakib
 * @Tester Stefan
 *
 */
public class DeclaredRunway implements DeclaredRunwayInterface{
	/** Ratio Format 1:n NOT DEGREES*/
	public static final double DEFAULT_DESC_RATIO = 50;
	/** Ratio Format 1:n NOT DEGREES*/
	public static final double DEFAULT_ASC_RATIO = 50;
	
	public static final double DEFAULT_RESA = 240;
	
	@Element 
	public String log; //TODO Somehow hide from element
	@Element
	private double decTora, decLda, decAsda, decToda, resa;
	
	@Element
	private int angle;
	@Element
	private char direction;

	//nullary constructor for xml
	protected DeclaredRunway(){}
	
	protected DeclaredRunway(AirfieldInterface airfield, int angleFromNorth,
			double tora, double asda, double toda, double lda) throws VariableDeclarationException{
		this.log = "-[Log]-\n";

		setTORA(tora);
		setASDA(asda);
		setTODA(toda);
		setLDA(lda);
		
		setRESA(DEFAULT_RESA);

		
		if(!(getTORA() <= getASDA() && getASDA() <= getTODA())) //&& this.decAsda <= this.decToda)) 
			throw new VariableDeclarationException("TORA, ASDA, TODA", new double[] {decTora,decAsda,decToda}, "TORA <= ASDA <= TODA");
		

		setAngle(angleFromNorth);
		this.direction = ' ';

	}
	//====[ Direction Methods  ]=====================================
		//----[ Getters ]------------------------------------------------------
		@Override
		public char getSideLetter() {
			return direction;
		}

		@Override
		/**
		 * The angle will be in actual degrees i.e. 270 or 51 
		 * Note: they are not the shortened 2sf version used in the identifier 
		 */
		public int getAngle() {
			return angle;
		}

		@Override
		/**
		 * The identifier consists of the angle rounded to the 
		 * nearest 10 and divided by 10 followed by the its parallel 
		 * runway side character.
		 * 
		 * e.g. 289 on the left side = "29L"
		 */
		public String getIdentifier() {
			String out = "";
			if(angle<100){
				out += "0";
			}
			out += String.valueOf(Math.round((double)angle/10))+direction;
			return out;
		}
		//----[ Setters ]---------------------------------------------------s---
		@Override
		/**
		 * The character that identifies a runway from a group of parallel,
		 *  the valid option for a parallel runway is 'L', 'R' or 'C'
		 *  If the runway is not parallel to any other runways then it must be ' ' i.e. empty space or null char
		 *  
		 *  @throws VariableDeclarationException - When the option is not one of the above options
		 */
		public void setSideLetter(char leftOrRight) throws VariableDeclarationException{
			//If LoR is not L or R
			if ( !(leftOrRight == ' ' || leftOrRight == 'L' || leftOrRight == 'R' || leftOrRight == 'C') )
				throw new VariableDeclarationException("Direction",leftOrRight,"Must be 'L', 'C', 'R' or space ");

			this.direction = leftOrRight;
		}

		/**
		 * Accepts any angle of degree but stores it as a value of v where:  0 <= v < 360
		 */
		public void setAngle(int angle) {
			while(angle<0){
				angle += 360;
			}
			this.angle = angle%360;
		}
		
		//====[ Inert Distance Methods  ]=====================================
		//----[ Getters ]------------------------------------------------------
		@Override
		public double getTORA() {
			return decTora;
		}
		@Override
		public double getTODA()  {
			return this.decToda;
		}
		@Override
		public double getASDA()  {
			return this.decAsda;
		}
		@Override
		public double getLDA()   {
			return this.decLda;
		}
		
		//----[ Setters ]---------------------------------------------------------
		/**
		 * @throws VariableDeclarationException - when the 'tora' is not > 0
		 */
		public void setTORA(double tora) throws VariableDeclarationException{
			if(tora <= 0) throw new VariableDeclarationException("TORA", tora, "TORA > 0");

			this.decTora = tora;
		}
		/**
		 * @throws VariableDeclarationException - when 'lda' is not >= 0
		 */
		public void setLDA(double lda) throws VariableDeclarationException{
			if ( lda  < 0 ) throw new VariableDeclarationException("LDA", lda, "LDA >= 0");
			
			this.decLda = lda;
		}
		/**
		 * @throws VariableDeclarationException - when 'asda' is not >= 0
		 */
		public void setASDA(double asda) throws VariableDeclarationException{
			if ( asda  < 0 ) throw new VariableDeclarationException("ASDA", asda, "ASDA >= 0");
			
			this.decAsda = asda;
		}
		/**
		 * @throws VariableDeclarationException - when 'toda' is not >= 0
		 */
		public void setTODA(double toda) throws VariableDeclarationException{
			if ( toda  < 0 ) throw new VariableDeclarationException("TODA", toda, "TODA >= 0");
			
			this.decToda = toda;
		}
		@Override
		public double getRESA() {
			return resa;
		}
		@Override
		public void setRESA(double resa) throws VariableDeclarationException {
			if ( resa  < 0 ) throw new VariableDeclarationException("RESA", resa, "RESA > 0");
			this.resa  = resa;			
		}
		//====[ Dependent Distance Methods  ]=====================================
		//----[ Getters ]------------------------------------------------------
		@Override
		public double getStopway(){
			return getASDA() - getTORA();
		}
		@Override
		public double getClearway(){
			return getTODA() - getTORA();
		}
		@Override
		public double getDisplacedThreshold(){
			return getTORA()-getLDA();
		}
		
		//====[ Mutators ]=============================================================
		@Override
		public void resetToNoObstacle(DeclaredRunwayInterface original) throws VariableDeclarationException{
			setTORA(original.getTORA());
			setLDA(original.getLDA());
			setASDA(original.getASDA());
			setTODA(original.getTODA());
			setRESA(original.getRESA());
		}

		@Override
		/**  ________________
		 *  |
		 *  |   ~~X~---->
		 *  |________________
		 */
		public void landOver(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException {
			this.addToLog("-[ "+getIdentifier()+" Land Over: Calculations ]-");
			
			double distFromObs = distanceFrom(parent.getPositionedObstacle()) - 2*parent.getPositionedObstacle().getRadius();
			double RESA = getRESA();
			double ALS = parent.getPositionedObstacle().getHeight() * getDescentAngle();
			
			double largestFactor = Math.max(Math.max(RESA,ALS)+ correctStripEnd(parent), parent.getBlastAllowance());
			double newLDA = original.getLDA() - largestFactor -  distFromObs;
			
			if(newLDA > original.getLDA()){
				newLDA = original.getLDA();
			}
			
			this.addToLog("distFromObs: "+distFromObs);
			this.addToLog("resa: "+RESA);
			this.addToLog("ALS: "+ALS);
			this.addToLog("Strip End: "+correctStripEnd(parent));
			this.addToLog("largestFactor: "+largestFactor);
			
			this.addToLog("LDA = original LDA - distFromObs - max(ALS,RESA,Blast Protection) ");
			this.addToLog("LDA: "+newLDA+" = "+original.getLDA()+" - "+distFromObs+" - "+largestFactor);
			this.line();

			setLDA(newLDA);
		}

		@Override
		/**  ________________
		 *  |
		 * ~~~---->   X
		 *  |________________
		 */
		public void landTowards(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException {
			this.addToLog("-[ "+getIdentifier()+" Land Towards: Calculations ]-");
			
			double distFromObs = distanceFrom(parent.getPositionedObstacle());
			double resa = getRESA();
			
			double newLDA = distFromObs - resa - oppositeStripEnd(parent);
			if(newLDA > original.getLDA()){
				newLDA = original.getLDA();
			}
			
			this.addToLog("distFromObs: "+distFromObs);
			this.addToLog("resa: "+resa);
			this.addToLog("LDA = distFromObs - RESA - strip end");
			this.addToLog("lda: "+newLDA+" = "+distFromObs+" - "+resa+" - "+oppositeStripEnd(parent));
			this.line();
			
			setLDA(newLDA);

		}

		@Override
		/**  ________________
		 *  |
		 *  |     X  ----~~~>
		 *  |________________
		 */
		public void takeOffAwayFrom(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException {
			//ASSUMPTION: stopway is part of clearway
			this.addToLog("-[ "+getIdentifier()+" Take Off Away: Calculations ]- ");
			
			double distFromObs = distanceFrom(parent.getPositionedObstacle()) + parent.getPositionedObstacle().getRadius()*2;
			double newTORA = original.getTORA() - parent.getBlastAllowance() - distFromObs - original.getDisplacedThreshold() ;
			
			this.addToLog("distFromObs: "+distFromObs);
			this.addToLog("TORA = origonal TORA - Blast Distance - distFromObs - displaced Threshold");
			this.addToLog("newTORA: "+newTORA+" = "+original.getTORA()+" - "+parent.getBlastAllowance()+" - "+distFromObs+" - "+getDisplacedThreshold());
			
			setTORA(newTORA);
			setASDA(newTORA+original.getStopway());
			setTODA(newTORA+original.getClearway());
			
			this.line();

			
		}

		@Override
		/**  ________________
		 *  |
		 *  |  ----~~X~>
		 *  |________________
		 */
		public void takeOffTowardsOver(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException {
			double distFromObs = distanceFrom(parent.getPositionedObstacle());
			double ALS = getAscentAngle()*parent.getPositionedObstacle().getHeight();
			
			double newTORA = distFromObs + original.getDisplacedThreshold() - ALS - oppositeStripEnd(parent);
			
			this.addToLog("-[ "+getIdentifier()+" Take Off Towards: Calculations ]-");
			this.addToLog("distFromObs: "+distFromObs);
			this.addToLog("ALS: "+ALS);
			this.addToLog("disThres: "+original.getDisplacedThreshold());
			this.addToLog("TORA = distFromObs + displaced Threshold - ALS - strip end");
			this.addToLog("newTORA: "+newTORA+" = "+distFromObs+" + "+original.getDisplacedThreshold()+" - "+ALS+" - "+oppositeStripEnd(parent));
			this.line();
			
			setTORA(newTORA);
			setASDA(newTORA); //Stopway blocked
			setTODA(newTORA);//clearway blocked
		}
		
		
		
		
		
		public boolean isSmallEnd(){
			return this.angle<180;
		}

		/**
		 * Returns the distance from threshold to the nearest part of the obstacle
		 */
		private double distanceFrom(PositionedObstacleInterface obs){
			if(this.isSmallEnd()){
				return obs.distanceFromSmallEnd()-obs.getRadius();
			}else{
				return obs.distanceFromLargeEnd()-obs.getRadius();
			}
		}
		
		/**
		 * Returns the strip end relevant to the current Declared Runway
		 */
		private double correctStripEnd(AirfieldInterface airfield){
			if(this.isSmallEnd()){
				return airfield.getLeftStripEnd();
			}else{
				return airfield.getRightStripEnd();
			}
		}
		/**
		 * Returns the strip end relevant to the current Declared Runway
		 */
		private double oppositeStripEnd(AirfieldInterface airfield){
			if(this.isSmallEnd()){
				return airfield.getRightStripEnd();
			}else{
				return airfield.getLeftStripEnd();
			}
		}
		
		@Override
		public double getDescentAngle() {
			return DEFAULT_DESC_RATIO;
		}

		@Override
		public double getAscentAngle() {
			return DEFAULT_ASC_RATIO;
		}
		
		public void line(){
			this.addToLog("--------~~----------");
		}
		
		public void addToLog(String text){
			this.log += text+"\n";
			try{
				Notification.notify(text);
			}catch (NullPointerException np){
				//External Logger not initialised
			}
		}
		
		public String getLog(){
			return this.log;
		}
		

}
