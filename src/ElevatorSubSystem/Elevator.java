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
    private static Logger LOGGER = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(Elevator.class.getName());
    }
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
     * 
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
        elevatorFloorButtons = new HashMap<Integer, ElevatorButton>(){};
        elevatorArrivalSensor = new HashMap<Integer, ArrivalSensor>(){};
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
            LOGGER.warning(String.format("IOException: %s%n", e));
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

    /**
     * The Main Function that is used to control the movement of the elevator according the Scheduler Request
     * @param destinationFloor, The requested destination floor
     * @param elevatorDirection, The user-requested direction
     * @return True when the request has been executed correctly, False if the request is not executed and request
     * added in the waiting queue
     */
    public boolean moveElevator(int destinationFloor, Direction elevatorDirection){
        switch (elevatorDirection) {
            case DOWN:
                if(door == ElevatorDoor.CLOSE){
                    if(motor == ElevatorMotor.STOP || motor == ElevatorMotor.DOWNWARD){
                        LOGGER.info(String.format("Elevator Moving %d Down to Floor Number: %d From: %d",
                                elevatorNumber, destinationFloor, currentElevatorLevel));
                        while(currentElevatorLevel >= GROUND_FLOOR && currentElevatorLevel > destinationFloor){
                            currentElevatorLevel -= 1;
                            elevatorDelay(ELEVATOR_MOVING_TIME);
                            LOGGER.info(String.format("Elevator %d Moving Down to Floor Number: %d From: %d",
                                    elevatorNumber, destinationFloor, currentElevatorLevel));
                        }
                        motor = ElevatorMotor.DOWNWARD;
                        LOGGER.info(String.format("Elevator %d Has Reached Floor Number: %d \n", elevatorNumber,
                                destinationFloor));
                        elevatorArrivalSensor.replace(destinationFloor, ArrivalSensor.REACHED_FLOOR);
                        motor = ElevatorMotor.STOP;
                        openElevatorDoor();
                        return true;
                    }else{
                        //Elevator Movement in a different direction
                        LOGGER.info(String.format("Elevator %d Not Moving In The Requested Direction", elevatorNumber));
                        LOGGER.info(String.format("Elevator %d Current Status: %s", elevatorNumber, motor.toString()));
                        addFloorToQueue(destinationFloor, elevatorDirection);
                        return false;
                    }
                }else{
                    //Elevator Currently Stopped at a Floor
                    LOGGER.info(String.format("Elevator %d Currently Stopped at floor %d", elevatorNumber,
                            currentElevatorLevel));
                    addFloorToQueue(destinationFloor, elevatorDirection);
                    closeElevatorDoor();
                    return false;
                }

            case UP:
                if(door == ElevatorDoor.CLOSE){
                    if(motor == ElevatorMotor.STOP || motor == ElevatorMotor.UPWARD){
                        LOGGER.info(String.format("Elevator %d Moving UP To Floor Number: %d From: %d",
                                elevatorNumber, destinationFloor, currentElevatorLevel));
                        while (currentElevatorLevel < TOTAL_FLOORS && currentElevatorLevel < destinationFloor){
                            currentElevatorLevel += 1;
                            elevatorDelay(ELEVATOR_MOVING_TIME);
                            LOGGER.info(String.format("Elevator %d Moving UP To Floor Number: %d From: %d",
                                    elevatorNumber, destinationFloor, currentElevatorLevel));
                        }
                        motor = ElevatorMotor.UPWARD;
                        LOGGER.info(String.format("Elevator %d Has Reached Floor Number: %d \n", elevatorNumber,
                                destinationFloor));
                        elevatorArrivalSensor.replace(destinationFloor, ArrivalSensor.REACHED_FLOOR);
                        motor = ElevatorMotor.STOP;
                        openElevatorDoor();
                        return true;
                    }else{
                        //Elevator Movement in a different direction
                        LOGGER.info(String.format("Elevator %d Not Moving In The Requested Direction", elevatorNumber));
                        LOGGER.info(String.format("Elevator %d Current Status: %s", elevatorNumber, motor.toString()));
                        addFloorToQueue(destinationFloor, elevatorDirection);
                        return false;
                    }

                }else{
                    //Elevator Currently Stopped at a Floor
                    LOGGER.info(String.format("Elevator %d Currently Stopped at floor %d", elevatorNumber,
                            currentElevatorLevel));
                    addFloorToQueue(destinationFloor, elevatorDirection);
                    closeElevatorDoor();
                    return false;
                }

            default:
                LOGGER.warning(String.format("Invalid Movement Direction %s Specified", elevatorDirection.toString()));
                return false;
        }
    }

    /**
     * Adds the Floor to the Waiting Queue If a new request was received during the movement of the elevator in the
     * requested direction. This Waiting Queue is is processed after the current request if completed.
     * @param requestedFloor, The Destination Floor Number
     * @param requestedDirection, The Requested Direction
     */
    private void addFloorToQueue(int requestedFloor, Direction requestedDirection){
        //floors.add(new Floor(requestedFloor));
        elevatorFloorButtons.replace(requestedFloor, ElevatorButton.ON);
        elevatorArrivalSensor.replace(requestedFloor, ArrivalSensor.NOT_REACHED_FLOOR);
    }

    /***
     * The Scheduler's received request is analyzed and processed
     * @return True, when the request has been executed completely. False, when the request execution was not
     * completed or failure happened during the processing.
     */
    public boolean checkSchedulerRequest(){
        boolean requestSuccessful = false;
        //Retrieve All Commands Sent From the Scheduler
        while (!commandRecieved.isEmpty()) {
            // Gets the sent Floor Request associated with the Selected Elevator from the Scheduler
            Floor systemSchedulerCommand = floors.poll();

            if(systemSchedulerCommand != null){
                if (elevatorNumber == systemSchedulerCommand.getElevatorNumber()) {
                    LOGGER.info(String.format("Elevator %d Currently In Service", elevatorNumber));
                }
                if(door == ElevatorDoor.OPEN){
                    if(systemSchedulerCommand.getDestinationFloor() > currentElevatorLevel &&
                            systemSchedulerCommand.getDestinationFloor() <= TOTAL_FLOORS){
                        //MOVE UP
                        elevatorFloorButtons.replace(systemSchedulerCommand.getDestinationFloor(), ElevatorButton.ON);
                        closeElevatorDoor();
                        requestSuccessful = moveElevator(systemSchedulerCommand.getDestinationFloor(),
                                systemSchedulerCommand.getRequestedDirection());
                    }else if(systemSchedulerCommand.getDestinationFloor() < currentElevatorLevel &&
                            systemSchedulerCommand.getDestinationFloor() >= GROUND_FLOOR){
                        // MOVE DOWNWARD
                        elevatorFloorButtons.replace(systemSchedulerCommand.getDestinationFloor(), ElevatorButton.ON);
                        closeElevatorDoor();
                        requestSuccessful = moveElevator(systemSchedulerCommand.getDestinationFloor(),
                                systemSchedulerCommand.getRequestedDirection());
                    }else{
                        LOGGER.warning(String.format("Elevator %d Request for Floor %d Invalid", elevatorNumber,
                                systemSchedulerCommand.getDestinationFloor()));
                    }
                }else{
                    //Add Logic for handling the elevator movement
                }
            }else {
                LOGGER.warning("Elevator %d Received Invalid Request From Scheduler");
            }
        }
        return requestSuccessful;
    }

    /***
     *  This is the main method that is implemented from the Runnable interface. This method ensure that only one
     *  elevator thread can process the request and respond accordingly. (Ensure The Operation is Atomic)
     */
    @Override
    public void run() {
        while (true) {
            synchronized (systemScheduler){

            }
        }
    }
}



