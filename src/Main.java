
/**
	 * @author Shaun Gordon
	 */
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ElevatorSubSystem.Elevator;
import FloorSubSystem.Floor;
import SchedulerSubSystem.Scheduler;
import Util.Parser;

/**
 * The Class Main.
 */
public class Main
{
	public static void main(String[] args) throws ParseException 
	{
		List<Parser> elevatorEvents = new ArrayList<Parser>();
		Parser parser = new Parser();
		elevatorEvents = parser.makeList(parser.csvReader());

		Thread elevatorA;
		Thread floor;
		Scheduler ss = new Scheduler();
		
		
		floor = new Thread(new Floor(ss, elevatorEvents));
		elevatorA = new Thread(new Elevator(1, ss),"Elevator");
		elevatorA.start();
		floor.start();
	}
}
