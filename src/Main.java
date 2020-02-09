
/**
 * @author Shaun Gordon
 */
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ElevatorSubSystem.Elevator;
import FloorSubSystem.Floor;
import SchedulerSubSystem.Scheduler;
import Util.CallEvent;
import Util.Parser;

/**
 * The Class Main.
 */
public class Main {
	public static void main(String[] args) throws ParseException 
	{
		Parser parser = new Parser();
		List<CallEvent> elevatorEvents = new ArrayList<CallEvent>();
		List<String[]> csvData = new ArrayList<String[]>();
		csvData = Parser.csvReader();
		
		elevatorEvents = parser.makeList(csvData);
		
		
		Thread elevatorA;
		Thread floor;
		Scheduler ss = new Scheduler();

		floor = new Thread(new Floor(ss, elevatorEvents));
		elevatorA = new Thread(new Elevator(1, ss), "Elevator");
		elevatorA.start();
		floor.start();
	}
}
