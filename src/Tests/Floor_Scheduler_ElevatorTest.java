package Tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import ElevatorSubSystem.Elevator;
import FloorSubSystem.Floor;
import SchedulerSubSystem.Scheduler;
import Util.Parser;
import org.junit.Test;

/**
 * Main Test Case For Elevator, Scheduler and Floor communication. Please run separately than the
 * JUnit Testing "TestAll"
 * 
 * @author Muneeb Nasir
 */
public class Floor_Scheduler_ElevatorTest {

	public static void main(String[] args) throws ParseException {
		Thread elevatorTest, floorTest;
		List<Parser> elevatorEventsTest = new ArrayList<Parser>();
		Parser parserTest = new Parser();
		elevatorEventsTest = parserTest.makeList(parserTest.csvReader());
		Scheduler schedulerTest = new Scheduler();


		floorTest = new Thread(new Floor(schedulerTest, elevatorEventsTest));
		elevatorTest = new Thread(new Elevator(1, schedulerTest), "Elevator");
		elevatorTest.start();
		floorTest.start();
	}

}