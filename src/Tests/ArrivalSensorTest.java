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
	private ArrivalSensor arrivalSensorTest = new ArrivalSensor();

	@Test
	public void testArrivalSensor(){
		assertNotEquals("REACHED_FLOOR", arrivalSensorTest.getSensor());
		assertNotNull(arrivalSensorTest.getSensor());
	}

	@Test
	public void testSetArrivalSensor() {
		arrivalSensorTest.setArrivalSensor(true);
		assertNotEquals("REACHED_FLOOR", arrivalSensorTest.getSensor());
		assertNotNull(arrivalSensorTest.getSensor());

		arrivalSensorTest.setArrivalSensor(false);
		assertNotEquals("REACHED_FLOOR", arrivalSensorTest.getSensor());
		assertNotEquals("NOT_REACHED_FLOOR", arrivalSensorTest.getSensor());
		assertNotNull(arrivalSensorTest.getSensor());
	}

}