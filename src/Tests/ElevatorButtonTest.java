package Tests;

import ElevatorSubSystem.ElevatorButton;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Elevator Button JUnit Test Case
 * 
 * @author Muneeb Nasir
 */
public class ElevatorButtonTest {

	@Test
	public void enumStringTest() {
		assertEquals("ON", ElevatorButton.ON.toString());
		assertEquals("OFF", ElevatorButton.OFF.toString());
	}

}