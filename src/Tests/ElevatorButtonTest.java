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

	/**
	 * JUnit Test Case for Elevator Buttons
	 */
	@Test
	public void enumStringTest() {
		assertEquals("ON", ElevatorButton.ON.toString());
		assertEquals("OFF", ElevatorButton.OFF.toString());
		assertNotEquals("OFF", ElevatorButton.ON.toString());
		assertNotEquals("UP", ElevatorButton.OFF.toString());
		assertNotNull(ElevatorButton.ON.toString());
		assertNotNull(ElevatorButton.OFF.toString());
	}

}