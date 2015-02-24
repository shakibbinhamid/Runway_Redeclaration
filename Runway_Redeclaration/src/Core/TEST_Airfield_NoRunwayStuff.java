package Core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidIdentifierException;

public class TEST_Airfield_NoRunwayStuff {
	public static final double[] airfieldVars = {100,500,200,100,200,300,400,500};
	public static final double[] smallVars = {3660,0,0,7};
	public static final double[] largeVars = {3660,0,0,0};
	public static final int angle = 340;
	
	public Airfield air;
	
	@Before
	public void setUp() throws Exception {
		this.air = new Airfield(angle, airfieldVars, smallVars, largeVars);
	}

	@Test
	public void testNoObstacle() {
		assertEquals(null, this.air.getObstacle());
	}
	
	@Test
	public void testAddingObstacleWithBadID(){
		Obstacle obj = new Obstacle("Spongebob", 2.3, 4.5);
		try {
			this.air.addObstacle(obj, "jim", 20);
			fail("Exception not thrown");
		} catch (InvalidIdentifierException e) {
			//WE want to be found 
		}
	}
	
	@Test
	public void testAddingObstacleWithGoodID(){
		Obstacle obj = new Obstacle("Spongebob", 2.3, 4.5);
		try {
			this.air.addObstacle(obj,this.air.getSmallAngledRunway().getIdentifier(), 20);
			assertNotEquals(this.air.getObstacle(), null);
			assertEquals(,this.air.getObstacle().distanceFromSmallEnd());
			System.out.println(this.air.getObstacle().distanceFromLargeEnd());
			
		} catch (InvalidIdentifierException e) {
			System.out.println(this.air.getSmallAngledRunway().getIdentifier());
			fail("We used a valid id");
		}
	}
	
	@Test
	public void testRemovingObstacle(){
		
	}
	
	@Test
	public void testChangingObstacle(){
		
	}

}
