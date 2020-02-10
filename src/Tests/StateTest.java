package Tests;

import ElevatorSubSystem.State;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateTest {

    @Test
    public void enumStringTest() {
        assertEquals("ELEVATOR_MOVING", State.ELEVATOR_MOVING.toString());
        assertEquals("ELEVATOR_STOPPED", State.ELEVATOR_STOPPED.toString());
        assertEquals("DOORS_OPENING_PASSENGER_BOARDING", State.DOORS_OPENING_PASSENGER_BOARDING.toString());
        assertEquals("DOORS_CLOSING_PASSENGER_EXITING", State.DOORS_CLOSING_PASSENGER_EXITING.toString());
        assertEquals("ELEVATOR_IDLE", State.ELEVATOR_IDLE.toString());
    }
}