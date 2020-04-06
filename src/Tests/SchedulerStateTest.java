package Tests;

import SchedulerSubSystem.SchedulerState;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Scheduler State Test
 *
 * @author Muneeb Nasir
 */
public class SchedulerStateTest {

    /**
     * The Scheduler State Test
     */
    @Test
    public void enumStringTest() {
        assertEquals("E_MOVING", SchedulerState.E_MOVING.toString());
        assertEquals("E_ARRIVED", SchedulerState.E_ARRIVED.toString());
        assertEquals("E_BOARDED", SchedulerState.E_BOARDED.toString());
        assertEquals("E_REQUESTED", SchedulerState.E_REQUESTED.toString());
        assertEquals("IDLE", SchedulerState.IDLE.toString());
        assertNotNull(SchedulerState.IDLE.toString());
    }

}