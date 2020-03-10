package Tests;

import SchedulerSubSystem.SchedulerState;
import ElevatorSubSystem.ElevatorState;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

        assertEquals("IDLE", SchedulerState.IDLE.toString());
        assertEquals("E_ARRIVED", SchedulerState.E_ARRIVED.toString());
        assertEquals("E_BOARDED", SchedulerState.E_BOARDED.toString());
        assertEquals("E_MOVING", SchedulerState.E_MOVING.toString());
        assertEquals("E_REQUESTED", SchedulerState.E_REQUESTED.toString());
    }
}