package SchedulerSubSystem;

/**
 * The main class for the scheduler. This class is used as an middle man to 
 * recieve and forward events from the Floor class and the Elevator class. It also
 * keeps track of all events hit and queues them up accordingly.
 * 
 * @author Boyan Siromahov
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Util.CallEvent;

public class Scheduler implements Runnable {
	boolean elevatorRequest = false;
	boolean elevatorArrived = false;
	boolean elevatorBoarded = false;
	int arrivedFloor = 0;
	LinkedList eventQ = new LinkedList<CallEvent>();

	public Scheduler() {
	}

	/***
	 * This function is used to forward the elevator request to the elevator from
	 * the floor
	 */
	public synchronized CallEvent getEvent() {
		while (!elevatorRequest) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		notifyAll();
		elevatorRequest = false;
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
		elevatorArrived = true;
		notifyAll();
	}

	/***
	 * This function is used to tell the floor which floor the elevator has arrived
	 * at
	 */
	public synchronized int getArrivedFloor() {
		while (!elevatorArrived) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		elevatorArrived = false;
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
		elevatorRequest = true;
		eventQ.add(p);
		notifyAll();
	}

	/***
	 * This function is used to notify the scheduler to flip the boarded flag to
	 * true which will then allow the elevator to move
	 */
	public synchronized void elevatorBoarded() {
		elevatorBoarded = true;
		notifyAll();
	}

	/***
	 * This function is used to notify that the elevator can start moving once all
	 * passengers are boarded. This is sent from floor.
	 */
	public synchronized void elevatorReady() {
		while (!elevatorBoarded) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		elevatorBoarded = false;
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
