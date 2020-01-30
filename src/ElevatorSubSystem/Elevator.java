package ElevatorSubSystem;

import java.util.logging.Logger;
import FloorSubSystem.Floor;
import SchedulerSubSystem.Scheduler;
import java.util.*;

/**
 * The Main Elevator Class that represents the Elevator Subsystem. The Elevator class controls the movement of
 * the Elevator throughout the elevator shaft. The Elevator class receives message from the scheduler and processes
 * the data to ensure the elevator request is executed.
 * @author Muneeb Nasir
 */
public class Elevator implements Runnable{

    private static final int DOOR_OPENING_CLOSING_DELAY  = 2;
    private static final int ELEVATOR_MOVING_TIME  = 3;
    private static final int GROUND_FLOOR  = 1;
    private static final int TOTAL_FLOORS  = 5;
    private ElevatorMotor motor;
    private ElevatorDoor door;
    private int elevatorNumber;
    private int currentElevatorLevel;

    private Scheduler systemScheduler;
    private Queue<Floor> commandRecieved;
    private HashMap<Integer, ArrivalSensor> elevatorArrivalSensor;
    private HashMap<Integer, ElevatorButton> elevatorFloorButtons;
    private Queue<Floor> floors;


    /**
     * The Constructor for the Elevator Class. Each elevator is assigned a unique elevator number and is
     * connected to the main Scheduler; elevator control system.
     * @param elevatorNumber, The Unique Elevator Number
     * @param elevatorScheduler, The Main Scheduler
     */
    public Elevator(int elevatorNumber, Scheduler elevatorScheduler){
        currentElevatorLevel = GROUND_FLOOR;
        door = ElevatorDoor.OPEN;
        motor = ElevatorMotor.STOP;
        this.elevatorNumber = elevatorNumber;
        commandRecieved =  new LinkedList<>();
        systemScheduler = elevatorScheduler;
        floors = new LinkedList<>();
        initialiseDataSet();
    }

    /**
     * This can be used to initialize the Elevator Floor Buttons and the Arrival Sensor for each Elevator
     */
    private void initialiseDataSet(){
        elevatorFloorButtons = new HashMap(){};
        elevatorArrivalSensor = new HashMap(){};
        for(int i = GROUND_FLOOR; i < TOTAL_FLOORS; i++){
            elevatorFloorButtons.put(i, ElevatorButton.OFF);
            elevatorArrivalSensor.put(i, ArrivalSensor.NOT_REACHED_FLOOR);
        }
    }

    /**
     * To create the delay in the execution to ensure the system as "Real-Time"
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
    private void openElevatorDoor(){
        door = ElevatorDoor.OPEN;
        elevatorDelay(DOOR_OPENING_CLOSING_DELAY);
    }

    /**
     * To signal the door closure for the elevator using the Enum
     */
    private void closeElevatorDoor(){
        door = ElevatorDoor.CLOSE;
        elevatorDelay(DOOR_OPENING_CLOSING_DELAY);
    }

    /***
     * The Scheduler's received request is analyzed and processed
     * @return True, when the request has been executed completely. False, when the request execution was not
     * completed or failure happened during the processing.
     */
    public boolean receiveAndCheckSchedulerRequest() {

        return false;
    }

    /***
     *  This is the main method that is implemented from the Runnable interface. This method ensure that only one
     *  elevator thread can process the request and respond accordingly. (Ensure The Operation is Atomic)
     */
    @Override
    public void run() {
        while (true) {
            synchronized (systemScheduler){
                // TO DO RECEIVE THE REQUEST FROM THE Scheduler
                // commandRecieved = systemScheduler.requestFromScheduler();
                if(receiveAndCheckSchedulerRequest()){
                    // TO BE DONE ONCE THE COMMAND EXECUTION HAS BEEN SUCCESSFUL
                    // notifyAll();
                }else{

                }

            }
        }
    }
}



