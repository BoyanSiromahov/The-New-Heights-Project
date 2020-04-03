package Tests;

import ElevatorSubSystem.ElevatorMotor;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Elevator Motor JUnit Test Case
 * 
 * @author Muneeb Nasir
 */
public class ElevatorMotorTest {

	private ElevatorMotor testElevatorMovement = new ElevatorMotor();

	@Test
	public void testElevatorMotor(){
		assertNotEquals("MOTOR_UP", testElevatorMovement.getElevatorMovement());
		assertNotNull(testElevatorMovement.getElevatorMovement());
	}

	@Test
	public void testMotorState() {
		assertNotEquals("MOTOR_DOWN", testElevatorMovement.getElevatorMovement());
		assertNotNull(testElevatorMovement.getElevatorMovement());
	}

	@Test
	public void testSetElevatorMovement() {

		assertNotEquals(false, testElevatorMovement.setElevatorMovement("UP"));
		assertTrue(testElevatorMovement.setElevatorMovement("UP"));
		assertNotNull(testElevatorMovement.getElevatorMovement());

		assertNotEquals(false, testElevatorMovement.setElevatorMovement("DOWN"));
		assertTrue(testElevatorMovement.setElevatorMovement("DOWN"));

		assertNotEquals(false, testElevatorMovement.setElevatorMovement("STOP"));
		assertTrue(testElevatorMovement.setElevatorMovement("STOP"));
	}

	@Test
	public void testMovingDown() {
		testElevatorMovement.setElevatorMovement("DOWN");
		assertEquals(true, testElevatorMovement.movingDown());
		assertNotEquals(false, testElevatorMovement.movingDown());
		assertTrue(testElevatorMovement.movingDown());
	}

	@Test
	public void testElevatorMotorStates(){

		testElevatorMovement.setElevatorMovement("UP");
		assertTrue(testElevatorMovement.movingUp());

		testElevatorMovement.setElevatorMovement("STOP");
		assertTrue(testElevatorMovement.elevatorStopped());
	}
}