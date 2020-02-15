/*
 * 
 */
package FloorSubSystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import SchedulerSubSystem.Scheduler;
import Util.CallEvent;

/**
 * This class represents the floor subsystem. It is in charge of tracking time and send events. 
 * Events are parsed from the CSV file.
 * 
 * @author Samantha Tripp
 *
 */
public class Floor implements Runnable {
	private LinkedList<Integer> eventQ = new LinkedList<Integer>();
	private Scheduler scheduler;
	private List<CallEvent> floorEvents = new ArrayList<CallEvent>();

	/**
	 * The Floor object constructor. A Parser object is created that processes a CSV
	 * file, and this data is transferred to the scheduler.
	 * 
	 * @param scheduler
	 * @param floorEvents
	 */
	public Floor(Scheduler scheduler, List<CallEvent> floorEvents) {
		this.scheduler = scheduler;
		this.floorEvents = floorEvents;
	}

	
	/***
	 * This is the main method that is implemented from the Runnable interface. This
	 * method ensure that only one floor thread can process the request and
	 * respond accordingly. (Ensure The Operation is Atomic)
	 */
	@Override
	public void run() {

		long startTime = System.currentTimeMillis() / 1000;
		long elapsedTime = 0L;
		while (true) {
			elapsedTime = (System.currentTimeMillis() / 1000 - startTime); //record time since the program started in s
			if (floorEvents.size() > 0) {
				for (int i = 0; i < floorEvents.size(); i++) { //only send events while the csv queue exists
					double millis = floorEvents.get(i).getStartTime().getTime() - 3600000 * 5;
					// System.out.println("Comparing: " + millis/1000 + " and " + elapsedTime);
					if (millis / 1000 == elapsedTime) { //when time listed in the csv is the same as elapsed sent event
						System.out.println("Floor sending event to scheduler:\n" + floorEvents.get(i));
						scheduler.elevatorRequest(floorEvents.get(i));
						floorEvents.remove(i); //remove event from queue
					}
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//used to notify when people have boarded the elevator
			if (eventQ.size() > 0 && (Integer) eventQ.peek() == scheduler.getArrivedFloor()) {
				try {
					Thread.sleep(2300);//sleep for the number of s it takes to board
					//TO DO: add a variable that will indicate the number of people
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Elevator arrived, people have boarded");
				eventQ.pop();
				scheduler.elevatorBoarded();
			}

		}

	}

}
