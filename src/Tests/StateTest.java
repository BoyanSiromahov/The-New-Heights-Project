package Tests;

import ElevatorSubSystem.State;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateTest {

    @Test
    public void enumStringTest() {
        assertEquals("ELEVATOR_MOVING", State.ELEVATOR_MOVING.toString());
        assertEquals("ELEVATOR_STOPPED", State.ELEVATOR_STOPPED.toString());
        assertEquals("DOORS_OPENING", State.DOORS_OPENING.toString());
        assertEquals("DOORS_CLOSING", State.DOORS_CLOSING.toString());
        assertEquals("ELEVATOR_IDLE_WAITING_FOR_REQUEST", State.ELEVATOR_IDLE_WAITING_FOR_REQUEST.toString());
        assertEquals("SCHEDULER_SENDS_REQUEST_TO_ELEVATOR", State.SCHEDULER_SENDS_REQUEST_TO_ELEVATOR.toString());
        assertEquals("SCHEDULER_RECEIVES_REQUEST_FROM_FLOOR", State.SCHEDULER_RECEIVES_REQUEST_FROM_FLOOR.toString());
    }
}