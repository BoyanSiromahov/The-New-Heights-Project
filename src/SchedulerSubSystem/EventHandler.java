package SchedulerSubSystem;

import java.util.List;

import Util.CallEvent;
import Util.Parser;
import Util.UDPHelper;

public class EventHandler implements Runnable {
	UDPHelper udp;
	List<CallEvent> list;
	Parser p;
	
	public EventHandler(List<CallEvent> list) {
		this.list = list;
		udp = new UDPHelper(10);
		p = new Parser();
	}
	
	@Override
	public void run() {
		while(true) {
			p.parseByteEvent(udp.receive());
		}
		
	}
	
}
