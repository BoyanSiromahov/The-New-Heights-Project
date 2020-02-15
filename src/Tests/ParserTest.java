package Tests;

import Util.CallEvent;
import Util.Parser;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Floor Class JUnit Test Case
 * 
 * @author Muneeb Nasir
 */
public class ParserTest {
	private Parser parserTest;
	private List<CallEvent> elevatorEventsTest;

	@Before
	public void setUp() throws Exception {
		parserTest = new Parser();
		elevatorEventsTest =  parserTest.makeList(parserTest.csvReader());
	}

	@Test
	public void testMakelist() {
		assertNotNull(elevatorEventsTest);
		assertNotEquals("", parserTest.toString());

	}

	@Test
	public void getStartFloor() {
		assertNotEquals(0, elevatorEventsTest.get(0).getStartFloor());
	}

	@Test
	public void setEndFloor() {
		assertNotEquals(0, elevatorEventsTest.get(0).getEndFloor());
	}

}