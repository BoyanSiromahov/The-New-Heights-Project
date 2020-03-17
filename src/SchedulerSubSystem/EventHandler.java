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
public class EventHandler implements Runnable{
	
	private static final int FLOOR_PORT = 33;
	private static final int FLOOR_SCHEDULER_PORT = 29;
	private static final int ELEVATOR_SCHEDULER_PORT = 30;
	
	private static UDPHelper udp;
	private static Parser p;
	private Scheduler s;
	
	public EventHandler(Scheduler s) {
		udp = new UDPHelper(FLOOR_SCHEDULER_PORT);
		p = new Parser();
		this.s = s;
	}
	
	@Override
	public void run() {
		while(true){
			CallEvent c;
			c = p.parseByteEvent(udp.receive(false));
			try {
				udp.send(udp.createReply(), FLOOR_PORT, false,
						InetAddress.getLocalHost());

			} catch (IOException e) {
	            e.printStackTrace();
			}
			s.addEvent(c);
			
		}
		
	}

	
}
