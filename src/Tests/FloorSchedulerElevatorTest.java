package Tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import ElevatorSubSystem.Elevator;
import FloorSubSystem.Floor;
import SchedulerSubSystem.Scheduler;
import Util.CallEvent;
import Util.Parser;
import org.junit.Test;


/**
 * Main Test Case For Elevator, Scheduler and Floor communication. Please run separately than the
 * JUnit Testing "TestAll"
 * 
 * @author Muneeb Nasir
 */
public class FloorSchedulerElevatorTest {

	public static void main(String[] args) throws ParseException {
		Thread elevatorTest, floorTest;
		List<CallEvent> elevatorEventsTest = new ArrayList<CallEvent>();
		Parser parserTest = new Parser();
		elevatorEventsTest = parserTest.makeList(Parser.csvReader());
		Scheduler schedulerTest = new Scheduler();


		floorTest = new Thread(new Floor(schedulerTest, elevatorEventsTest));
		elevatorTest = new Thread(new Elevator(1, schedulerTest), "Elevator");
		elevatorTest.start();
		floorTest.start();
	}

}