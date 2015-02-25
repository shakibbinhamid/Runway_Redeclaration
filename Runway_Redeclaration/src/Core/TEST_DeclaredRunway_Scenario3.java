package Core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidIdentifierException;
import Exceptions.UnusableRunwayException;

public class TEST_DeclaredRunway_Scenario3 {
	public static final double stipEnd = 60;
	public static final double runwayLength = 4000;
	public static final double[] airfieldDimensions = {100,runwayLength,200,stipEnd,200,300,400,500};//TODO check the size of the runway ?4000?
	
	public static final double[] smallVars = {3660,0,0,307};
	public static final double[] LargeVars = {3660,0,0,0};
	public static final int angle = 90;
	
	public Airfield air;
	
	@Before
	public void setUp() throws Exception {
		this.air = new Airfield(angle, airfieldDimensions, smallVars, LargeVars);
	}

	@Test
	public void testAddingObstacleWithGoodID_On_SMALL() throws UnusableRunwayException{
		Obstacle obj = new Obstacle("Scenario 1 Obstacle", 0, 25);
		double dist = 150;
		try {
			//===[ Check Pre-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("Small TORA",2903,air.getSmallAngledRunway().getTORA(),0);
			assertEquals("Small ASDA",2903,air.getSmallAngledRunway().getASDA(),0);
			assertEquals("Small TODA",2903,air.getSmallAngledRunway().getTODA(),0);
			assertEquals("Small LDA",2393,air.getSmallAngledRunway().getLDA(),0);
			
			assertEquals("Small Stopway",0,air.getSmallAngledRunway().getStopway(),0);
			assertEquals("Small Clearway",0,air.getSmallAngledRunway().getClearway(),0);
			assertEquals("Small Displaced Threshold",307,air.getSmallAngledRunway().getDisplacedThreshold(),0);

			//--[ Large angled ]------------
			assertEquals("Large TORA",2393,air.getLargeAngledRunway().getTORA(),0);
			assertEquals("Large ASDA",2393,air.getLargeAngledRunway().getASDA(),0);
			assertEquals("Large TODA",2393,air.getLargeAngledRunway().getTODA(),0);
			assertEquals("Large LDA",3660,air.getLargeAngledRunway().getLDA(),0);
			
			assertEquals("Large Stopway",0,air.getLargeAngledRunway().getStopway(),0);
			assertEquals("Large Clearway",0,air.getLargeAngledRunway().getClearway(),0);
			assertEquals("Large Displaced Threshold",0,air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END pre checks >========================
			
		
			//===[ Add Obstacle ]==============================================================
			this.air.addObstacle(obj,this.air.getSmallAngledRunway().getIdentifier(),dist);
			assertNotEquals("Obstacle is actually added",this.air.getPositionedObstacle(), null);
			assertEquals("MY maths calculating small side",150,this.air.getPositionedObstacle().distanceFromSmallEnd(),0);
			assertEquals("My maths calculating large side",3203,this.air.getPositionedObstacle().distanceFromLargeEnd(),0);
			//===< End add Obstacle >============================
			
			
			
			//===[ Check Post-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("NEW Small TORA",1850,air.getSmallAngledRunway().getTORA(),0);
			assertEquals("NEW Small ASDA",1850,air.getSmallAngledRunway().getASDA(),0);
			assertEquals("NEW Small TODA",1850,air.getSmallAngledRunway().getTODA(),0);
			assertEquals("NEW Small LDA",2553,air.getSmallAngledRunway().getLDA(),0);
			
			assertEquals("NEW Small Stopway",0,air.getSmallAngledRunway().getStopway(),0);
			assertEquals("NEW Small Clearway",0,air.getSmallAngledRunway().getClearway(),0);
			assertEquals("NEW Displaced Threshold",307,air.getSmallAngledRunway().getDisplacedThreshold(),0);
			//TODO figure out the displaced Threshold
			
			//--[ Large angled ]------------
			assertEquals("NEW Large TORA",2860,air.getLargeAngledRunway().getTORA(),0);
			assertEquals("NEW Large ASDA",2860,air.getLargeAngledRunway().getASDA(),0);
			assertEquals("NEW Large TODA",2860,air.getLargeAngledRunway().getTODA(),0);
			assertEquals("NEW Large LDA",1850,air.getLargeAngledRunway().getLDA(),0);
			
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
		Obstacle obj = new Obstacle("Scenario 1 Obstacle", 0, 25);
		double dist = 3203;
		try {
			//===[ Check Pre-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("Small TORA",3660,air.getSmallAngledRunway().getTORA(),0);
			assertEquals("Small ASDA",3660,air.getSmallAngledRunway().getASDA(),0);
			assertEquals("Small TODA",3660,air.getSmallAngledRunway().getTODA(),0);
			assertEquals("Small LDA",3353,air.getSmallAngledRunway().getLDA(),0);
			
			assertEquals("Small Stopway",0,air.getSmallAngledRunway().getStopway(),0);
			assertEquals("Small Clearway",0,air.getSmallAngledRunway().getClearway(),0);
			assertEquals("Small Displaced Threshold",307,air.getSmallAngledRunway().getDisplacedThreshold(),0);

			//--[ Large angled ]------------
			assertEquals("Large TORA",3660,air.getLargeAngledRunway().getTORA(),0);
			assertEquals("Large ASDA",3660,air.getLargeAngledRunway().getASDA(),0);
			assertEquals("Large TODA",3660,air.getLargeAngledRunway().getTODA(),0);
			assertEquals("Large LDA",3660,air.getLargeAngledRunway().getLDA(),0);
			
			assertEquals("Large Stopway",0,air.getLargeAngledRunway().getStopway(),0);
			assertEquals("Large Clearway",0,air.getLargeAngledRunway().getClearway(),0);
			assertEquals("Large Displaced Threshold",0,air.getLargeAngledRunway().getDisplacedThreshold(),0);
			//===< END pre checks >========================
			
		
			//===[ Add Obstacle ]==============================================================
			this.air.addObstacle(obj,this.air.getLargeAngledRunway().getIdentifier(),dist);
			assertNotEquals("Obstacle is actually added",this.air.getPositionedObstacle(), null);
			assertEquals("MY maths calculating small side",2853,this.air.getPositionedObstacle().distanceFromSmallEnd(),0);
			assertEquals("My maths calculating large side",500,this.air.getPositionedObstacle().distanceFromLargeEnd(),0);
			//===< End add Obstacle >============================
			
			
			
			//===[ Check Post-Obstacle values ]===================================
			//--[ small angled ]------------
			assertEquals("NEW Small TORA",1850,air.getSmallAngledRunway().getTORA(),0);
			assertEquals("NEW Small ASDA",1850,air.getSmallAngledRunway().getASDA(),0);
			assertEquals("NEW Small TODA",1850,air.getSmallAngledRunway().getTODA(),0);
			assertEquals("NEW Small LDA",2553,air.getSmallAngledRunway().getLDA(),0);
			
			assertEquals("NEW Small Stopway",0,air.getSmallAngledRunway().getStopway(),0);
			assertEquals("NEW Small Clearway",0,air.getSmallAngledRunway().getClearway(),0);
			assertEquals("NEW Displaced Threshold",307,air.getSmallAngledRunway().getDisplacedThreshold(),0);
			//TODO figure out the displaced Threshold
			
			//--[ Large angled ]------------
			assertEquals("NEW Large TORA",2860,air.getLargeAngledRunway().getTORA(),0);
			assertEquals("NEW Large ASDA",2860,air.getLargeAngledRunway().getASDA(),0);
			assertEquals("NEW Large TODA",2860,air.getLargeAngledRunway().getTODA(),0);
			assertEquals("NEW Large LDA",1850,air.getLargeAngledRunway().getLDA(),0);
			
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
