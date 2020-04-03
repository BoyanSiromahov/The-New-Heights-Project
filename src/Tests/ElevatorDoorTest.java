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

	private ElevatorDoor testElevatorDoor = new ElevatorDoor();

	@Test
	public void testElevatorDoor(){
		assertNotNull(testElevatorDoor.getElevatorDoor());
		assertNotEquals("CLOSE", testElevatorDoor.getElevatorDoor());
	}

	@Test
	public void testGetElevatorDoor(){
		assertNotEquals("CLOSE", testElevatorDoor.getElevatorDoor());
		assertNotNull(testElevatorDoor.getElevatorDoor());
	}

	@Test
	public void testSetElevatorDoor(){
		testElevatorDoor.setElevatorDoor(true);
		assertNotEquals("OPEN", testElevatorDoor.getElevatorDoor());
		assertNotNull(testElevatorDoor.getElevatorDoor());

		testElevatorDoor.setElevatorDoor(false);
		assertNotEquals("CLOSE", testElevatorDoor.getElevatorDoor());
		assertNotNull(testElevatorDoor.getElevatorDoor());
	}

	@Test
	public void testDoorClosed(){
		testElevatorDoor.doorClosed();
		assertNotEquals("OPEN", testElevatorDoor.getElevatorDoor());
		assertNotNull(testElevatorDoor.getElevatorDoor());
	}

}