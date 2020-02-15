package Tests;

import ElevatorSubSystem.Elevator;
import ElevatorSubSystem.ElevatorState;
import SchedulerSubSystem.Scheduler;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Elevator Class JUnit Test Case
 * 
 * @author Muneeb Nasir
 */
public class ElevatorTest {

	private Elevator testElevator;
	private Scheduler testElevatorScheduler;

	@Before
	public void setUp() {
		testElevatorScheduler = new Scheduler();
		testElevator = new Elevator(1, testElevatorScheduler);
	}

	@Test
	public void receiveAndCheckSchedulerRequest() {
		System.out.println("\n[JUNIT TESTING]\n");
		System.out.println("\nJUnit Test: The Elevator Class successfully identifies the Invalid Request\n");
		assertFalse(testElevator.receiveAndCheckSchedulerRequest());
	}

	@Test
	public void testGetElevatorState() {
		assertNotNull(testElevator.getElevatorState());
		assertNotEquals(ElevatorState.ELEVATOR_MOVING, testElevator.getElevatorState());
		assertEquals("\nElevator State Test: Idle-Waiting for request",
				ElevatorState.ELEVATOR_IDLE_WAITING_FOR_REQUEST, testElevator.getElevatorState());
	}

	@After
	public void tearDown() {
		testElevator = null;
		testElevatorScheduler = null;
	}
}