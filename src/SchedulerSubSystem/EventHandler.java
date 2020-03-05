package SchedulerSubSystem;

import java.io.IOException;
import java.util.List;

import Util.CallEvent;
import Util.Parser;
import Util.UDPHelper;

public class EventHandler {
	
	private static final int FLOOR_PORT = 33;
	private static final int FLOOR_SCHEDULER_PORT = 29;
	private static final int ELEVATOR_SCHEDULER_PORT = 30;
	private static final int ELEVATOR_PORT = 22; 
	
	private Scheduler scheduler;
	private UDPHelper floorScheduler, elevatorScheduler;
	private List<CallEvent> list;
	private Parser p;
	
	public EventHandler(Scheduler s, List<CallEvent> list) {
		scheduler = s;
		this.list = list;
		
		// UDPHelper to send/receive from floor
		floorScheduler = new UDPHelper(FLOOR_SCHEDULER_PORT);
		
		//UDPHelper to send/receive from elevator
		elevatorScheduler = new UDPHelper(ELEVATOR_SCHEDULER_PORT);
		
		p = new Parser();
	}

	public CallEvent receiveFloorRequest() {
		CallEvent c;
		c = p.parseByteEvent(floorScheduler.receive());
		try {
			floorScheduler.send(floorScheduler.createReply(), FLOOR_PORT);
		} catch (IOException e) {
			
		}
		
		return c;
	}
	
	public void sendElevatorRequest(CallEvent c) {
		elevatorScheduler.send(elevatorScheduler.createMessage(c), ELEVATOR_PORT);
	}
	
	
	
}
