package ElevatorSubSystem;

/**
 * This class is used to keep track of the Elevator movement between
 * floors. There are only three states for the buttons: GO_UP indicates the
 * elevator is moving to a upper floor GO_DOWN indicates the elevator is moving
 * to a lower floor STOP indicates that the elevator is currently located/halted
 * on a floor.
 * 
 * @author Muneeb Nasir
 */

enum MotorState{
	UPWARD,
	DOWNWARD,
	STOP
}

public class ElevatorMotor {

	private MotorState elevatorMovement;


	/**
	 * The Initial State (Default State) Constructor for the MotorState State
	 */
	public ElevatorMotor(){
		elevatorMovement = MotorState.STOP;
	}

	/**
	 * The Getter Method For The Current State Of The Elevator
	 * Motor
	 */
	public MotorState getElevatorMovement() {
		return elevatorMovement;
	}

	/**
	 *  Is used to track the elevator MotorState position and the elevator movement.
	 *  The elevator movement is constantly tracked and changed accordingly.
	 *
	 * @param motorStateMovement, True - If the elevator has reached the floor
	 *                 False - If the elevator has not reached the floor
	 * @return True, If the MotorState state has been changed accordingly
	 */
	public boolean setElevatorMovement(String motorStateMovement){

		boolean success = false;
		switch (motorStateMovement){
			case "UP":
				elevatorMovement = MotorState.UPWARD;
				success = true;
				break;

			case "DOWN":
				elevatorMovement = MotorState.DOWNWARD;
				success = true;
				break;

			case "STOP":
				elevatorMovement = MotorState.STOP;
				success = true;
				break;

			default:
				System.err.println("[ERROR] Invalid MotorState Movement Specified");
		}

		return success;
	}

	/**
	 * Is Used To Check Whether The Elevator Is Moving Down or Not
	 * @return False, if motor not moving Up
	 */
	public boolean movingDown(){
		return (elevatorMovement == MotorState.DOWNWARD);
	}

	/**
	 * Returns the current state ordinal for the elevator motor
	 * @return int, Elevator Ordinal
	 */
	public int state(){
		return (elevatorMovement.ordinal());
	}

	/**
	 * Is Used To Check Whether The Elevator Is Moving Up or Not
	 * @return False, if motor not moving Up
	 */
	public boolean movingUp(){
		return (elevatorMovement == MotorState.UPWARD);
	}

	public boolean elevatorStopped(){
		return (elevatorMovement == MotorState.STOP);
	}

}
