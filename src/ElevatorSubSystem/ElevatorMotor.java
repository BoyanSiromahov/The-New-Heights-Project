package ElevatorSubSystem;

/**
 * This enumeration class is used to keep track of the Elevator movement between
 * floors. There are only three states for the buttons: GO_UP indicates the
 * elevator is moving to a upper floor GO_DOWN indicates the elevator is moving
 * to a lower floor STOP indicates that the elevator is currently located/halted
 * on a floor.
 * 
 * @author Muneeb Nasir
 */
public enum ElevatorMotor {
	UPWARD, DOWNWARD, STOP
}
