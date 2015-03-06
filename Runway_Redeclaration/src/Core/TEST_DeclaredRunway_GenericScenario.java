package Core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import Exceptions.InvalidIdentifierException;
import Exceptions.VariableDeclarationException;

public class TEST_DeclaredRunway_GenericScenario {
	public static double[] _09L =  {3902,3902,3902,3596};
	public static double[] _09R =  {3660,3660,3660,3353};
	public static double[] _027L = {3660,3660,3660,3660};
	public static double[] _027R = {3884,3884,3962,3884};
	
	public static void Setup1() throws VariableDeclarationException, InvalidIdentifierException{
		//  09L/27R
		make(_09L, _027R,'L',
				90,  12,
				new double[] {3346,3346,3346,2986}, new double[] {2986,2986,2986,3346},
				-50, 3646);
	}
	public static void Setup2() throws VariableDeclarationException, InvalidIdentifierException{
		//  09R/27L
		make(_09R, _027L,'R',
				90, 25,
				new double[] {1850,1850,1850,2553}, new double[] {2860,2860,2860,1850},
				2853, 500);
	}
	public static void Setup3() throws VariableDeclarationException, InvalidIdentifierException{
		//  09R/27L
		make(_09R, _027L,'R',
				90, 15,
				new double[] {2903,2903,2903,2393}, new double[] {2393,2393,2393,2903},
				150, 3203);
	}
	public static void Setup4() throws VariableDeclarationException, InvalidIdentifierException{
		//  09L/27R
		make(_09L, _027R,'L',
				90, 20,
				new double[] {2792,2792,2792,3246}, new double[] {3534,3534,3612,2774},
				3546, 50);
	}
	public static void Setup5() throws VariableDeclarationException, InvalidIdentifierException{
		//  09L/27R
		make(_027R, _09L,'L',
				90, 20,
				new double[] {3534,3534,3612,2774},  new double[] {2792,2792,2792,3246},  
				  50, 3546);
	}
	static boolean verboseMode = false;
	@BeforeClass
	public static void chooseScenario() throws VariableDeclarationException, InvalidIdentifierException{
		 Scanner in = new Scanner(System.in);
		 System.out.println("Verbose mode?");
		 if(getInput().toLowerCase().equals("n")){
			 verboseMode = false;
		 }else{
			 verboseMode = true;
		 }
		 
		 System.out.println("Choose a scneario");
		 int choice = in.nextInt();
		 if (choice == -1){
			 entryModeSetup();
		 }else
		 if(choice== 1){
			 Setup1();
		 }else
		 if(choice ==2){
			 Setup2();
		 }else
		 if (choice ==3){
			 Setup3();
		 }else
		 if(choice==4){
			Setup4();
		 }else 
		 if(choice==5){
			Setup5();
		 }else{
			 System.out.println("Adios then bro");
		 }
		 
		 in.close();
		 
	}

	private static void entryModeSetup() throws VariableDeclarationException, InvalidIdentifierException{
		System.out.println("Please enter values:\n");
		
		System.out.println("==[ Airfield Params ]==");
		int angle = (int) getDblput("Angle");
		System.out.print("Side $>");
		String unchanged = inp.next();
		char side = unchanged.charAt(0);
		System.out.println("=========================\n\n");

		System.out.println("==[ Small Angled Size ]==");
		System.out.println("---[ Default Values ]----");
		double tora = getDblput("TORA");
		double toda = getDblput("TODA");
		 if(toda < 0){ 
			 tora = getDblput("r:TORA");
			 toda = getDblput("TODA");
		 }
		 double asda = getDblput("ASDA");
		 if(asda < 0)  {
			 toda = getDblput("r:TODA");
			 asda = getDblput("ASDA");
		 }
		 double lda = getDblput("LDA");
		 if(lda < 0) {
			 asda = getDblput("r:ASDA");
			 getDblput("LDA");
		 }

		 if(toda == 0 ) toda = tora;
		 if(asda == 0 ) asda = tora;
		 if(lda == 0 ) lda = tora;
		 
		System.out.println("---[ Expected Values ]----");
		double newtora = getDblput("TORA");
		 if(newtora < 0) {
			 lda = getDblput("r:LDA");
			 newtora = getDblput("TORA");
		 }
		 double newtoda = getDblput("TODA");
		 if(newtoda < 0){
			 newtora = getDblput("r:(N) TORA");
			 newtoda = getDblput("TODA");
		 }
		 double newasda = getDblput("ASDA");
		 if(newasda < 0){
			 newtoda = getDblput("r:(N) TODA");
			 newasda = getDblput("ASDA");
		 }
		 double newlda = getDblput("LDA");
		 if(newlda < 0){
			 newasda = getDblput("r:(N) ASDA");
			 newlda = getDblput("LDA");
		 }

		 
		 if(newtoda == 0 ) newtoda = newtora;
		 if(newasda == 0 ) newasda = newtora;
		 if(newlda == 0 ) newlda = newtora;
		
		double[] smallDefVals = {tora,asda,toda,lda};
		double[] smallExpVals = {newtora,newasda,newtoda,newlda};
		System.out.println("=========================\n\n");
		System.out.println("==[ Large Angled Size ]==");
		System.out.println("---[ Default Values ]----");
		
	     tora = getDblput("TORA");
	     if(tora < 0 ) {
	    	 newlda = getDblput("r: LDA");
	 		smallExpVals = new double[] {newtora,newasda,newtoda,newlda};
	 		tora = getDblput("TORA");
	     }
	     toda = getDblput("TODA");
		 if(toda < 0){ 
			 tora = getDblput("r:TORA");
			 toda = getDblput("TODA");
		 }
		  asda = getDblput("ASDA");
		 if(asda < 0)  {
			 toda = getDblput("r:TODA");
			 asda = getDblput("ASDA");
		 }
		  lda = getDblput("LDA");
		 if(lda < 0) {
			 asda = getDblput("r:ASDA");
			 getDblput("LDA");
		 }

		 if(toda == 0 ) toda = tora;
		 if(asda == 0 ) asda = tora;
		 if(lda == 0 ) lda = tora;
		 
		System.out.println("---[ Expected Values ]----");
		 newtora = getDblput("TORA");
		 if(newtora < 0) {
			 lda = getDblput("r:LDA");
			 newtora = getDblput("TORA");
		 }
		  newtoda = getDblput("TODA");
		 if(newtoda < 0){
			 newtora = getDblput("r:(N) TORA");
			 newtoda = getDblput("TODA");
		 }
		  newasda = getDblput("ASDA");
		 if(newasda < 0){
			 newtoda = getDblput("r:(N) TODA");
			 newasda = getDblput("ASDA");
		 }
		  newlda = getDblput("LDA");
		 if(newlda < 0){
			 newasda = getDblput("r:(N) ASDA");
			 newlda = getDblput("LDA");
		 }

		 
		 if(newtoda == 0 ) newtoda = newtora;
		 if(newasda == 0 ) newasda = newtora;
		 if(newlda == 0 ) newlda = newtora;
		 
		 double[] largeDefVals = {tora,asda,toda,lda};
		 double[] largeExpVals = {newtora,newasda,newtoda,newlda};
		 System.out.println("=========================\n\n");
		
		System.out.println("==[ Obstacle Params ]==");
		double left = getDblput("Distance from left");
		double right = getDblput("Distance from right");
		double height = getDblput("Height");
		 
		 make(smallDefVals, largeDefVals, side, angle, height, smallExpVals, largeExpVals, left, right);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static double[] iniSmallVars;
	public static double[] expSmallVars;

	public static double[] iniLargeVars;
	public static double[] expLargeVars;

	public static double leftDistance;
	public static double rightDistance;

	public static int angle;
	public static Airfield air;
	public static Obstacle obj;
	
	private static void make(double[] sVars,double[] lVars, char side,
			int ang, double height,
			double[] expSVars,double[] expLVars, 
			double lDis, double rDis) 
					throws VariableDeclarationException, InvalidIdentifierException {
		iniSmallVars = sVars;
		iniLargeVars = lVars;

		expSmallVars = expSVars;
		expLargeVars = expLVars;

		leftDistance = lDis;
		rightDistance = rDis;

		angle = ang;

		air = new Airfield(angle, side ,iniSmallVars, iniLargeVars);
		obj = new Obstacle("Scenario Obstacle", 0, height);
		
		verbose();
	}
	
	private static void verbose() throws InvalidIdentifierException{
		if(verboseMode){
			show("Small TORA",iniSmallVars[0],air.getSmallAngledRunway().getTORA(),0);
			show("Small ASDA",iniSmallVars[1],air.getSmallAngledRunway().getASDA(),0);
			show("Small TODA",iniSmallVars[2],air.getSmallAngledRunway().getTODA(),0);
			show("Small LDA", iniSmallVars[3], air.getSmallAngledRunway().getLDA(),0);

			show("Small Stopway",iniSmallVars[1]-iniSmallVars[0],air.getSmallAngledRunway().getStopway(),0);
			show("Small Clearway",iniSmallVars[2]-iniSmallVars[0],air.getSmallAngledRunway().getClearway(),0);
			show("Small Displaced Threshold",iniSmallVars[0]-iniSmallVars[3],air.getSmallAngledRunway().getDisplacedThreshold(),0);

			//--[ Large angled ]------------
			show("Large TORA",iniLargeVars[0],air.getLargeAngledRunway().getTORA(),0);
			show("Large ASDA",iniLargeVars[1],air.getLargeAngledRunway().getASDA(),0);
			show("Large TODA",iniLargeVars[2],air.getLargeAngledRunway().getTODA(),0);
			show("Large LDA", iniLargeVars[3],air.getLargeAngledRunway().getLDA(),0);

			show("Large Stopway",iniLargeVars[1]-iniLargeVars[0],air.getLargeAngledRunway().getStopway(),0);
			show("Large Clearway",iniLargeVars[2]-iniLargeVars[0],air.getLargeAngledRunway().getClearway(),0);
			show("Large Displaced Threshold",iniLargeVars[0]-iniLargeVars[3],air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END pre checks >========================


			//===[ Add Obstacle ]==============================================================
			air.addObstacle(obj,leftDistance,rightDistance);
			assertNotEquals("Obstacle is actually added",air.getPositionedObstacle(), null);
			show("MY maths for small side works",leftDistance,air.getPositionedObstacle().distanceFromSmallEnd(),0);
			show("My maths for large side worked",rightDistance,air.getPositionedObstacle().distanceFromLargeEnd(),0);
			//===< End add Obstacle >============================



			//===[ Check Post-Obstacle values ]===================================
			//--[ small angled ]------------
			show("NEW Small TORA",expSmallVars[0],air.getSmallAngledRunway().getTORA(),0);
			show("NEW Small ASDA",expSmallVars[1],air.getSmallAngledRunway().getASDA(),0);
			show("NEW Small TODA",expSmallVars[2],air.getSmallAngledRunway().getTODA(),0);
			show("NEW Small LDA", expSmallVars[3],air.getSmallAngledRunway().getLDA(),0);

			show("NEW Small Stopway",expSmallVars[1]-expSmallVars[0],air.getSmallAngledRunway().getStopway(),0);
			show("NEW Small Clearway",expSmallVars[2]-expSmallVars[0],air.getSmallAngledRunway().getClearway(),0);
			show("NEW SMALL Displaced Threshold",expSmallVars[0]-expSmallVars[3],air.getSmallAngledRunway().getDisplacedThreshold(),0);

			//--[ Large angled ]------------
			show("NEW Large TORA",expLargeVars[0],air.getLargeAngledRunway().getTORA(),0);
			show("NEW Large ASDA",expLargeVars[1],air.getLargeAngledRunway().getASDA(),0);
			show("NEW Large TODA",expLargeVars[2],air.getLargeAngledRunway().getTODA(),0);
			show("NEW Large LDA",expLargeVars[3],air.getLargeAngledRunway().getLDA(),0);

			show("NEW Large Stopway",expLargeVars[1]-expLargeVars[0],air.getLargeAngledRunway().getStopway(),0);
			show("NEW Large Clearway",expLargeVars[2]-expLargeVars[0],air.getLargeAngledRunway().getClearway(),0);
			show("NEW Large Displaced Threshold",expLargeVars[0]-expLargeVars[3],air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END Post checks >========================
		}
	}

	static boolean failed = false;
	@Test
	public void test() {
		try {
			
			//===[ Check Pre-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("Small TORA",iniSmallVars[0],air.getSmallAngledRunway().getTORA(),0);
			assertEquals("Small ASDA",iniSmallVars[1],air.getSmallAngledRunway().getASDA(),0);
			assertEquals("Small TODA",iniSmallVars[2],air.getSmallAngledRunway().getTODA(),0);
			assertEquals("Small LDA", iniSmallVars[3], air.getSmallAngledRunway().getLDA(),0);

			assertEquals("Small Stopway",iniSmallVars[1]-iniSmallVars[0],air.getSmallAngledRunway().getStopway(),0);
			assertEquals("Small Clearway",iniSmallVars[2]-iniSmallVars[0],air.getSmallAngledRunway().getClearway(),0);
			assertEquals("Small Displaced Threshold",iniSmallVars[0]-iniSmallVars[3],air.getSmallAngledRunway().getDisplacedThreshold(),0);

			//--[ Large angled ]------------
			assertEquals("Large TORA",iniLargeVars[0],air.getLargeAngledRunway().getTORA(),0);
			assertEquals("Large ASDA",iniLargeVars[1],air.getLargeAngledRunway().getASDA(),0);
			assertEquals("Large TODA",iniLargeVars[2],air.getLargeAngledRunway().getTODA(),0);
			assertEquals("Large LDA", iniLargeVars[3],air.getLargeAngledRunway().getLDA(),0);

			assertEquals("Large Stopway",iniLargeVars[1]-iniLargeVars[0],air.getLargeAngledRunway().getStopway(),0);
			assertEquals("Large Clearway",iniLargeVars[2]-iniLargeVars[0],air.getLargeAngledRunway().getClearway(),0);
			assertEquals("Large Displaced Threshold",iniLargeVars[0]-iniLargeVars[3],air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END pre checks >========================


			//===[ Add Obstacle ]==============================================================
			air.addObstacle(obj,leftDistance,rightDistance);
			assertNotEquals("Obstacle is actually added",air.getPositionedObstacle(), null);
			assertEquals("MY maths for small side works",leftDistance,air.getPositionedObstacle().distanceFromSmallEnd(),0);
			assertEquals("My maths for large side worked",rightDistance,air.getPositionedObstacle().distanceFromLargeEnd(),0);
			//===< End add Obstacle >============================



			//===[ Check Post-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("NEW Small TORA",expSmallVars[0],air.getSmallAngledRunway().getTORA(),0);
			assertEquals("NEW Small ASDA",expSmallVars[1],air.getSmallAngledRunway().getASDA(),0);
			assertEquals("NEW Small TODA",expSmallVars[2],air.getSmallAngledRunway().getTODA(),0);
			assertEquals("NEW Small LDA", expSmallVars[3],air.getSmallAngledRunway().getLDA(),0);

			assertEquals("NEW Small Stopway",expSmallVars[1]-expSmallVars[0],air.getSmallAngledRunway().getStopway(),0);
			assertEquals("NEW Small Clearway",expSmallVars[2]-expSmallVars[0],air.getSmallAngledRunway().getClearway(),0);
			assertEquals("NEW SMALL Displaced Threshold",expSmallVars[0]-expSmallVars[3],air.getSmallAngledRunway().getDisplacedThreshold(),0);

			//--[ Large angled ]------------
			assertEquals("NEW Large TORA",expLargeVars[0],air.getLargeAngledRunway().getTORA(),0);
			assertEquals("NEW Large ASDA",expLargeVars[1],air.getLargeAngledRunway().getASDA(),0);
			assertEquals("NEW Large TODA",expLargeVars[2],air.getLargeAngledRunway().getTODA(),0);
			assertEquals("NEW Large LDA",expLargeVars[3],air.getLargeAngledRunway().getLDA(),0);

			assertEquals("NEW Large Stopway",expLargeVars[1]-expLargeVars[0],air.getLargeAngledRunway().getStopway(),0);
			assertEquals("NEW Large Clearway",expLargeVars[2]-expLargeVars[0],air.getLargeAngledRunway().getClearway(),0);
			assertEquals("NEW Large Displaced Threshold",expLargeVars[0]-expLargeVars[3],air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END Post checks >========================


			
			
		} catch (InvalidIdentifierException e) {
			System.err.println("-[ Failed ]-");
			System.err.println(air.getSmallAngledRunway().getIdentifier());
			System.err.println("-------------");
			failed = true;
			reasons.add("InvalidIntetifierException thrown");

		}finally{
			inp.close();
			if(failed) {
				printFailures();
			}
		}
	}
	static Scanner inp = new Scanner(System.in);
	private static String getInput(){
		String out  = inp.nextLine();
		return out;
	}
	private static double getDblput(String print){
		System.out.print(print+" $>");
		double out  = inp.nextDouble();
		return out;
	}
	
	static List<String> reasons = new ArrayList<>();
	
	private static void show(String name, double val, double exp, double leniancy){
		System.out.println(name+"= "+val);
		System.out.print("Ok? ..");
		String res = getInput();
		if (res.toLowerCase().equals("n")){
			failed = true;
			String realVal = getInput();
			reasons.add(name+"="+val+" not "+realVal+"   Hard coded("+exp+")");
		}
	}
	private static void printFailures(){
		for(String reason : reasons){
			System.err.println(reason);
		}
	}
	
}

