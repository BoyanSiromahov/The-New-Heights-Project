/*
 * 
 */
package SchedulerSubSystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Util.Parser;

public class Scheduler implements Runnable {
	boolean elevatorRequest = false;
	boolean elevatorArrived = false;
	boolean elevatorBoarded = false;
	int arrivedFloor = 0;
	LinkedList eventQ = new LinkedList<Parser>();

	public Scheduler() {
	}

	public synchronized Parser getEvent() {
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
		return (Parser) eventQ.remove();
	}

	public synchronized void elevatorArrived(int floorNum) {
		arrivedFloor = floorNum;
		elevatorArrived = true;
		notifyAll();
	}

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

	public synchronized void elevatorRequest(Parser p) {
		System.out.println("Request recieved");
		elevatorRequest = true;
		eventQ.add(p);
		notifyAll();
	}

	public synchronized void elevatorBoarded() {
		elevatorBoarded = true;
		notifyAll();
	}

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

	@Override
	public void run() {

	}

}
