/*
 * 
 */
package SchedulerSubSystem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Util.Parser;


public class Scheduler implements Runnable{
	boolean elevatorRequest = false;
	boolean elevatorArrived = false;
	boolean elevatorBoarded = false;
	int arrivedFloor = 0;
	LinkedList eventQ = new LinkedList<Parser>(); 
	
	
	public Scheduler() {
	}
	
	
	public synchronized Parser getEvent() {
		while(!elevatorRequest) {//muneeb
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		notifyAll();
		elevatorRequest = false;
		System.out.println("sending event to elevator:\n" + eventQ.peek());
		return (Parser) eventQ.remove();	
	}
	
	public synchronized void elevatorArrived(int floorNum) {
		System.out.println("Elevator arrived at floor: " + floorNum);//muneeb
		arrivedFloor = floorNum;
		elevatorArrived = true;
		notifyAll();	
	}
	
	public synchronized int getArrivedFloor() {
		while(!elevatorArrived) {
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
		elevatorRequest = true;
		eventQ.add(p);
		notifyAll();
	}
	
	public synchronized void elevatorBoarded() {
		elevatorBoarded = true;
		System.out.println("elevator leaving to dest");//muneeb
		notifyAll();
	}
	
	public synchronized void elevatorReady() {
		while(!elevatorBoarded) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		elevatorBoarded = false;
		System.out.println("elevator leaving to dest");	//muneeb
	}	

	@Override
	public void run() {
		
		
	}


}
