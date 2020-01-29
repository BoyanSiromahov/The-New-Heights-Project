package FloorSubSystem;

import ElevatorSubSystem.Direction;
import SchedulerSubSystem.Scheduler;
import Util.Parser;

/**
 * 
 * 
 * @author Samantha Tripp
 *
 */
public class Floor {

	private Scheduler scheduler;
    private Direction requestedDirection;
    private int elevatorNumber;
    private int destinationFloor;
    private int currentFloor;
    

    /**
     * The Floor object constructor. A Parser object is created that processes a CSV file, and this
     * data is transferred to the scheduler.
     * 
     * @param scheduler
     * @param currentFloor
     * @param destinationFloor
     * @param direction
     */
    public Floor(Scheduler scheduler, int currentFloor, int destinationFloor, Direction direction){
        this.scheduler = scheduler;
    	elevatorNumber = 1;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        requestedDirection = direction;
       
        Parser p = new Parser();
        //scheduler.sendData(p.csvReader());
    }
    
    /**
     * Returns the current floor number.
     * 
     * @return currentFloor
     */
    public int getCurrentFloor(){
        return currentFloor;
    }
    
    /**
     * Returns the destination floor number.
     * 
     * @return destinationFloor
     */
    public int getDestinationFloor() {
    	return destinationFloor;
    }
    
    /**
     * Returns the number of the elevator car that is currently servicing this floor.
     * 
     * @return elevatorNumber
     */
    public int getElevatorNumber(){
        return elevatorNumber;
    }
    
    /**
     * Returns the desired direction of travel.
     * 
     * @return requestedDirection UP, DOWN
     */
    public Direction getRequestedDirection(){
        return requestedDirection;
    }

}
