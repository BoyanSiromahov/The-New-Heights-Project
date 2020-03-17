package ElevatorSubSystem;

import Util.CallEvent;
import Util.Parser;
import Util.UDPHelper;

import java.net.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * The Main Elevator Class that represents the Elevator Subsystem. The Elevator
 * class controls the movement of the Elevator throughout the elevator shaft.
 * The Elevator class receives message from the scheduler and processes the data
 * to ensure the elevator request is executed.
 *
 * @author Muneeb Nasir
 */
public class Elevator implements Runnable {
    private static Logger LOGGER = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(Elevator.class.getName());
    }

    private static final int DOOR_OPENING_CLOSING_DELAY = 2;
    private static final int ELEVATOR_MOVING_TIME = 4;
    private static final int GROUND_FLOOR = 1;
    private static final int TOTAL_FLOORS = 5;

    private int elevatorPort;
    private int elevatorNumber;
    private int currentElevatorLevel;
    private int elevatorElapsedTime;

    // Elevator Associated Components States
    private ElevatorMotor motor;
    private ElevatorDoor door;
    private ArrivalSensor elevatorArrivalSensor;
    private ElevatorState subSystem; // Communication Control Class For Elevator

    // Elevator Communicating With The Scheduler (Receives The Call Events)
    private UDPHelper elevatorHelper;
    // Call Event Queue That Is Received From The Scheduler
    private List<CallEvent> commandReceived;

    // Elevator Button Associated With Each Elevator (List Of All Floor Buttons)
    private HashMap<Integer, ElevatorButton> elevatorFloorButtons;
    private Parser elevatorParser;

    /**
     * The Constructor for the Elevator Class. Each elevator is assigned a unique
     * elevator number; elevator control system.
     *
     * @param elevatorNumber,    The Unique Elevator Number
     * @param elevatorPortNum,   The Elevator Specified Port Number
     */
    public Elevator(int elevatorNumber, int elevatorPortNum) {

        this.elevatorPort = elevatorPortNum;
        currentElevatorLevel = GROUND_FLOOR;
        this.elevatorNumber = elevatorNumber;

        door = new ElevatorDoor(); // Initial State = Closed
        motor = new ElevatorMotor(); // Initial State = Stop
        elevatorArrivalSensor = new ArrivalSensor(); // Initial State = Not Reached Floor i.e. OFF
        elevatorParser = new Parser();
        elevatorHelper = new UDPHelper(elevatorPortNum);
        subSystem = new ElevatorState(elevatorPortNum + 1, elevatorNumber);
        initialiseDataSet();
    }

    /**
     * This can be used to initialize the Elevator Floor Buttons and the Arrival
     * Sensor for each Elevator
     */
    private void initialiseDataSet() {
        elevatorFloorButtons = new HashMap<Integer, ElevatorButton>() {
			private static final long serialVersionUID = 1L;
        };
        for (int i = GROUND_FLOOR; i < TOTAL_FLOORS; i++) {
            elevatorFloorButtons.put(i, ElevatorButton.OFF);
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
            LOGGER.warning(String.format("IOException: %s", e));
        }
    }

    /**
     *  The function is used to format the Log For The Elevator Movement
     * @param time, The Elapsed Time That Is To Be Added On The Logger
     * @return The Formatted Log String
     */
    private String formatLog(int time){
        DecimalFormat formatter = new DecimalFormat("00"); //This is used to keep track of the Time Format
        return String.format("[TIME: 00:00:%s] [ELEVATOR] [INFO] Elevator %d ",
                formatter.format(time), elevatorNumber); // Elevator Number Global Variable
    }

    /**
     * To signal the door opening/closing for the elevator
     */
    private void signalElevatorDoor(String command) {

        switch (command){
            case "OPEN":
                door.setElevatorDoor(true); // Close Door
                elevatorElapsedTime +=DOOR_OPENING_CLOSING_DELAY; // Door Movement Delay
                System.out.println(formatLog(elevatorElapsedTime) + "Doors Opening\n");
                elevatorDelay(DOOR_OPENING_CLOSING_DELAY);
                break;

            case "CLOSE":
                door.setElevatorDoor(true); // Close Door
                elevatorElapsedTime +=DOOR_OPENING_CLOSING_DELAY; // Door Movement Delay
                elevatorDelay(DOOR_OPENING_CLOSING_DELAY);
                System.out.println(formatLog(elevatorElapsedTime) + "Doors Closing\n");
                break;
        }

    }


    /**
     * The Main Function that is used to control the movement of the elevator according the Scheduler Request.
     * The elevator state is controlled and managed as per the scheduler request
     * @param destinationFloor,  The requested destination floor
     * @param elevatorDirection, The user-requested direction
     * @return True when the request has been executed correctly, False if the request is not executed and request
     * added in the waiting queue
     */
    public boolean moveElevator(int destinationFloor, Direction elevatorDirection) {
        switch (elevatorDirection) {
            case DOWN:
                if (door.doorClosed()) {
                    if (motor.movingDown() || motor.elevatorStopped()) {
                        subSystem.setSystemStateChange("EM"); // Elevator State = MOVING

                        while (currentElevatorLevel >= GROUND_FLOOR && currentElevatorLevel > destinationFloor) {
                            elevatorElapsedTime +=ELEVATOR_MOVING_TIME;
                            elevatorDelay(ELEVATOR_MOVING_TIME);
                            System.out.println(formatLog(elevatorElapsedTime) +
                                    String.format("Moving Down to Floor Number: %d From: %d", destinationFloor,
                                            currentElevatorLevel));
                            currentElevatorLevel--;
                            subSystem.setCurrentFloor(currentElevatorLevel);
                        }

                        motor.setElevatorMovement("DOWN");
                        System.out.println(formatLog(elevatorElapsedTime) +
                                String.format("Has Reached Floor Number: %d", currentElevatorLevel));

                        elevatorArrivalSensor.set_arrivalSensor(true);// Floor Reached

                        motor.setElevatorMovement("STOP"); // Motor Stopped
                        subSystem.setSystemStateChange("WI"); // Elevator Stopped-IDLE
                        signalElevatorDoor("OPEN");

                        elevatorElapsedTime +=DOOR_OPENING_CLOSING_DELAY; //Off-Boarding Delay

                        // E Stopped Reached Floor
                        System.out.println(formatLog(elevatorElapsedTime) +
                                String.format("arrived at floor: %d\n",currentElevatorLevel));

                        System.out.println(formatLog(elevatorElapsedTime) + "Passengers Exiting");

                        return true;

                        // TO DO Informing the Scheduler of the Elevator Current State
                    } else {
                        //Elevator Movement in a different direction
                        System.err.println(formatLog(elevatorElapsedTime) +"Not Moving In The Requested Direction");
                        System.err.println(formatLog(elevatorElapsedTime) + "Current Status: "+
                                motor.getElevatorMovement().toString());
                        //addFloorToQueue(destinationFloor, elevatorDirection);

                        // TO DO Informing the Scheduler of the Elevator Current State

                        return false;
                    }
                } else {
                    //Elevator Currently Stopped at a Floor.
                    System.err.println(formatLog(elevatorElapsedTime) + "Currently At Floor " + currentElevatorLevel);
                    return false;
                    //closeElevatorDoor();
                    //moveElevator(destinationFloor,elevatorDirection);
                }

            case UP:
                if (door.doorClosed()) {
                    if (motor.movingUp() || motor.elevatorStopped()) {
                        subSystem.setSystemStateChange("EM"); // Elevator State = MOVING

                        while (currentElevatorLevel < TOTAL_FLOORS && currentElevatorLevel < destinationFloor) {
                            elevatorDelay(ELEVATOR_MOVING_TIME);
                            elevatorElapsedTime +=ELEVATOR_MOVING_TIME;
                            System.out.println(formatLog(elevatorElapsedTime) +
                                    String.format("Moving UP To Floor Number: %d From: %d", destinationFloor,
                                            currentElevatorLevel));
                            currentElevatorLevel++;
                            subSystem.setCurrentFloor(currentElevatorLevel);
                        }

                        motor.setElevatorMovement("UP"); // Motor Movement
                        System.out.println(formatLog(elevatorElapsedTime) +
                                String.format("Has Reached Floor Number: %d", currentElevatorLevel));

                        elevatorArrivalSensor.set_arrivalSensor(true);// Floor Reached
                        motor.setElevatorMovement("STOP"); // Motor Stopped
                        subSystem.setSystemStateChange("WI"); // Elevator Stopped-IDLE
                        signalElevatorDoor("OPEN"); // Open Door
                        elevatorElapsedTime +=DOOR_OPENING_CLOSING_DELAY; //Off-Boarding Delay

                        // Elevator Reached Floor
                        System.out.println(formatLog(elevatorElapsedTime) +
                                String.format("arrived at floor: %d\n",currentElevatorLevel));

                        System.out.println(formatLog(elevatorElapsedTime) + "Passengers Exiting");

                        return true;
                    } else {
                        //Elevator Movement in a different direction
                        System.err.println(formatLog(elevatorElapsedTime) +"Not Moving In The Requested Direction");
                        System.err.println(formatLog(elevatorElapsedTime) + "Current Status: "+
                                motor.getElevatorMovement().toString());

                        return false;
                    }

                } else {
                    //Elevator Currently Stopped at a Floor.
                    System.err.println(formatLog(elevatorElapsedTime) + "Currently At Floor " + currentElevatorLevel);
                    return false;
                }

            default:
                LOGGER.warning(String.format("[ELEVATOR] [ERROR] Invalid Movement Direction %s Specified",
                        elevatorDirection.toString()));
                return false;
        }
    }

    /**
     * Adds the Floor to the Waiting Queue If a new request was received during the movement of the elevator in the
     * requested direction. This Waiting Queue is is processed after the current request if completed.
     *
     * @param requestedFloor,     The Destination Floor Number
     * @param requestedDirection, The Requested Direction
     *
    private void addFloorToQueue(int requestedFloor, Direction requestedDirection) {
        floorsProcessingDelayed.put(requestedFloor, requestedDirection);
        elevatorFloorButtons.replace(requestedFloor, ElevatorButton.ON);
        elevatorArrivalSensor.replace(requestedFloor, ArrivalSensor.NOT_REACHED_FLOOR);
    }*/

    /***
     * The Scheduler's received request is analyzed and processed and Movement logic is controlled accordingly
     * @return True, when the request has been executed completely. False, when the request execution was not
     * completed or failure happened during the processing.
     */
    public boolean receiveAndCheckSchedulerRequest() {
        boolean requestSuccessful = false;
        DecimalFormat formatter = new DecimalFormat("00"); //This is used to keep track of the Time Format
        SimpleDateFormat timeFormatter = new SimpleDateFormat("ss");
        //Retrieve All Commands Sent From the Scheduler
        while (!commandReceived.isEmpty()) {
            // Gets the sent Floor Request associated with the Selected Elevator from the Scheduler
            CallEvent systemSchedulerCommand = commandReceived.remove(0);

            if (systemSchedulerCommand != null) {
                // Extracting The Call Event Time From The Command
                elevatorElapsedTime = Integer.parseInt(timeFormatter.format(systemSchedulerCommand.getStartTime()));
                System.out.println(formatLog(elevatorElapsedTime) + "Currently In Service ");
                if (door.doorClosed() && subSystem.elevatorIdle()) {

                    // UPWARDS
                    if (systemSchedulerCommand.getEndFloor() > currentElevatorLevel &&
                            systemSchedulerCommand.getEndFloor() <= TOTAL_FLOORS) {

                        //OPEN Doors
                        signalElevatorDoor("OPEN");

                        //MOVE UP
                        elevatorElapsedTime+=DOOR_OPENING_CLOSING_DELAY; //Adding Delay for Boarding
                        System.out.println(formatLog(elevatorElapsedTime) + "Boarding");

                        signalElevatorDoor("CLOSE");
                        // Changing The Elevator Button Flag
                        elevatorFloorButtons.replace(systemSchedulerCommand.getEndFloor(), ElevatorButton.ON);
                        // sendElevatorStatus(); // Informing the Scheduler of the Elevator Current State TO DO

                        // sendElevatorStatus();  // Informing the Scheduler of the Elevator Current State TO DO

                        requestSuccessful = moveElevator(systemSchedulerCommand.getEndFloor(),
                                systemSchedulerCommand.getDirection());


                    } else if (systemSchedulerCommand.getEndFloor() < currentElevatorLevel &&
                            systemSchedulerCommand.getEndFloor() >= GROUND_FLOOR) {

                        // Case when the request start floor is below the current elevator level floor
                        while (currentElevatorLevel > systemSchedulerCommand.getStartFloor()){
                            elevatorDelay(ELEVATOR_MOVING_TIME);
                            elevatorElapsedTime +=ELEVATOR_MOVING_TIME;
                            System.out.println(formatLog(elevatorElapsedTime) +
                                    String.format("Moving Down Towards The Request Originated Floor Level %d From " +
                                                    "Current Floor Level %d\n", systemSchedulerCommand.getStartFloor(),
                                    currentElevatorLevel));
                            currentElevatorLevel--;
                            subSystem.setCurrentFloor(currentElevatorLevel);
                        }

                        //OPEN Doors
                        signalElevatorDoor("OPEN");

                        elevatorElapsedTime+=DOOR_OPENING_CLOSING_DELAY; //Adding Delay for Boarding
                        System.out.println(formatLog(elevatorElapsedTime) + "Boarding");

                        // sendElevatorStatus();  // Informing the Scheduler of the Elevator Current State TO DO

                        signalElevatorDoor("CLOSE");
                        // Changing The Elevator Button Flag
                        elevatorFloorButtons.replace(systemSchedulerCommand.getEndFloor(), ElevatorButton.ON);

                        // sendElevatorStatus();  // Informing the Scheduler of the Elevator Current State TO DO

                        requestSuccessful = moveElevator(systemSchedulerCommand.getEndFloor(),
                                systemSchedulerCommand.getDirection());

                    } else {
                        LOGGER.warning(String.format("[ELEVATOR] [ERROR] Elevator %d Request for Floor %d Invalid",
                                elevatorNumber, systemSchedulerCommand.getEndFloor()));
                    }
                } else {
                    LOGGER.warning(String.format("[ELEVATOR] [ERROR] Elevator %d Currently Moving", elevatorNumber));
                }
            }
        }
        return requestSuccessful;
    }


    
    /***
     * This is the main method that is implemented from the Runnable interface. This
     * method ensure that only one elevator thread can process the request and
     * respond accordingly. (Ensure The Operation is Atomic)
     */
    @Override
    public void run() {
        while (true) {
            commandReceived.add(elevatorParser.parseByteEvent(elevatorHelper.receive(false)));
            receiveAndCheckSchedulerRequest();
        }
    }

    public static void main(String[] args)
	{
        Thread elevatorThread_1, elevatorThread_2;
        elevatorThread_1 = new Thread(new Elevator(1,22),"Elevator NO.1");
        elevatorThread_2 = new Thread(new Elevator(2,24),"Elevator NO.2");
        elevatorThread_1.start();
        elevatorThread_2.start();

    }
}
