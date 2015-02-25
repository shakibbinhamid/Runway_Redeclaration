package core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Stefan
 *
 */
public class TEST_Obstacle {
	
	public static final String name = "TestName";
	public static final double radius = 69;
	public static final double height = 666.666;

	public Obstacle obj;
	
	@Before
	public void init(){
		this.obj = new Obstacle(name,radius,height);
	}
	
	@Test
	public void testName() {
		assertEquals(name, this.obj.getName());
	}

	@Test
	public void testRadius(){
		assertEquals(radius, this.obj.getRadius(),0);
	}
	
	@Test
	public void testHeight(){
		assertEquals(height, this.obj.getHeight(),0);
	}
	
	
}
