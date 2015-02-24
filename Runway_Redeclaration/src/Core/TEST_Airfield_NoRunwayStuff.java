package Core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidIdentifierException;

public class TEST_Airfield_NoRunwayStuff {
	public static final double[] airfieldVars = {100,3000,200,100,200,300,400,500};
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
		assertEquals(null, this.air.getPositionedObstacle());
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
		double dist = 20;
		try {
			this.air.addObstacle(obj,this.air.getSmallAngledRunway().getIdentifier(),dist);
			
			assertNotEquals(this.air.getPositionedObstacle(), null);
			
			assertEquals(dist,this.air.getPositionedObstacle().distanceFromSmallEnd(),0);
			assertEquals(airfieldVars[1]-dist,this.air.getPositionedObstacle().distanceFromLargeEnd(),0);
			
		} catch (InvalidIdentifierException e) {
			System.out.println(this.air.getSmallAngledRunway().getIdentifier());
			fail("We used a valid id");
		}
	}
	
	@Test
	public void testRemovingObstacle(){
		Obstacle obj = new Obstacle("Spongebob", 2.3, 4.5);
		double dist = 20;
		try {
			this.air.addObstacle(obj,this.air.getSmallAngledRunway().getIdentifier(),dist);
			
			assertNotEquals(this.air.getPositionedObstacle(), null);
			
		} catch (InvalidIdentifierException e) {
			System.out.println(this.air.getSmallAngledRunway().getIdentifier());
			fail("We used a valid id");
		}
		
		this.air.removeObstacle();
		assertEquals(this.air.getPositionedObstacle(), null);
	}
	
	@Test
	public void testChangingObstacle(){
		Obstacle obj = new Obstacle("Spongebob", 2.3, 4.5);
		Obstacle obj2 = new Obstacle("Side Show Bob", 23, 8);
		double dist = 20;
		double dist2 = 40;
		try {
			assertEquals(this.air.hasObstacle(), false);
			this.air.addObstacle(obj,this.air.getSmallAngledRunway().getIdentifier(),dist);
			assertEquals(this.air.hasObstacle(), true);
			this.air.addObstacle(obj2, this.air.getSmallAngledRunway().getIdentifier(), dist2);
			
			assertNotEquals(obj.getName(), this.air.getPositionedObstacle().getName());
			assertEquals(obj2.getName(), this.air.getPositionedObstacle().getName());
			
			
		} catch (InvalidIdentifierException e) {
			System.out.println(this.air.getSmallAngledRunway().getIdentifier());
			fail("We used a valid id");
		}
		
	}

}
