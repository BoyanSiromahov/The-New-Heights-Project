package Tests;


import static org.junit.jupiter.api.Assertions.*;

import ElevatorSubSystem.Direction;
import ElevatorSubSystem.Elevator;
import Util.CallEvent;
import Util.Faults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;


/**
 * A test class to test the Elevator functionality
 *
 * @author Muneeb Nasir
 *
 */
public class ElevatorTest {
    private Elevator elevator;

    /**
     * Initial test setup
     *
     * @throws Exception Ant exceptions thrown by the tests
     */
    @BeforeEach
    public void setUp() throws Exception {
        elevator = new Elevator(1,10);
    }

    /**
     * JUnit Test For Completely Testing the Elevator Functionality
     */
    @Test
    public void testElevator() {
        elevator.moveElevator(2, Direction.DOWN);
        assertEquals("STOP",elevator.getElevatorMotorState());
        assertEquals("REACHED_FLOOR",elevator.elevatorArrivedOnFloor());
        elevator.moveElevator(5, Direction.UP);
        assertEquals("STOP",elevator.getElevatorMotorState());
        assertEquals("REACHED_FLOOR",elevator.elevatorArrivedOnFloor());
        elevator.moveElevator(1, Direction.DOWN);
        assertEquals("STOP",elevator.getElevatorMotorState());
        elevator.moveElevator(3, Direction.UP);
        assertEquals("REACHED_FLOOR",elevator.elevatorArrivedOnFloor());

        // Fault Handling (Regular Request and Fault Handling)
        CallEvent testCallEvent1 = new CallEvent(new Date(), 1, 5, Direction.UP, null);
        assertFalse(elevator.checkFault(testCallEvent1));
        CallEvent testCallEvent2 = new CallEvent(new Date(), -1, 5, null, Faults.SENSOR);
        assertTrue(elevator.checkFault(testCallEvent2));
        CallEvent testCallEvent3 = new CallEvent(new Date(), 3, 1, Direction.DOWN, null);
        assertFalse(elevator.checkFault(testCallEvent3));
        CallEvent testCallEvent4 = new CallEvent(new Date(), -2, 5, null, Faults.ELEVATOR);
        assertTrue(elevator.checkFault(testCallEvent4));
    }


}