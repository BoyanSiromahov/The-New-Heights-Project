package SchedulerSubSystem;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * The main class for the scheduler. This class is used as an middle man to 
 * receive and forward events from the Floor class and the Elevator class. It also
 * keeps track of all events hit and queues them up accordingly.
 * 
 * @author Boyan Siromahov
 */

import ElevatorSubSystem.ElevatorMotor;
import ElevatorSubSystem.ElevatorState;
import Util.CallEvent;
import Util.Faults;

public class Scheduler {
	
	
	private int arrivedFloor;
	private List<CallEvent> eventQ;
	private Map<Integer, int[]> elevators;
	private EventHandler eventHandler;
	private SchedulerState ss;
	private ElevatorMotor e_Motor;
	
	public Scheduler() {
		e_Motor = new ElevatorMotor();
		arrivedFloor = 0;
		eventQ = Collections.synchronizedList(new LinkedList<CallEvent>());
		elevators = Collections.synchronizedMap(new HashMap<Integer, int[]>());
		eventHandler = new EventHandler(this, eventQ);
		ss = SchedulerState.IDLE;
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
	 */
	public synchronized void elevatorRequest() throws UnknownHostException {
	    // Associated Values
        // [0] -> Elevator Port Number
        // [1] -> The Current State of the Elevator
        // [2] -> The Current Floor Level of the Elevator
        // [3] -> The Current Direction of the Elevator Motor
		CallEvent c = eventHandler.receiveFloorRequest();
		eventQ.add(c);
        int bestElevator = 1;

        if(!elevators.isEmpty())
        {
            for (Map.Entry<Integer, int[]> pair : elevators.entrySet())
            {
            	if(eventQ.get(0).getStartFloor() < 0){
            		// Send The Fault Command Randomly To Any Available Elevator
					// TO DO (Handle Fault Based ON End Floor)
					if(eventQ.get(0).getFault() == Faults.DOOR){
						bestElevator = 2;
					}else if(eventQ.get(0).getFault() == Faults.SENSOR){
						bestElevator = 1;
					}else if(eventQ.get(0).getFault() == Faults.ELEVATOR){
						bestElevator = 2;
					}
				}
                else if(pair.getValue()[3] == e_Motor.state() && pair.getValue()[2] <=eventQ.get(0).getStartFloor())
                {
                    if(elevators.get(bestElevator)[2] - eventQ.get(0).getStartFloor() > pair.getValue()[2] - eventQ.get(0).getStartFloor())
                    {
                        bestElevator = pair.getKey();
                    }
                    if(pair.getValue()[2] == eventQ.get(0).getStartFloor())
					{
						bestElevator = pair.getKey();
					}
                }
                else if(pair.getValue()[3] == e_Motor.state() && pair.getValue()[2] >=eventQ.get(0).getStartFloor())
                {
                    if(elevators.get(bestElevator)[3] - eventQ.get(0).getStartFloor() >= pair.getValue()[2] - eventQ.get(0).getStartFloor())
                    {
                        bestElevator = pair.getKey();
                    }
                }
                else //IDLE
                {
                    if(elevators.get(bestElevator)[2] - eventQ.get(0).getStartFloor() > pair.getValue()[2] - eventQ.get(0).getStartFloor())
                    {
                        bestElevator = pair.getKey();
                    }
                }
            }

            eventHandler.sendElevatorRequest(eventQ.get(0), elevators.get(bestElevator)[0]);
            eventQ.clear(); //Clear The Request After The Command Has Been Executed

        }
//		if(!elevators.isEmpty()){
//
//            for (Map.Entry<Integer, int[]> pair : elevators.entrySet()) {
//
//                if(pair.getValue()[1] == ElevatorState.ELEVATOR_IDLE_WAITING_FOR_REQUEST.ordinal() &&
//                        pair.getValue()[3] == ElevatorMotor.STOP.ordinal()){
//                    System.out.println("Sending To Port: " + pair.getValue()[0]);
//                    eventHandler.sendElevatorRequest(eventQ.get(0), pair.getValue()[0]);
//                }
//            }
//        }



		ss = SchedulerState.E_REQUESTED;
	}

    /**
     * Associated with the receiving thread that os dedicated to receiving the elevator statuses
     */
	public void elevatorStatus(){

        // [0] -> Elevator Number
        // [1] -> Elevator Port Number
	    // [2] -> The Current State of the Elevator
        // [3] -> The Current Floor Level of the Elevator
        // [4] -> The Current Direction of the Elevator Motor

        byte[] elevatorStatus = eventHandler.receiveElevatorStatus();
        

        // Map with Elevator Number as a key and the array as value associated
        elevators.put((int) elevatorStatus[0], new int[]{elevatorStatus[1],
                elevatorStatus[2], elevatorStatus[3], elevatorStatus[4]});

        //Send Wait Response After The Receiving The State Of The Elevator
//        if (eventQ.isEmpty() && elevatorStatus[2] == ElevatorState.ELEVATOR_IDLE_WAITING_FOR_REQUEST.ordinal() &&
//                elevatorStatus[4] == e_Motor.state()){
//            //Reply With Response Of 0 Indicating Wait For Instructions
//
//            //eventHandler.replyToElevatorStatus(new byte[]{0}, elevatorStatus[1]);
//
//        }


    }

	/**
	 * Associated with the receiving thread that os dedicated to receiving the elevator Fault statuses
	 */
	public void handleElevatorFault(){

     	//[0] -> Elevator Fault Flag
		//[1] -> Elevator Number
      	//[2] -> The Current Status of the Elevator Fault
		//[3] -> Fault Type
     	//[4] -> Fault Time-Stamp

		byte[] elevatorFault = eventHandler.receiveElevatorFaultStatus();
		if(elevatorFault[0] == 11){

			if((int) elevatorFault[3] == Faults.SENSOR.ordinal() && elevatorFault[2] == 1){

				System.out.println("\n\u001B[31m" + String.format("[TIME: 00:00:%d] [SCHEDULER] [ELEVATOR-FAULT] " +
						"Elevator %d Soft-Fault Detected: Elevator Arrival Sensor",
						elevatorFault[4], elevatorFault[1]) + "\u001B[0m");
				System.out.println("\u001B[92m" + String.format("[TIME: 00:00:%d] [SCHEDULER] [FAULT RECOVERING] " +
								"Elevator %d Attempting To Resolve Sensor Inactivity",
						elevatorFault[4], elevatorFault[1]) + "\u001B[0m");

				System.out.println("\u001B[92m" + String.format("[TIME: 00:00:%d] [SCHEDULER] [SOFT-FAULT FIXED] " +
								"Elevator %d Elevator Arrival Sensor Fixed, Resuming Task",
						elevatorFault[4]+2, elevatorFault[1]) + "\u001B[0m" + "\n");

			}else if((int) elevatorFault[3] == Faults.DOOR.ordinal() && elevatorFault[2] == 1){

				System.out.println("\u001B[31m" + String.format("[TIME: 00:00:%d] [SCHEDULER] [ELEVATOR-FAULT] " +
								"Elevator %d Soft-Fault Detected: Elevator Doors Stuck",
						elevatorFault[4], elevatorFault[1]) + "\u001B[0m");
				System.out.println("\u001B[92m" + String.format("[TIME: 00:00:%d] [SCHEDULER] [FAULT RECOVERING] " +
								"Elevator %d Attempting To Re-Initiate Door Movement",
						elevatorFault[4], elevatorFault[1]) + "\u001B[0m");
				System.out.println("\u001B[92m" + String.format("[TIME: 00:00:%d] [SCHEDULER] [SOFT-FAULT FIXED] " +
								"Elevator %d Elevator Doors Functioning Properly, Resuming Task",
						elevatorFault[4]+2, elevatorFault[1]) + "\u001B[0m" +"\n");

			}else{

				System.out.println("\n\033[1;31m" + String.format("[TIME: 00:00:%d] [SCHEDULER] [CRITICAL-FAULT] " +
								"Elevator %d Hard-Fault Detected: Elevator Stuck Between Floors",
						elevatorFault[4], elevatorFault[1]) + "\u001B[0m");
				System.out.println("\033[1;31m" + String.format("[TIME: 00:00:%d] [SCHEDULER] [CRITICAL-FAULT] " +
								"Elevator %d Non-Recoverable Fault", elevatorFault[4], elevatorFault[1]) + "\u001B[0m");
				System.out.println("\033[1;31m" + String.format("[TIME: 00:00:%d] [SCHEDULER] [CRITICAL-FAULT] " +
								"Elevator %d Out Of Service", elevatorFault[4], elevatorFault[1]) + "\u001B[0m" +"\n");

			}

		}

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

    /**
     * State Change for the elevator
     */
	public synchronized void elevatorFinished() {
		ss = SchedulerState.IDLE;
	}

	
	public static void main(String[] args) throws UnknownHostException {

        Scheduler schedulerControl = new Scheduler();

        //Thread 1 - Communication Link B/w Scheduler & Floor
        Thread floor_To_Scheduler = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        schedulerControl.elevatorRequest();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Floor_Scheduler_Communication_Link");
        floor_To_Scheduler.start();

        //Thread 2 - Communication Link B/w Scheduler & Elevator
        Thread scheduler_To_Elevator = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    schedulerControl.elevatorStatus();
                }
            }
        }, "Scheduler_Elevator_Communication_Link");
        scheduler_To_Elevator.start();

		//Thread 3 - Communication Link B/w Scheduler & Elevator For Fault Handling
		Thread scheduler_Elevator_Fault_Handler = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					schedulerControl.handleElevatorFault();
				}
			}
		}, "scheduler_Elevator_Fault_Handler");
		scheduler_Elevator_Fault_Handler.start();

	}

}
