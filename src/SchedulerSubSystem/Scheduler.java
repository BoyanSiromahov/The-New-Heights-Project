package SchedulerSubSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.Collections;

/**
 * The main class for the scheduler. This class is used as an middle man to 
 * receive and forward events from the Floor class and the Elevator class. It also
 * keeps track of all events hit and queues them up accordingly.
 * 
 * @author Boyan Siromahov
 */

import java.util.LinkedList;
import java.util.List;

import Util.ByteArray;
import Util.CallEvent;
import Util.UDPHelper;

public class Scheduler{
	
	public static final int SCHEDULER_PORT = 30;
	
	private int arrivedFloor;
	private List<CallEvent> eventQ;
	private SchedulerState ss;
	private UDPHelper schedulerHelper;
	private ByteArray byteArray;
	
	public Scheduler() {
		this.arrivedFloor = 0;
		this.eventQ = Collections.synchronizedList(new LinkedList<CallEvent>());
		this.ss = SchedulerState.IDLE;
		this.schedulerHelper = new UDPHelper(SCHEDULER_PORT);
		this.byteArray = new ByteArray();
		
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
		//System.out.println("Scheduler sending event to elevator:\n" + eventQ.peek());
		return (CallEvent) eventQ.remove(0);
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
	public synchronized void elevatorRequest() {
		//CallEvent c = byteArray.decodeMessage(schedulerHelper.receive());
		//eventQ.add(c);
		System.out.println("QWEQWE ");
		byte msg[] = new byte[] {1};
		schedulerHelper.send(msg, 33);
		ss = SchedulerState.E_REQUESTED;
	} 
	

	/***
	 * This function is used to notify the scheduler to flip the boarded flag to
	 * true which will then allow the elevator to move
	 */
	public synchronized void elevatorBoarded() {
		ss = SchedulerState.E_BOARDED;
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

	
	public static void main(String[] args) throws ParseException 
	{
		Scheduler test = new Scheduler();
		
		//test.run();
	}

}
