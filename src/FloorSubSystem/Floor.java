package FloorSubSystem;
import ElevatorSubSystem.Direction;

public class Floor {

    private Direction requestedDirection;
    private int elevatorNumber;
    private int destinationFloor;
    private int currentFloor;

    public Floor(int num, Direction direction){
        elevatorNumber = 1;
        currentFloor = 1;
        requestedDirection = direction;
        destinationFloor = num;
    }
    public int getDestinationFloorNum(){
        return destinationFloor;
    }
    public int getElevatorNumber(){
        return elevatorNumber;
    }
    public int getCurrentFloor(){ return currentFloor; }
    public Direction getRequestedDirection(){
        return requestedDirection;
    }

}
