package Tests;

import ElevatorSubSystem.Direction;
import Util.CallEvent;
import Util.Faults;
import java.util.Date;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Call Event JUnit Test Case
 *
 * @author Muneeb Nasir
 */
public class CallEventTest {

    private CallEvent testCallEvent = new CallEvent(new Date(), 1, 5, Direction.UP, null);

    @Test
    public void testGetStartTime() {
        assertNotEquals("00:00:10",testCallEvent.getStartTime());
        assertNotNull(testCallEvent.getStartTime());
    }

    @Test
    public void testSetStartTime() {
        testCallEvent.setStartFloor(20);
        assertNotEquals("00:00:10",testCallEvent.getStartTime());
        assertNotNull(testCallEvent.getStartTime());
    }

    @Test
    public void testGetStartFloor() {
        assertEquals(1,testCallEvent.getStartFloor());
        assertNotEquals(0, testCallEvent.getStartFloor());
    }

    @Test
    public void testSetStartFloor() {
        testCallEvent.setStartFloor(2);
        assertNotEquals(4,testCallEvent.getStartFloor());
        assertNotEquals(0, testCallEvent.getStartFloor());
        assertEquals(2, testCallEvent.getStartFloor());
    }

    @Test
    public void testGetEndFloor() {
        assertNotEquals(4,testCallEvent.getEndFloor());
        assertNotEquals(0, testCallEvent.getEndFloor());
        assertEquals(5, testCallEvent.getEndFloor());
    }

    @Test
    public void testSetEndFloor() {
        testCallEvent.setEndFloor(3);
        assertNotEquals(5,testCallEvent.getEndFloor());
        assertNotEquals(1, testCallEvent.getEndFloor());
        assertEquals(3, testCallEvent.getEndFloor());
    }

    @Test
    public void testGetDirection() {
        assertNotEquals(Direction.DOWN,testCallEvent.getDirection());
        assertEquals(Direction.UP, testCallEvent.getDirection());
    }

    @Test
    public void testSetDirection() {
        testCallEvent.setDirection(Direction.DOWN);
        assertNotEquals(Direction.UP,testCallEvent.getDirection());
        assertEquals(Direction.DOWN, testCallEvent.getDirection());
    }

    @Test
    public void testGetFault() {
    }

    @Test
    public void testSetFault() {
        testCallEvent.setFault(Faults.ELEVATOR);
        assertNotEquals(Faults.SENSOR,testCallEvent.getFault());
        assertEquals(Faults.ELEVATOR, testCallEvent.getFault());
        assertNotNull(testCallEvent.getFault());
    }

    @Test
    public void testToString() {
        assertNotNull(testCallEvent.toString());
        assertNotEquals("", testCallEvent.toString());
    }
}