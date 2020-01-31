/*
 * 
 */
package FloorSubSystem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ElevatorSubSystem.Direction;
import SchedulerSubSystem.Scheduler;
import Util.Parser;


/**
 * 
 * 
 * @author Samantha Tripp
 *
 */
public class Floor implements Runnable {
	private LinkedList eventQ = new LinkedList<Integer>(); 
	private Scheduler scheduler;
	private List<Parser> floorEvents = new ArrayList<Parser>();  

    /**
     * The Floor object constructor. A Parser object is created that processes a CSV file, and this
     * data is transferred to the scheduler.
     * 
     * @param scheduler
     * @param floorEvents
     */
    public Floor(Scheduler scheduler, List<Parser> floorEvents){
       this.scheduler = scheduler;
       this.floorEvents = floorEvents;
    }
    
    
    

	@Override
	public void run() {
		
	long startTime = System.currentTimeMillis()/1000;	
		long elapsedTime = 0L;
		while(true) {
			elapsedTime = (System.currentTimeMillis()/1000 - startTime);
			if(floorEvents.size() > 0) {
				for(int i = 0; i < floorEvents.size(); i++) {
					double millis = floorEvents.get(i).getStartTime().getTime() - 3600000*5;
					System.out.println("Comparing: " + millis/1000 + " and " +  elapsedTime);
					if (millis/1000 == elapsedTime) {
						System.out.println("Sending event to scheduler:\n" + floorEvents.get(i));
						scheduler.elevatorRequest(floorEvents.get(i));
						floorEvents.remove(i);
					}
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(eventQ.size() > 0 && (Integer)eventQ.peek() == scheduler.getArrivedFloor()) {
				System.out.println("Elevator arrived, people have boarded");
				eventQ.pop();
				scheduler.elevatorBoarded();
			}
			
			
			
			
		}

		
	}
	
	

}
