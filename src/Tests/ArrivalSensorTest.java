package Tests;

import ElevatorSubSystem.ArrivalSensor;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Arrival Sensor Enum JUnit Test Case
 * 
 * @author Muneeb Nasir
 */
public class ArrivalSensorTest {

	@Test
	public void enumStringTest() {
		assertEquals("REACHED_FLOOR", ArrivalSensor.REACHED_FLOOR.toString());
		assertEquals("NOT_REACHED_FLOOR", ArrivalSensor.NOT_REACHED_FLOOR.toString());
	}

}