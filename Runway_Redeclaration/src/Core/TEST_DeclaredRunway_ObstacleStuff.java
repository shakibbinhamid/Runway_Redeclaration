package Core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidIdentifierException;

public class TEST_DeclaredRunway_ObstacleStuff {
	public static final double[] airfieldVars = {100,3696,200,100,200,300,400,500};
	public static final double[] smallVars = {3902,0,0,306};
	public static final double[] largeVars = {3884,0,78,0};
	public static final int angle = 90;
	
	public Airfield air;
	
	@Before
	public void setUp() throws Exception {
		this.air = new Airfield(angle, airfieldVars, smallVars, largeVars);
	}

	@Test
	public void testAddingObstacleWithGoodID(){
		Obstacle obj = new Obstacle("Spongebob", 2.3, 4.5);
		double dist = 20;
		try {
			this.air.addObstacle(obj,this.air.getSmallAngledRunway().getIdentifier(),dist);
			
			assertNotEquals(this.air.getObstacle(), null);
			
			assertEquals(dist,this.air.getObstacle().distanceFromSmallEnd(),0);
			assertEquals(airfieldVars[1]-dist,this.air.getObstacle().distanceFromLargeEnd(),0);
			
		} catch (InvalidIdentifierException e) {
			System.out.println(this.air.getSmallAngledRunway().getIdentifier());
			fail("We used a valid id");
		}
	}
	
	

}
