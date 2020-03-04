/*
 * 
 */
package FloorSubSystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;


import SchedulerSubSystem.Scheduler;
import Util.CallEvent;
import Util.UDPHelper;

/**
 * This class represents the floor subsystem. It is in charge of tracking time and send events. 
 * Events are parsed from the CSV file.
 * 
 * @author Samantha Tripp
 *
 */
public class Floor implements Runnable {
	private LinkedList<Integer> eventQ;
	private Scheduler scheduler;
	private List<CallEvent> floorEvents;
	
	private UDPHelper floorHelper;
	public static final int floorPort = 30;
	private byte[] sendData;

	/**
	 * The Floor object constructor. A Parser object is created that processes a CSV
	 * file, and this data is transferred to the scheduler.
	 * 
	 * @param scheduler
	 * @param floorEvents
	 */
	public Floor(Scheduler scheduler, List<CallEvent> floorEvents) {
		this.eventQ = new LinkedList<Integer>();
		this.scheduler = scheduler;
		this.floorEvents = floorEvents;
		this.floorHelper = new UDPHelper(floorPort);
	}
	
	/**
	 * Helper method to convert CallEvent objects to byte array messages for
	 * use in constructing DatagramPackets.
	 * 
	 * @param floorEvent
	 */
	private void createMessage(CallEvent floorEvent) {
		
		// Prepare byte array of data to send
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteStream);
			
			// Convert floor event to bytes
			out.writeObject(floorEvent);
			out.flush();
			
			// Create byte array
			sendData = byteStream.toByteArray();
			byteStream.close();
		
		} catch (IOException e) {
			System.out.println("Floor error: ");
			e.printStackTrace();
			System.exit(1);
		}
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
						createMessage(floorEvents.get(i));
						floorHelper.sendAndReceive(sendData, Scheduler.schedulerPort);
						//scheduler.elevatorRequest(floorEvents.get(i));
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
