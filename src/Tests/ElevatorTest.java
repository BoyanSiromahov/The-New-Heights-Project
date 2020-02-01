package Tests;

import ElevatorSubSystem.Elevator;
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
		System.out.println("\nJUnit Test: The Elevator Class successfully identifies the Invalid Request\n");
		assertFalse(testElevator.receiveAndCheckSchedulerRequest());
	}

	@After
	public void tearDown() {
		testElevator = null;
		testElevatorScheduler = null;
	}
}