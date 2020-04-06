package Tests;

import Util.CallEvent;
import Util.Parser;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Parser Class JUnit Test Case
 * 
 * @author Muneeb Nasir
 */
public class ParserTest {
	private Parser parserTest;
	private List<CallEvent> elevatorEventsTest;

	@Before
	public void setUp() throws Exception {
		parserTest = new Parser();
		elevatorEventsTest =  parserTest.makeList(Parser.csvReader());
	}

	/**
	 * Test for the parser functionality
	 */
	@Test
	public void testMakelist() {
		assertNotNull(elevatorEventsTest);
		assertNotEquals("", parserTest.toString());

	}

	/**
	 * Test for getting the start floor
	 */
	@Test
	public void testGetStartFloor() {
		assertNotEquals(0, elevatorEventsTest.get(0).getStartFloor());
	}

	/**
	 * Test for destination floor
	 */
	@Test
	public void testSetEndFloor() {
		assertNotEquals(0, elevatorEventsTest.get(0).getEndFloor());
	}

	/**
	 * Test for fault
	 */
	@Test
	public void testGetFault() {
		assertNotEquals(0, elevatorEventsTest.get(0).getFault());
	}

}