package Core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Exceptions.CannotMakeRunwayException;
import Exceptions.VariableDeclarationException;
/**
 * Tests Airport and ParallelRunwayException
 * 
 * @author Stefan
 *
 */
public class TEST_Airport {
	public static final String name = "The super awesome airport";
	public static final double[] airfieldVars = {50,100,100,100,200,300,400,500};
	public static final double[] smallVars = {};
	public static final double[] largeVars = {};

	public Airport airport;

	

	@Before
	public void setUp() {
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
			assertEquals(2, this.airport.getAirfields().size());

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
	public void testTwoParallels_DoNothing() {
		try {
			this.airport.addNewAirfield(44, airfieldVars);
			this.airport.addNewAirfield(40, airfieldVars);
			fail("Exception not caught");

		} catch (ParrallelRunwayException e) {
			assertNotEquals("Ensure 2 runways",2, this.airport.getAirfields().size());

		} catch (CannotMakeRunwayException e) {
			fail("CannotMakeRunwayException");
			e.printStackTrace();
		} catch (VariableDeclarationException e) {
			fail("VariableDeclarationException \n"+e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testTwoParallels_UseException() throws VariableDeclarationException {
		try {
			this.airport.addNewAirfield(44, airfieldVars);
			this.airport.addNewAirfield(40, airfieldVars);
			fail("Exception not caught");

		} catch (ParrallelRunwayException pe) {
			char option = pe.getAvailableOptions().get(0);
			pe.chooseOption(option);
			assertEquals("Ensure 2 runways via Exception", 2, this.airport.getAirfields().size());

		} catch (CannotMakeRunwayException e) {
			fail("CannotMakeRunwayException");
			e.printStackTrace();
		} catch (VariableDeclarationException e) {
			fail("VariableDeclarationException \n"+e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testThreeParallels_UseException() throws VariableDeclarationException {
		try {
			this.airport.addNewAirfield(44, airfieldVars);
			this.airport.addNewAirfield(40, airfieldVars);
			fail("Exception not caught");

		} catch (ParrallelRunwayException pe) {
			char option = pe.getAvailableOptions().get(0);
			pe.chooseOption(option);
			assertEquals("Ensure 2 runways via Exception", 2, this.airport.getAirfields().size());

			//Time to make a third runway
			try {
				this.airport.addNewAirfield(41, airfieldVars);
				fail("Second Exception not caught");


			} catch (ParrallelRunwayException pe2) {
				assertEquals("Ensure 2 runways after second exception", 2, this.airport.getAirfields().size());
				char option2 = pe.getAvailableOptions().get(0);
				pe.chooseOption(option2);
				assertEquals("Ensure 3 runways via Exception", 3, this.airport.getAirfields().size());

			} catch (CannotMakeRunwayException e) {
				fail("CannotMakeRunwayException");
				e.printStackTrace();
			}



		} catch (CannotMakeRunwayException e) {
			fail("CannotMakeRunwayException");
			e.printStackTrace();
		} catch (VariableDeclarationException e) {
			fail("VariableDeclarationException \n"+e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testThreeParallels_AddingAnother() throws VariableDeclarationException  {
		try {
			this.airport.addNewAirfield(44, airfieldVars);
			this.airport.addNewAirfield(40, airfieldVars);
			fail("Exception not caught");

		} catch (ParrallelRunwayException pe) {
			char option = pe.getAvailableOptions().get(0);
			pe.chooseOption(option);
			assertEquals("Ensure 2 runways via Exception", 2, this.airport.getAirfields().size());

			//Time to make a third runway
			try {
				this.airport.addNewAirfield(41, airfieldVars);
				fail("Second Exception not caught");


			} catch (ParrallelRunwayException pe2) {
				assertEquals("Ensure 2 runways after second exception", 2, this.airport.getAirfields().size());
				char option2 = pe.getAvailableOptions().get(0);
				pe.chooseOption(option2);
				assertEquals("Ensure 3 runways via Exception", 3, this.airport.getAirfields().size());

				try {
					this.airport.addNewAirfield(40, airfieldVars);
				} catch (ParrallelRunwayException e) {
					fail("ParrallelRunwayException");
					e.printStackTrace();
				} catch (CannotMakeRunwayException e) {
					assertEquals("Ensure 3 runways 4th Parallel not allowed", 3, this.airport.getAirfields().size());
						
				} catch (VariableDeclarationException e) {
					fail("VariableDeclarationException \n"+e.getMessage());
					e.printStackTrace();
				}
				
			} catch (CannotMakeRunwayException e) {
				fail("CannotMakeRunwayException");
				e.printStackTrace();
			}



		} catch (CannotMakeRunwayException e) {
			fail("CannotMakeRunwayException");
			e.printStackTrace();
		} catch (VariableDeclarationException e) {
			fail("VariableDeclarationException \n"+e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testTwoParallels_Add3rdNonParallel() throws VariableDeclarationException {
		try {
			this.airport.addNewAirfield(44, airfieldVars);
			this.airport.addNewAirfield(40, airfieldVars);
			fail("Exception not caught");

		} catch (ParrallelRunwayException pe) {
			char option = pe.getAvailableOptions().get(0);
			pe.chooseOption(option);
			assertEquals("Ensure 2 runways via Exception", 2, this.airport.getAirfields().size());
			
			try{
				this.airport.addNewAirfield(150, airfieldVars);
				assertEquals("Ensure 3 Runways", 3, this.airport.getAirfields().size());

			}catch (ParrallelRunwayException e){
				fail("ParrallelRunwayException");
				e.printStackTrace();
			} catch (CannotMakeRunwayException e) {
				fail("CannotMakeRunwayException");
				e.printStackTrace();
			}

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
