package SchedulerSubSystem;

/**
 * The main class for the scheduler. This class is used as an middle man to 
 * receive and forward events from the Floor class and the Elevator class. It also
 * keeps track of all events hit and queues them up accordingly.
 * 
 * @author Boyan Siromahov
 */

import java.util.LinkedList;

import Util.CallEvent;

public class Scheduler implements Runnable {
	int arrivedFloor = 0;

	LinkedList<CallEvent> eventQ = new LinkedList<CallEvent>();
	SchedulerState ss = SchedulerState.IDLE;
	
	public Scheduler() {
	}

	/***
	 * This function is used to forward the elevator request to the elevator from
	 * the floor
	 */
	public synchronized CallEvent getEvent() {
		while (ss != SchedulerState.E_REQUESTED) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		notifyAll();
		ss = SchedulerState.E_MOVING;
		System.out.println("Scheduler sending event to elevator:\n" + eventQ.peek());
		return (CallEvent) eventQ.remove();
	}

	/***
	 * This function is used to tell the floor which floor the elevator has arrived
	 * at
	 * 
	 * @param floorNum - the floor number that the elevator has reached. 
	 */
	public synchronized void elevatorArrived(int floorNum) {
		arrivedFloor = floorNum;
		ss = SchedulerState.E_ARRIVED;
		notifyAll();
	}

	/***
	 * This function is used to tell the floor which floor the elevator has arrived
	 * at
	 */
	public synchronized int getArrivedFloor() {
		while (ss != SchedulerState.E_ARRIVED) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ss = SchedulerState.E_MOVING;
		notifyAll();
		return arrivedFloor;
	}

	/***
	 * This function is used to notify the scheduler to flip the boarded flag to
	 * true which will then allow the elevator to move
	 * 
	 * @param p - the elevator request that is sent from the floor.
	 */
	public synchronized void elevatorRequest(CallEvent p) {
		System.out.println("Request recieved");
		ss = SchedulerState.E_REQUESTED;
		eventQ.add(p);
		notifyAll();
	}

	/***
	 * This function is used to notify the scheduler to flip the boarded flag to
	 * true which will then allow the elevator to move
	 */
	public synchronized void elevatorBoarded() {
		ss = SchedulerState.E_BOARDED;
		notifyAll();
	}

	/***
	 * This function is used to notify that the elevator can start moving once all
	 * passengers are boarded. This is sent from floor.
	 */
	public synchronized void elevatorReady() {
		while (ss != SchedulerState.E_BOARDED) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ss = SchedulerState.E_MOVING;
	}
	
	public synchronized void elevatorFinished() {
		ss = SchedulerState.IDLE;
	}

	/***
	 * This is the main method that is implemented from the Runnable interface. This
	 * method ensure that only one scheduler thread can process the request and
	 * respond accordingly. (Ensure The Operation is Atomic)
	 * 
	 * Currently not in use, will be used in later iterations.
	 */
	@Override
	public void run() {
	}

}
