package Core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidIdentifierException;
import Exceptions.UnusableRunwayException;

public class TEST_DeclaredRunway_Scenario1 {
	public static final double stipEnd = 60;
	public static final double runwayLength = 4000;
	public static final double[] airfieldDimensions = {100,runwayLength,200,stipEnd,200,300,400,500};//TODO check the size of the runway ?4000?
	
	public static final double[] smallVars = {3902,0,0,306};
	public static final double[] LargeVars = {3884,0,78,0};
	public static final int angle = 90;
	
	public Airfield air;
	
	@Before
	public void setUp() throws Exception {
		this.air = new Airfield(angle, airfieldDimensions, smallVars, LargeVars);
	}

	@Test
	public void testAddingObstacleWithGoodID_On_SMALL() throws UnusableRunwayException{
		Obstacle obj = new Obstacle("Scenario 1 Obstacle", 0, 12);
		double dist = -50;
		try {
			//===[ Check Pre-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("Small TORA",3902,air.getSmallAngledRunway().getTORA(),0);
			assertEquals("Small ASDA",3902,air.getSmallAngledRunway().getASDA(),0);
			assertEquals("Small TODA",3902,air.getSmallAngledRunway().getTODA(),0);
			assertEquals("Small LDA",3596,air.getSmallAngledRunway().getLDA(),0);
			
			assertEquals("Small Stopway",0,air.getSmallAngledRunway().getStopway(),0);
			assertEquals("Small Clearway",0,air.getSmallAngledRunway().getClearway(),0);
			assertEquals("Small Displaced Threshold",306,air.getSmallAngledRunway().getDisplacedThreshold(),0);

			//--[ Large angled ]------------
			assertEquals("Large TORA",3884,air.getLargeAngledRunway().getTORA(),0);
			assertEquals("Large ASDA",3884,air.getLargeAngledRunway().getASDA(),0);
			assertEquals("Large TODA",3962,air.getLargeAngledRunway().getTODA(),0);
			assertEquals("Large LDA",3884,air.getLargeAngledRunway().getLDA(),0);
			
			assertEquals("Large Stopway",0,air.getLargeAngledRunway().getStopway(),0);
			assertEquals("Large Clearway",78,air.getLargeAngledRunway().getClearway(),0);
			assertEquals("Large Displaced Threshold",0,air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END pre checks >========================
			
		
			//===[ Add Obstacle ]==============================================================
			this.air.addObstacle(obj,this.air.getSmallAngledRunway().getIdentifier(),dist);
			assertNotEquals("Obstacle is actually added",this.air.getPositionedObstacle(), null);
			assertEquals("MY maths calculating small side works",dist,this.air.getPositionedObstacle().distanceFromSmallEnd(),0);
			assertEquals("My maths in calculating other side worked",3646,this.air.getPositionedObstacle().distanceFromLargeEnd(),0);
			//===< End add Obstacle >============================
			
			
			
			//===[ Check Post-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("NEW Small TORA",3346,air.getSmallAngledRunway().getTORA(),0);
			assertEquals("NEW Small ASDA",3346,air.getSmallAngledRunway().getASDA(),0);
			assertEquals("NEW Small TODA",3346,air.getSmallAngledRunway().getTODA(),0);
			assertEquals("NEW Small LDA",2986,air.getSmallAngledRunway().getLDA(),0);
			
			assertEquals("NEW Small Stopway",0,air.getSmallAngledRunway().getStopway(),0);
			assertEquals("NEW Small Clearway",0,air.getSmallAngledRunway().getClearway(),0);
			assertEquals("NEW Displaced Threshold",306,air.getSmallAngledRunway().getDisplacedThreshold(),0);
			//TODO figure out the displaced Threshold
			
			//--[ Large angled ]------------
			assertEquals("NEW Large TORA",2986,air.getLargeAngledRunway().getTORA(),0);
			assertEquals("NEW Large ASDA",2986,air.getLargeAngledRunway().getASDA(),0);
			assertEquals("NEW Large TODA",2986,air.getLargeAngledRunway().getTODA(),0);
			assertEquals("NEW Large LDA",3346,air.getLargeAngledRunway().getLDA(),0);
			
			assertEquals("NEW Large Stopway",0,air.getLargeAngledRunway().getStopway(),0);
			assertEquals("NEW Large Clearway",0,air.getLargeAngledRunway().getClearway(),0);
			assertEquals("NEW Displaced Threshold",0,air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END Post checks >========================
			
			
			
		} catch (InvalidIdentifierException e) {
			System.out.println(this.air.getSmallAngledRunway().getIdentifier());
			fail("We used a valid id");
		}
	}
	
	@Test
	public void testAddingObstacleWithGoodID_On_LARGE() throws UnusableRunwayException{
		Obstacle obj = new Obstacle("Scenario 1 Obstacle", 0, 12);
		double dist = 3646;
		try {
			//===[ Check Pre-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("Small TORA",3902,air.getSmallAngledRunway().getTORA(),0);
			assertEquals("Small ASDA",3902,air.getSmallAngledRunway().getASDA(),0);
			assertEquals("Small TODA",3902,air.getSmallAngledRunway().getTODA(),0);
			assertEquals("Small LDA",3596,air.getSmallAngledRunway().getLDA(),0);
			
			assertEquals("Small Stopway",0,air.getSmallAngledRunway().getStopway(),0);
			assertEquals("Small Clearway",0,air.getSmallAngledRunway().getClearway(),0);
			assertEquals("Small Displaced Threshold",306,air.getSmallAngledRunway().getDisplacedThreshold(),0);

			//--[ Large angled ]------------
			assertEquals("Large TORA",3884,air.getLargeAngledRunway().getTORA(),0);
			assertEquals("Large ASDA",3884,air.getLargeAngledRunway().getASDA(),0);
			assertEquals("Large TODA",3962,air.getLargeAngledRunway().getTODA(),0);
			assertEquals("Large LDA",3884,air.getLargeAngledRunway().getLDA(),0);
			
			assertEquals("Large Stopway",0,air.getLargeAngledRunway().getStopway(),0);
			assertEquals("Large Clearway",78,air.getLargeAngledRunway().getClearway(),0);
			assertEquals("Large Displaced Threshold",0,air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END pre checks >========================
			
		
			//===[ Add Obstacle ]==============================================================
			this.air.addObstacle(obj,this.air.getLargeAngledRunway().getIdentifier(),dist);
			assertNotEquals("Obstacle is actually added",this.air.getPositionedObstacle(), null);
			assertEquals("MY maths calculating small ",-50,this.air.getPositionedObstacle().distanceFromSmallEnd(),0);
			assertEquals("My maths calculating large",3646,this.air.getPositionedObstacle().distanceFromLargeEnd(),0);
			//===< End add Obstacle >============================
			
			
			
			//===[ Check Post-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("NEW Small TORA",3346,air.getSmallAngledRunway().getTORA(),0);
			assertEquals("NEW Small ASDA",3346,air.getSmallAngledRunway().getASDA(),0);
			assertEquals("NEW Small TODA",3346,air.getSmallAngledRunway().getTODA(),0);
			assertEquals("NEW Small LDA",2986,air.getSmallAngledRunway().getLDA(),0);
			
			assertEquals("NEW Small Stopway",0,air.getSmallAngledRunway().getStopway(),0);
			assertEquals("NEW Small Clearway",0,air.getSmallAngledRunway().getClearway(),0);
			assertEquals("NEW Displaced Threshold",306,air.getSmallAngledRunway().getDisplacedThreshold(),0);
			//TODO figure out the displaced Threshold
			
			//--[ Large angled ]------------
			assertEquals("NEW Large TORA",2986,air.getLargeAngledRunway().getTORA(),0);
			assertEquals("NEW Large ASDA",2986,air.getLargeAngledRunway().getASDA(),0);
			assertEquals("NEW Large TODA",2986,air.getLargeAngledRunway().getTODA(),0);
			assertEquals("NEW Large LDA",3346,air.getLargeAngledRunway().getLDA(),0);
			
			assertEquals("NEW Large Stopway",0,air.getLargeAngledRunway().getStopway(),0);
			assertEquals("NEW Large Clearway",0,air.getLargeAngledRunway().getClearway(),0);
			assertEquals("NEW Displaced Threshold",0,air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END Post checks >========================
			
			
			
		} catch (InvalidIdentifierException e) {
			System.out.println(this.air.getSmallAngledRunway().getIdentifier());
			fail("We used a valid id");
		}
	}
	
	

}
