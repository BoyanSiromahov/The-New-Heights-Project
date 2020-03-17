package SchedulerSubSystem;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import ElevatorSubSystem.Direction;

/**
 * The main class for the scheduler. This class is used as an middle man to 
 * receive and forward events from the Floor class and the Elevator class. It also
 * keeps track of all events hit and queues them up accordingly.
 * 
 * @author Boyan Siromahov
 */

import SchedulerSubSystem.ElevatorState;
import Util.CallEvent;

public class Scheduler {
	
	
	private ArrayList<ElevatorState> elevatorList = new ArrayList<ElevatorState>();
	private EventHandler eventHandler;
	
	public Scheduler() {
		elevatorList.add(new ElevatorState());
		elevatorList.add(new ElevatorState());
		elevatorList.add(new ElevatorState());
		elevatorList.add(new ElevatorState());
		elevatorList.add(new ElevatorState());
		elevatorList.add(new ElevatorState());
		
		for(ElevatorState e: elevatorList) {
			e.changeFloor(5);
		}
		
		eventHandler = new EventHandler(this);
		eventHandler.run();
	}

	public void addEvent(CallEvent c) {
		int endFloor = c.getEndFloor();
		int bestDelta = 9999;
		int bestElevator = -1;
		if (elevatorList.size() > 0) {
			for(int i = 0; i < elevatorList.size(); i++) {
				int floorDelta = c.getStartFloor() - elevatorList.get(i).getCurFloor();
				
				if(c.getDirection() == Direction.UP && floorDelta > 0 && elevatorList.get(i).isGoingUp()) {//should be negative since elevator is below
					if(bestDelta == 9999) {
						bestDelta = floorDelta;
						bestElevator = i;
					}
					
					if(floorDelta > bestDelta) {
						bestDelta = floorDelta;
						bestElevator = i;
					}
								
				}else if(c.getDirection() == Direction.DOWN && floorDelta < 0 && !elevatorList.get(i).isGoingUp()) { //should be positive since elevator is above
					if(bestDelta == 9999) {
						bestDelta = floorDelta;
						bestElevator = i;
					}
					
					if(floorDelta < bestDelta) {
						bestDelta = floorDelta;
						bestElevator = i;
					}
					
				}else if(elevatorList.get(i).getState() == 0 && floorDelta == 0) {//check if elevator is idle and at the desired floor
					System.out.println("sending elevator: " + i + "\n");
					elevatorList.get(i).setGoingUp(false);
					elevatorList.get(i).changeFloor(3);
					return;
				}
			}
			if(bestElevator == -1) {
				//TODO some error handling
			}else {
				System.out.println("sending elevator: " + bestElevator + "\n");
				 
			}
		}
	}

	public static void main(String[] args){
		Scheduler s = new Scheduler();
	}
}
