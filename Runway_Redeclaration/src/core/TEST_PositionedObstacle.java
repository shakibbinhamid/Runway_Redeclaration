package core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Stefan
 *
 */
public class TEST_PositionedObstacle {
	
	public static final Obstacle obj = new Obstacle("Jim",54.02,67.99);
	public static final double dFromSmallAnge = 1994;
	public static final double dFromLargeAnge = 28;
	
	public PositionedObstacle posObj;
	
	@Before
	public void setUp() {
		this.posObj = new PositionedObstacle(obj, dFromSmallAnge,dFromLargeAnge );
	}

	@Test
	public void testSmallDistance() {
		assertEquals(dFromSmallAnge, this.posObj.distanceFromSmallEnd(),0);
	}	@Test
	public void testLargeDistance() {
		assertEquals(dFromLargeAnge, this.posObj.distanceFromLargeEnd(),0);
	}
	
	
	@Test
	public void testName(){
		assertEquals(obj.getName(),this.posObj.getName());
	}
	
	@Test
	public void testRadius(){
		assertEquals(obj.getRadius(), this.posObj.getRadius(),0);
	}
	
	@Test
	public void testHeight(){
		assertEquals(obj.getHeight(), this.posObj.getHeight(),0);
	}

}
