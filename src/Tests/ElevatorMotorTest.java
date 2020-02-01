package Tests;

import ElevatorSubSystem.ElevatorMotor;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Elevator Motor JUnit Test Case
 * 
 * @author Muneeb Nasir
 */
public class ElevatorMotorTest {

	@Test
	public void enumStringTest() {
		assertEquals("UPWARD", ElevatorMotor.UPWARD.toString());
		assertEquals("DOWNWARD", ElevatorMotor.DOWNWARD.toString());
		assertEquals("STOP", ElevatorMotor.STOP.toString());
	}

}