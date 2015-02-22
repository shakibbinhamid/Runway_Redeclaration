package Core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Exceptions.CannotMakeRunwayException;
import Exceptions.ParrallelRunwayException;
import Exceptions.VariableDeclarationException;

public class TEST_Airport {
	public static final String name = "The super awesome airport";
	public static final double[] airfieldVars = {50,100,100,100,200,300,400,500};
	
	
	public Airport airport;
	
	
	
	@Before
	public void setUp()  {
		this.airport = new Airport(name);
	}

	@Test
	public void testName() {
		assertEquals(name,this.airport.getName());
	}

	@Test
	public void testSingleAddingAirfield() {
		try {
			this.airport.addNewAirfield(45, airfieldVars);
			
			
		} catch (ParrallelRunwayException e) {
			fail("ParrallelRunwayException");
			e.printStackTrace();
		} catch (CannotMakeRunwayException e) {
			fail("CannotMakeRunwayException");
			e.printStackTrace();
		} catch (VariableDeclarationException e) {
			fail("VariableDeclarationException \n"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTwoNonParallelRunways() {
		try {
			this.airport.addNewAirfield(45, airfieldVars);
			this.airport.addNewAirfield(76, airfieldVars);

			
		} catch (ParrallelRunwayException e) {
			fail("ParrallelRunwayException");
			e.printStackTrace();
		} catch (CannotMakeRunwayException e) {
			fail("CannotMakeRunwayException");
			e.printStackTrace();
		} catch (VariableDeclarationException e) {
			fail("VariableDeclarationException \n"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testParallelRunways_DoNothing() {
		try {
			this.airport.addNewAirfield(45, airfieldVars);
			this.airport.addNewAirfield(40, airfieldVars);
			fail("Exception not caught");
			
		} catch (ParrallelRunwayException e) {
			
		} catch (CannotMakeRunwayException e) {
			fail("CannotMakeRunwayException");
			e.printStackTrace();
		} catch (VariableDeclarationException e) {
			fail("VariableDeclarationException \n"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test @Ignore
	public void test2() {
		fail("Not yet implemented");
	}
	@Test @Ignore
	public void test3() {
		fail("Not yet implemented");
	}

	

}
