package SchedulerSubSystem;

import java.util.ArrayList;

import Util.CallEvent;

/**
 * This class is used to track the states of the elevators. 
 *
 * 
 * @author Boyan Siromahov
 */

public class ElevatorState {
	private int currFloor;// current floor the elevator is in
	private int state; //current state of the elevator TODO
	private ArrayList<CallEvent> floorQueue = new ArrayList<CallEvent>();
	private boolean goingUp; // 0 for up, 1 for down
	
	public ElevatorState() {
		currFloor = 1;
		state = 0;
		goingUp = false;
	}
	
	public void setGoingUp(boolean up) {
		goingUp = up;
	}
	
	public boolean isGoingUp() {
		return goingUp;
	}
	
	public void changeFloor(int newFloor) {
		currFloor = newFloor;
	}
	
	public int getCurFloor() {
		return currFloor;
	}
	
	public void changeState(int newState) {
		state = newState;
	}
	
	public int getState() {
		return state;
	}
	
	public void addEvent(CallEvent c) {
		floorQueue.add(c);
	}
	
	public void removeEvent(int floor) {
		for(CallEvent c : floorQueue) {
			if(c.getEndFloor() == floor);
				floorQueue.remove(c);
		}
	}

}