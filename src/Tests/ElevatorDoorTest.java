package Tests;

import ElevatorSubSystem.ElevatorDoor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Elevator Door JUnit Test Case
 * 
 * @author Muneeb Nasir
 */
public class ElevatorDoorTest {

	@Test
	public void enumStringTest() {
		assertEquals("OPEN", ElevatorDoor.OPEN.toString());
		assertEquals("CLOSE", ElevatorDoor.CLOSE.toString());
	}

}