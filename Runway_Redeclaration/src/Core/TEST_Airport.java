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
	public static final double[] airfieldVars = {100,3500,200,100,200,300,400,500};
	public static final double[] smallVars = {3660,0,0,7};
	public static final double[] largeVars = {3660,0,0,0};

	public Airport airport;

	

	@Before
	public void setUp() {
		this.airport = new Airport(name);
	}

	@Test
	public void testName() {
		assertEquals(name,this.airport.getName());
	}

	@Test @Ignore
	public void testSingleAddingAirfield() {
		try {
			this.airport.addNewAirfield(90, airfieldVars, smallVars, largeVars);


		} catch (ParallelRunwayException e) {
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

	@Test @Ignore
	public void testTwoNonParallelRunways() {
		try {
			this.airport.addNewAirfield(45, airfieldVars, smallVars, largeVars);
			this.airport.addNewAirfield(76, airfieldVars, smallVars, largeVars);
			assertEquals(2, this.airport.getAirfields().size());

		} catch (ParallelRunwayException e) {
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

	@Test @Ignore
	public void testTwoParallels_DoNothing() {
		try {
			this.airport.addNewAirfield(44, airfieldVars, smallVars, largeVars);
			this.airport.addNewAirfield(40, airfieldVars, smallVars, largeVars);
			fail("Exception not caught");

		} catch (ParallelRunwayException e) {
			assertNotEquals("Ensure 2 runways",2, this.airport.getAirfields().size());

		} catch (CannotMakeRunwayException e) {
			fail("CannotMakeRunwayException");
			e.printStackTrace();
		} catch (VariableDeclarationException e) {
			fail("VariableDeclarationException \n"+e.getMessage());
			e.printStackTrace();
		}
	}

	@Test @Ignore
	public void testTwoParallels_UseException() throws VariableDeclarationException {
		try {
			this.airport.addNewAirfield(44, airfieldVars, smallVars, largeVars);
			this.airport.addNewAirfield(40, airfieldVars, smallVars, largeVars);
			fail("Exception not caught");

		} catch (ParallelRunwayException pe) {
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

	@Test @Ignore
	public void testThreeParallels_UseException() throws VariableDeclarationException {
		try {
			this.airport.addNewAirfield(44, airfieldVars, smallVars, largeVars);
			this.airport.addNewAirfield(40, airfieldVars, smallVars, largeVars);
			fail("Exception not caught");

		} catch (ParallelRunwayException pe) {
			char option = pe.getAvailableOptions().get(0);
			pe.chooseOption(option);
			assertEquals("Ensure 2 runways via Exception", 2, this.airport.getAirfields().size());

			//Time to make a third runway
			try {
				this.airport.addNewAirfield(41, airfieldVars, smallVars, largeVars);
				fail("Second Exception not caught");


			} catch (ParallelRunwayException pe2) {
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
		System.out.println("Start!");
		try {
			System.out.println("---Adding---");
			this.airport.addNewAirfield(44, airfieldVars, smallVars, largeVars);
			System.out.println(airport);
			System.out.println("---Adding---");

			this.airport.addNewAirfield(40, airfieldVars, smallVars, largeVars);
			fail("Exception not caught");
			
		} catch (ParallelRunwayException pe) {
			char option = pe.getAvailableOptions().get(0);
			System.out.println("Options"+pe.getAvailableOptions());
			System.out.println("---Choosing---");
			pe.chooseOption(option);
			
			assertEquals("Ensure 2 runways via Exception", 2, this.airport.getAirfields().size());
			System.out.println(airport);
			
			
			//Time to make a third runway
			try {
				System.out.println("---Adding---");
				this.airport.addNewAirfield(41, airfieldVars, smallVars, largeVars);
				fail("Second Exception not caught");


			} catch (ParallelRunwayException pe2) {
				assertEquals("Ensure 2 runways after second exception", 2, this.airport.getAirfields().size());
				System.out.println("Options"+pe2.getAvailableOptions());
				
				char option2 = pe2.getAvailableOptions().get(0);
				System.out.println("---Choosing---");
				pe2.chooseOption(option2);
				assertEquals("Ensure 3 runways via Exception", 3, this.airport.getAirfields().size());
				System.out.println(airport);

				
				
				try {
					System.out.println("---Adding---");
					this.airport.addNewAirfield(40, airfieldVars, smallVars, largeVars);
					System.out.println(airport);
					fail("Should not allow this");
					
					
					
				} catch (ParallelRunwayException e) {
					fail("ParrallelRunwayException");
					e.printStackTrace();
				} catch (CannotMakeRunwayException e) {
					System.out.println("Adding not allowed");
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
		
		System.out.println("End!");
	}

	@Test  @Ignore
	public void testTwoParallels_Add3rdNonParallel() throws VariableDeclarationException {
		try {
			this.airport.addNewAirfield(44, airfieldVars, smallVars, largeVars);
			this.airport.addNewAirfield(40, airfieldVars, smallVars, largeVars);
			fail("Exception not caught");

		} catch (ParallelRunwayException pe) {
			char option = pe.getAvailableOptions().get(0);
			pe.chooseOption(option);
			assertEquals("Ensure 2 runways via Exception", 2, this.airport.getAirfields().size());
			
			try{
				this.airport.addNewAirfield(150, airfieldVars, smallVars, largeVars);
				assertEquals("Ensure 3 Runways", 3, this.airport.getAirfields().size());

				
			}catch (ParallelRunwayException e){
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



}
