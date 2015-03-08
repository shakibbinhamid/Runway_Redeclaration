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
	
	public static String log; //TODO Somehow hide from element
	@Element
	private double decTora, decLda, decAsda, decToda, disThresh, resa, startOfRofl;
	
	@Element
	private int angle;
	@Element
	private char direction;

	//nullary constructor for xml
	protected DeclaredRunway(){}
	
	protected DeclaredRunway(AirfieldInterface airfield, int angleFromNorth, char side,
			double tora, double asda, double toda, double lda) throws VariableDeclarationException{
		log = "-[Log]-\n";

		setTORA(tora);
		setASDA(asda);
		setTODA(toda);
		setLDA(lda);
		
		setDisplacedThreshold(tora-lda);
		setRESA(DEFAULT_RESA);
		setStartOfRoll(0);

		
		if(!(getTORA() <= getASDA() && getASDA() <= getTODA())) //&& this.decAsda <= this.decToda)) 
			throw new VariableDeclarationException("TORA, ASDA, TODA", new double[] {decTora,decAsda,decToda}, "TORA <= ASDA <= TODA");
		

		setAngle(angleFromNorth);
		setSideLetter(side);
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
		//----[ Setters ]-----------------------------------------------------
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
		@Override
		public double getDisplacedThreshold(){
			return this.disThresh;
		}
		@Override
		public double getStartOfRoll() {
			return this.startOfRofl;
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
			if ( resa  < 0 ) throw new VariableDeclarationException("RESA", resa, "RESA >= 0");
			this.resa  = resa;			
		}
		@Override
		public void setDisplacedThreshold(double dT) throws VariableDeclarationException {
			if ( dT < 0 ) throw new VariableDeclarationException("Displaced Threshold", dT, "DT > 0");
			
			this.disThresh = dT;
		}
		@Override
		public void setStartOfRoll(double roflmao) throws VariableDeclarationException {
			if (roflmao < 0) throw new VariableDeclarationException("Start Of Roll", roflmao, "Start Of Roll > 0");
			
			this.startOfRofl = roflmao;
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
		//====[ Mutators ]=============================================================
		@Override
		public void resetToNoObstacle(DeclaredRunwayInterface original) throws VariableDeclarationException{
			setTORA(original.getTORA());
			setLDA(original.getLDA());
			setASDA(original.getASDA());
			setTODA(original.getTODA());
			setRESA(original.getRESA());
			setDisplacedThreshold(original.getDisplacedThreshold());
		}

		@Override
		/**  ________________
		 *  |
		 *  |   ~~X~---->
		 *  |________________
		 */
		public void landOver(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException {
			this.addToLog("-[ "+getIdentifier()+" Land Over: Calculations ]-");
			if(outOfBounds(parent.getStripEnd(), parent.getPositionedObstacle())){
				this.addToLog("Out of Bounds");
				return;
			}
			double displacement = Math.max((Math.max(this.getRESA(), parent.getPositionedObstacle().getHeight()*getDescentAngle()))+parent.getStripEnd(), parent.getBlastAllowance());
			double newdt = Math.max(original.getDisplacedThreshold() + distanceFrom(parent.getPositionedObstacle())+parent.getPositionedObstacle().getRadius()*2 + displacement, original.getDisplacedThreshold());
		
			
			System.out.println("---------------");
			System.out.println("New dt (local): " + newdt);
			System.out.println("New dt (tora-lda);: "+getDisplacedThreshold());
			System.out.println("Old dt: "+ original.getDisplacedThreshold());
			setLDA(original.getTORA()-newdt);//-------------------------------------------//
			setDisplacedThreshold(newdt);//------------------------------------------------//
			System.out.println("(O) TORA - (R) LDA:  "+(original.getTORA()-getLDA()));
			System.out.println("---------------");

			/*
			double distFromObs = distanceFrom(parent.getPositionedObstacle()) - 2*parent.getPositionedObstacle().getRadius();
			double RESA = getRESA();
			double ALS = parent.getPositionedObstacle().getHeight() * getDescentAngle();
			
			double largestFactor = Math.max(Math.max(RESA,ALS)+ parent.getStripEnd(), parent.getBlastAllowance());
			double newLDA = original.getLDA() - largestFactor -  distFromObs;
			
			this.addToLog("distFromObs: "+distFromObs);
			this.addToLog("resa: "+RESA);
			this.addToLog("ALS: "+ALS);
			this.addToLog("Strip End: "+parent.getStripEnd());
			this.addToLog("largestFactor: "+largestFactor);
			
			this.addToLog("LDA = original LDA - distFromObs - max(ALS,RESA,Blast Protection) ");
			this.addToLog("LDA: "+newLDA+" = "+original.getLDA()+" - "+distFromObs+" - "+largestFactor);
			this.line();

			setLDA(newLDA);*/
		}

		@Override
		/**  ________________
		 *  |
		 * ~~~---->   X
		 *  |________________
		 */
		public void landTowards(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException {
			this.addToLog("-[ "+getIdentifier()+" Land Towards: Calculations ]-");
			if(outOfBounds(parent.getStripEnd(), parent.getPositionedObstacle())){
				this.addToLog("Out of Bounds");
				return;
			}
			double subtractThis = this.getRESA() + parent.getStripEnd() ;
			this.setLDA(distanceFrom(parent.getPositionedObstacle()) - subtractThis);
			
			/*
			double distFromObs = distanceFrom(parent.getPositionedObstacle());
			double resa = getRESA();
			
			double newLDA = distFromObs - resa - parent.getStripEnd();
			if(newLDA > original.getLDA()){
				newLDA = original.getLDA();
			}
			
			this.addToLog("distFromObs: "+distFromObs);
			this.addToLog("resa: "+resa);
			this.addToLog("LDA = distFromObs - RESA - strip end");
			this.addToLog("lda: "+newLDA+" = "+distFromObs+" - "+resa+" - "+parent.getStripEnd());
			this.line();
			
			setLDA(newLDA);
			*/

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
			if(outOfBounds(parent.getStripEnd(), parent.getPositionedObstacle())){
				this.addToLog("Out of Bounds");
				return;
			}
			double newStartOfRoll = Math.max(original.getDisplacedThreshold() + distanceFrom(parent.getPositionedObstacle()) + parent.getPositionedObstacle().getRadius()*2 + parent.getBlastAllowance(),original.getStartOfRoll());
			
			/* I NEED this.startOfRoll = newStartOfRoll */
			this.setStartOfRoll(newStartOfRoll);
			this.setTORA(original.getTORA() - newStartOfRoll);
			this.setTODA(this.getTORA() + original.getClearway());
			this.setASDA(this.getTORA() + original.getStopway());
			
			/*
			double distFromObs = distanceFrom(parent.getPositionedObstacle()) + parent.getPositionedObstacle().getRadius()*2;
			double newTORA = original.getTORA() - parent.getBlastAllowance() - distFromObs - original.getDisplacedThreshold() ;
			
			if(newTORA > original.getTORA()){
				newTORA = original.getTORA();
			}
			
			this.addToLog("distFromObs: "+distFromObs);
			this.addToLog("TORA = origonal TORA - Blast Distance - distFromObs - displaced Threshold");
			this.addToLog("newTORA: "+newTORA+" = "+original.getTORA()+" - "+parent.getBlastAllowance()+" - "+distFromObs+" - "+getDisplacedThreshold());
			
			
			setTORA(newTORA);
			setASDA(newTORA+original.getStopway());
			setTODA(newTORA+original.getClearway());
			
			this.line();
			*/
			
		}

		@Override
		/**  ________________
		 *  |
		 *  |  ----~~X~>
		 *  |________________
		 */
		public void takeOffTowardsOver(DeclaredRunwayInterface original, AirfieldInterface parent) throws VariableDeclarationException {
			
			/*
			double distFromObs = distanceFrom(parent.getPositionedObstacle());
			double ALS = getAscentAngle()*parent.getPositionedObstacle().getHeight();
			
			double newTORA = distFromObs + original.getDisplacedThreshold() - ALS - parent.getStripEnd();
			
			if(newTORA > original.getTORA()){
				newTORA = original.getTORA();
			}*/
			
			this.addToLog("-[ "+getIdentifier()+" Take Off Towards: Calculations ]-");
			if(outOfBounds(parent.getStripEnd(), parent.getPositionedObstacle())){
				this.addToLog("Out of Bounds");
				return;
			}
			double subtractThis = parent.getPositionedObstacle().getHeight()*this.getAscentAngle() + parent.getStripEnd();
			
			double newTORA = distanceFrom(parent.getPositionedObstacle()) + original.getDisplacedThreshold() - subtractThis;
			
			setTORA(newTORA);
			setASDA(newTORA);
			setTODA(newTORA);
			
			/*
			this.addToLog("distFromObs: "+distFromObs);
			this.addToLog("ALS: "+ALS);
			this.addToLog("disThres: "+original.getDisplacedThreshold());
			this.addToLog("TORA = distFromObs + displaced Threshold - ALS - strip end");
			this.addToLog("newTORA: "+newTORA+" = "+distFromObs+" + "+original.getDisplacedThreshold()+" - "+ALS+" - "+parent.getStripEnd());
			this.line();
			
			setTORA(newTORA);
			setASDA(newTORA); //Stopway blocked
			setTODA(newTORA);//clearway blocked
			*/
		}
		
		
		private boolean outOfBounds(double stripEnd, PositionedObstacleInterface obs){
			if(distanceFrom(obs)<0){
				if(-distanceFrom(obs) > (stripEnd+getDisplacedThreshold())){
					return true;
				}
			}
			return false;
		}
		
		
		@Override
		public boolean isSmallEnd(){
			return this.angle<180;
		}

		/**
		 * Returns the distance from threshold to the nearest part of the obstacle
		 */
		private double distanceFrom(PositionedObstacleInterface obs){
			int m =1;
			if(this.isSmallEnd()){
				if(obs.distanceFromSmallEnd()<0) m=-1;
				return obs.distanceFromSmallEnd()-obs.getRadius()*m;
			}else{
				if(obs.distanceFromLargeEnd()<0) m=-1;
				return obs.distanceFromLargeEnd()-obs.getRadius()*m;
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
			log += text+"\n";
			try{
				Notification.notify(text);
			}catch (NullPointerException np){
				//External Logger not initialised
			}
		}
		
		public String getLog(){
			return log;
		}
		
		public static char oppositeSide(char side){
			if(side == 'C'){
				return side;
			}else if(side == 'L'){
				return 'R';
			}else{
				return 'L';
			}
		}

		

}
