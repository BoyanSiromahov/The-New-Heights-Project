package ElevatorSubSystem;

/**
 * This class is used to keep track of the elevator doors.
 * ElevatorDoor objects may only be open or closed.
 * 
 * @author Muneeb Nasir
 */

enum DoorState {
	OPEN,
	CLOSE
}

public class ElevatorDoor {


	private DoorState elevatorDoor;


	/**
	 * The Initial State (Default State) Constructor for the MotorState State
	 */
	public ElevatorDoor(){
		elevatorDoor = DoorState.CLOSE;
	}

	/**
	 * The Getter Method For The Current State Of The Elevator
	 * Door
	 */
	public DoorState getElevatorDoor() {
		return elevatorDoor;
	}

	/**
	 *
	 *  Is used to track the elevator MotorState position and the elevator movement.
	 *  The elevator movement is constantly tracked and changed accordingly.
	 *
	 * @param doorClose, True - If the elevator doors are to be closed
	 *                 False - If the elevator door needs to be opened
	 * @return True, If the Door state has been changed accordingly
	 */
	public void setElevatorDoor(Boolean doorClose){

		if(doorClose){
			elevatorDoor = DoorState.CLOSE;
		}else{
			elevatorDoor = DoorState.OPEN;
		}
	}

	/**
	 * Is Used To Identify The Current State Of The Elevator Door
	 * @return True, if the door is closed, else False
	 */
	public boolean doorClosed(){

		if(elevatorDoor == DoorState.CLOSE){
			return true;
		}

		return false;

	}

}
