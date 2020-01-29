
public class Floor {

    private Direction requestedDirection;
    private int elevatorNumber;
    private int destinationFloor;
    private int currentFloor;

    Floor(int num, Direction direction){
        elevatorNumber = 1;
        currentFloor = 1;
        requestedDirection = direction;
        destinationFloor = num;
    }
    public int getFloorNum(){
        return destinationFloor;
    }
    public int getElevatorNumber(){
        return elevatorNumber;
    }
    public Direction getRequestedDirection(){
        return requestedDirection;
    }

}
