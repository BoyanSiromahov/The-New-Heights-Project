package SchedulerSubSystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
	private InetAddress elevatorHost;
	private UDPHelper floorScheduler, elevatorScheduler;
	private List<CallEvent> list;
	private Parser p;
	
	public EventHandler(Scheduler s, List<CallEvent> list) {
		scheduler = s;
		this.list = list;

		 try {
            InetAddress hostAddress = InetAddress.getLocalHost();
            // UDPHelper to send/receive from floor
            floorScheduler = new UDPHelper(FLOOR_SCHEDULER_PORT, hostAddress);

            //UDPHelper to send/receive from elevator
            elevatorScheduler = new UDPHelper(ELEVATOR_SCHEDULER_PORT, hostAddress);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

		
		p = new Parser();
	}

	public CallEvent receiveFloorRequest() {
		CallEvent c;
		c = p.parseByteEvent(floorScheduler.receive());
		try {
			floorScheduler.send(floorScheduler.createReply(), FLOOR_PORT, false);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		return c;
	}
	
	public void sendElevatorRequest(CallEvent c, int portNum) {
		System.out.println("Sending Elevator Request");
		elevatorScheduler.send(elevatorScheduler.createMessage(c), portNum, false);
	}

    public byte[] receiveElevatorStatus() {

        byte[] status = elevatorScheduler.receive();
        return status;
    }

    public void replyToElevatorStatus(byte[] response, int elevatorPort) {
        elevatorScheduler.send(response, elevatorPort, false);
    }
	
	
	
}
