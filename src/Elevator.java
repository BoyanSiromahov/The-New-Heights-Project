import java.util.*;

public class Elevator implements Runnable{
    private static int DOOR_OPENING_CLOSING_DELAY  = 2;
    private static int ELEVATOR_MOVING_TIME  = 8;
    private ElevatorMotor motor;
    private ElevatorDoor door;
    private int elevatorNumber;
    private int elevatorLevel;
    private int currentFloor;

    private Schduler systemSchduler;
    private HashMap<Integer, floorSubSystem> commandRecieved;
    private HashMap<Integer, ArrivalIndicator> elevatorArrivalIndicator;
    private HashMap<Integer, ElevatorButton> elevatorFloorButtons;
    private Queue<floorSubSystem> floors;


    public Elevator(int elevatorNumber, Schduler elevatorSchduler, Integer totalFloors){
        currentFloor = 0;
        motor = ElevatorMotor.STOP;
        this.elevatorNumber = elevatorNumber;
        commandRecieved = new HashMap<Integer, floorSubSystem>(){};
        systemSchduler = elevatorSchduler;
        floors = new LinkedList<floorSubSystem>();
        elevatorFloorButtons = new HashMap<Integer, ElevatorButton>(){};
        elevatorArrivalIndicator = new HashMap<Integer, ArrivalIndicator>(){};
    }

    private static void elevatorDelay(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    private void openElevatorDoor(){
        door = ElevatorDoor.OPEN;
        elevatorDelay(DOOR_OPENING_CLOSING_DELAY);
    }

    private void closeElevatorDoor(){
        door = ElevatorDoor.CLOSE;
        elevatorDelay(DOOR_OPENING_CLOSING_DELAY);
    }


    public boolean moveDown(int destinationFloor){
        if(door == ElevatorDoor.CLOSE){
            if(motor == ElevatorMotor.STOP || motor == ElevatorMotor.DOWNWARD)
            {
                System.out.println("Elevator Going DOWN");
                elevatorLevel -= 1;
                elevatorFloorButtons.put(destinationFloor, ElevatorButton.ON);
                elevatorArrivalIndicator.put(destinationFloor, ArrivalIndicator.REACHED_FLOOR);
                elevatorDelay(ELEVATOR_MOVING_TIME);
                motor = ElevatorMotor.DOWNWARD;
                System.out.println("Elevator " + this.elevatorNumber + ": Is Currently At " + elevatorLevel +"Floor");
                return true;
            }else{
                System.out.println("Elevator Not Moving In The Requested Direction");
                System.out.println("Current Elevator Direction: "+ motor.toString());
                floors.add(new floorSubSystem(destinationFloor));
            }
        }
        motor = ElevatorMotor.STOP;
        elevatorFloorButtons.put(destinationFloor, ElevatorButton.ON);
        elevatorArrivalIndicator.put(destinationFloor, ArrivalIndicator.NOT_REACHED_FLOOR);
        closeElevatorDoor();
        return false;
    }


    public boolean moveUp(int destinationFloor){
        if(door == ElevatorDoor.CLOSE) {
            if(motor == ElevatorMotor.STOP || motor == ElevatorMotor.UPWARD){
                System.out.println("Elevator Going UP");
                elevatorLevel += 1;
                elevatorFloorButtons.put(destinationFloor, ElevatorButton.ON);
                elevatorArrivalIndicator.put(destinationFloor, ArrivalIndicator.REACHED_FLOOR);
                elevatorDelay(ELEVATOR_MOVING_TIME);
                motor = ElevatorMotor.UPWARD;
                System.out.println("Elevator " + this.elevatorNumber + ": Is Currently At " + elevatorLevel +"Floor");
                return true;
            }else{
                System.out.println("Elevator Not Moving In The Requested Direction");
                System.out.println("Current Elevator Direction: "+ motor.toString());
                floors.add(new floorSubSystem(destinationFloor));
            }
        }
        motor = ElevatorMotor.STOP;
        elevatorFloorButtons.put(destinationFloor, ElevatorButton.ON);
        elevatorArrivalIndicator.put(destinationFloor, ArrivalIndicator.NOT_REACHED_FLOOR);
        closeElevatorDoor();
        return false;

    }

    /***
     * LOGIC NEEDS TO BE ADDED TO HANDLE EDGE CASES AND FLOORING
     * @return
     */
    public boolean checkSchdulerRequest(){
        for (HashMap.Entry<Integer, floorSubSystem> entry : commandRecieved.entrySet()) {
            int elevatorKey = entry.getKey();
            if(elevatorNumber == elevatorKey){
                floors.add(entry.getValue()); // Gets the set value associated with the Key Object
            }
            floorSubSystem destfloor = floors.poll();
            if(destfloor != null){
                if(destfloor.getFloorNum() > currentFloor){
                    //
                }else if(destfloor.getFloorNum() < currentFloor){
                    //
                }
            }
        }

        return true;
    }

    /***
     * LOGIC NEEDS TO BE ADDED TO HANDLE MOVEMENT, COMMUNICATION WITH THE ELEVATOR AND THE
     * SCHDULER
     * @return
     */
    @Override
    public void run() {
        while (true) {
            synchronized (systemSchduler){
                commandRecieved = systemSchduler.move();
                //Add logic to check the System and the elevator
                if(checkSchdulerRequest()){
                    systemSchduler.move();
                }else {
                    systemSchduler.notifyAll();
                }
            }
        }
    }
}



