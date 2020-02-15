package Tests;

import org.junit.Test;

import ElevatorSubSystem.ElevatorState;

import static org.junit.Assert.*;

public class StateTest {

    @Test
    public void enumStringTest() {
        assertEquals("ELEVATOR_MOVING", ElevatorState.ELEVATOR_MOVING.toString());
        assertEquals("ELEVATOR_STOPPED", ElevatorState.ELEVATOR_STOPPED.toString());
        assertEquals("DOORS_OPENING", ElevatorState.DOORS_OPENING.toString());
        assertEquals("DOORS_CLOSING", ElevatorState.DOORS_CLOSING.toString());
        assertEquals("ELEVATOR_IDLE_WAITING_FOR_REQUEST", ElevatorState.ELEVATOR_IDLE_WAITING_FOR_REQUEST.toString());
        assertEquals("SCHEDULER_SENDS_REQUEST_TO_ELEVATOR", ElevatorState.SCHEDULER_SENDS_REQUEST_TO_ELEVATOR.toString());
        assertEquals("SCHEDULER_RECEIVES_REQUEST_FROM_FLOOR", ElevatorState.SCHEDULER_RECEIVES_REQUEST_FROM_FLOOR.toString());
    }
}