package SchedulerSubSystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import Util.CallEvent;
import Util.Parser;
import Util.UDPHelper;

/**
 * The Event Handler Class is Associated With the Scheduler and component of the scheduler. The scheduler receives
 * the UDP Packets from-to the elevators/floors
 */
public class EventHandler {
	
	private static final int FLOOR_PORT = 33;
	private static final int FLOOR_SCHEDULER_PORT = 29;
	private static final int ELEVATOR_SCHEDULER_PORT = 30;

	private Scheduler scheduler;
	private UDPHelper floorScheduler, elevatorScheduler;
	private List<CallEvent> list;
	private Parser p;

    /**
     * The constructor for the event handler
     * @param s The connected scheduler class
     * @param list, The associated list of requests/commands
     */
	public EventHandler(Scheduler s, List<CallEvent> list) {
		scheduler = s;
		this.list = list;

		 try {
            InetAddress hostAddress = InetAddress.getLocalHost();
            // UDPHelper to send/receive from floor
            floorScheduler = new UDPHelper(FLOOR_SCHEDULER_PORT);

            //UDPHelper to send/receive from elevator
            elevatorScheduler = new UDPHelper(ELEVATOR_SCHEDULER_PORT);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        p = new Parser();
		p.ipAddressReader(); //Extract The IP Addresses
	}

    /**
     * Receive the floor request from the Floor and respond back with acknowledgement
     * stating the floor request has been received by the "Scheduler"
     * @return c, The event that has been requested by the Floor
     */
	public CallEvent receiveFloorRequest() {
		CallEvent c;
		c = p.parseByteEvent(floorScheduler.receive(false));
		try {
		    if(p.systemAddresses.isEmpty()){
                floorScheduler.send(floorScheduler.createReply(), FLOOR_PORT, false,
                        InetAddress.getLocalHost());
            }else{
                floorScheduler.send(floorScheduler.createReply(), FLOOR_PORT, false,
                        InetAddress.getByName(p.systemAddresses.get(0)));
            }

		} catch (IOException e) {
            e.printStackTrace();
		}
		
		return c;
	}

    /**
     * Transmits the corresponding request event to the Elevator (Selected Elevator)
     * @param c, The request that is to be transmitted
     * @param portNum, The port number of the elevator thread
     * @throws UnknownHostException, Exception if the specified IP Address is Invalid
     */
	public void sendElevatorRequest(CallEvent c, int portNum) throws UnknownHostException {
		System.out.println("Sending Elevator Request");
        if(p.systemAddresses.isEmpty()) {
            elevatorScheduler.send(elevatorScheduler.createMessage(c), portNum, false,
                    InetAddress.getLocalHost());
        }else {
            elevatorScheduler.send(elevatorScheduler.createMessage(c), portNum, false,
                    InetAddress.getByName(p.systemAddresses.get(2)));
        }

	}

    /**
     * Receives the Elevator State and status
     * @return status, The elevator decoded status
     */
    public byte[] receiveElevatorStatus() {

        byte[] status = elevatorScheduler.receive(true);
        return status;
    }

    public void replyToElevatorStatus(byte[] response, int elevatorPort) throws UnknownHostException {
        elevatorScheduler.send(response, elevatorPort, false, InetAddress.getByName("192.168.56.1"));
    }

}
