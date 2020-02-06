package Tests;

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
	private List<Parser> elevatorEventsTest;

	@Before
	public void setUp() throws Exception {
		parserTest = new Parser();
		elevatorEventsTest =  new ArrayList<>();
	}

	@Test
	public void testMakelist() {
		elevatorEventsTest = parserTest.makeList(parserTest.csvReader());
		assertNotNull(elevatorEventsTest);
		assertNotEquals("", parserTest.toString());

	}

	@Test
	public void getStartFloor() {
		assertNotEquals(0, parserTest.getStartFloor());
	}

	@Test
	public void setEndFloor() {
		assertNotEquals(0, parserTest.getEndFloor());
	}

}