package ElevatorSubSystem;

import SchedulerSubSystem.Scheduler;
import Util.CallEvent;

import java.util.*;

/**
 * The Main Elevator Class that represents the Elevator Subsystem. The Elevator
 * class controls the movement of the Elevator throughout the elevator shaft.
 * The Elevator class receives message from the scheduler and processes the data
 * to ensure the elevator request is executed.
 * 
 * @author Muneeb Nasir
 */
public class Elevator implements Runnable {

	private static final int DOOR_OPENING_CLOSING_DELAY = 2;
	private static final int ELEVATOR_MOVING_TIME = 4;
	private static final int GROUND_FLOOR = 1;
	private static final int TOTAL_FLOORS = 5;
	private ElevatorMotor motor;
	private ElevatorDoor door;
	private int elevatorNumber;
	private int currentElevatorLevel;

	private Scheduler systemScheduler;
	private Queue<CallEvent> commandRecieved;
	private HashMap<Integer, ArrivalSensor> elevatorArrivalSensor;
	private HashMap<Integer, ElevatorButton> elevatorFloorButtons;

	/**
	 * The Constructor for the Elevator Class. Each elevator is assigned a unique
	 * elevator number and is connected to the main Scheduler; elevator control
	 * system.
	 * 
	 * @param elevatorNumber, The Unique Elevator Number
	 * @param elevatorScheduler, The Main Scheduler
	 */
	public Elevator(int elevatorNumber, Scheduler elevatorScheduler) {
		currentElevatorLevel = GROUND_FLOOR;
		door = ElevatorDoor.OPEN;
		motor = ElevatorMotor.STOP;
		this.elevatorNumber = elevatorNumber;
		commandRecieved = new LinkedList<>();
		systemScheduler = elevatorScheduler;
		initialiseDataSet();
	}

	/**
	 * This can be used to initialize the Elevator Floor Buttons and the Arrival
	 * Sensor for each Elevator
	 */
	private void initialiseDataSet() {
		elevatorFloorButtons = new HashMap<Integer, ElevatorButton>() {	
		};
		elevatorArrivalSensor = new HashMap<Integer, ArrivalSensor>() {
		};
		for (int i = GROUND_FLOOR; i < TOTAL_FLOORS; i++) {
			elevatorFloorButtons.put(i, ElevatorButton.OFF);
			elevatorArrivalSensor.put(i, ArrivalSensor.NOT_REACHED_FLOOR);
		}
	}

	/**
	 * To create the delay in the execution to ensure the system as "Real-Time"
	 * 
	 * @param seconds, The amount of delay that is required
	 */
	private static void elevatorDelay(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			System.err.print(e);
		}
	}

	/**
	 * To signal the door opening for the elevator using the Enum
	 */
	private void openElevatorDoor() {
		door = ElevatorDoor.OPEN;
		elevatorDelay(DOOR_OPENING_CLOSING_DELAY);
	}

	/**
	 * To signal the door closure for the elevator using the Enum
	 */
	private void closeElevatorDoor() {
		door = ElevatorDoor.CLOSE;
		elevatorDelay(DOOR_OPENING_CLOSING_DELAY);
	}

	/***
	 * The Scheduler's received request is analyzed and processed
	 * 
	 * @return True, when the request has been executed completely. False, when the
	 *         request execution was not completed or failure happened during the
	 *         processing.
	 */
	public boolean receiveAndCheckSchedulerRequest() {
		CallEvent temp = commandRecieved.poll();
		if (temp != null) {
			System.out.println(String.format("\nElevator %d: received request from scheduler", elevatorNumber));
			elevatorFloorButtons.replace(temp.getEndFloor(), ElevatorButton.ON);
			systemScheduler.elevatorBoarded();
			System.out.println("Elevator boarding underway");
			closeElevatorDoor();
			systemScheduler.elevatorReady();
			System.out.println("Elevator moving towards Destination Floor: " + temp.getEndFloor());
			elevatorDelay(ELEVATOR_MOVING_TIME);
			currentElevatorLevel = temp.getEndFloor();
			return true;
		}
		System.out.println("Invalid Request Sent By Scheduler");
		return false;
	}

	/***
	 * This is the main method that is implemented from the Runnable interface. This
	 * method ensure that only one elevator thread can process the request and
	 * respond accordingly. (Ensure The Operation is Atomic)
	 */
	@Override
	public void run() {
		while (true) {
			synchronized (systemScheduler) {
				commandRecieved.add(systemScheduler.getEvent());
				if (receiveAndCheckSchedulerRequest()) {
					elevatorArrivalSensor.replace(currentElevatorLevel, ArrivalSensor.REACHED_FLOOR);
					systemScheduler.elevatorArrived(currentElevatorLevel);
					System.out.println("Elevator arrived at floor: " + currentElevatorLevel);
				} else {
					// Case if the request is invalid
					systemScheduler.notifyAll();
				}

			}
		}
	}
}
