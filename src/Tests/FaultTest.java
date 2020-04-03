package Tests;


import Util.Faults;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Elevator Faults JUnit Test Case
 *
 * @author Muneeb Nasir
 */
public class FaultTest {

    @Test
    public void enumStringTest() {
        assertEquals("DOOR", Faults.DOOR.toString());
        assertEquals("SENSOR", Faults.SENSOR.toString());
        assertEquals("ELEVATOR", Faults.ELEVATOR.toString());
        assertNotNull(Faults.ELEVATOR.toString());
    }
}