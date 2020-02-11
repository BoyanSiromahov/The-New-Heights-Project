package ElevatorSubSystem;

/**
 * The STATE MACHINE Implementation done using the State Enum Class
 *
 * The ENUM Class that is responsible for state execution and the control of the Elevator System.
 * This is used to manage the communication channel between the Scheduler and the Elevators.
 * ELEVATOR_MOVING: State indicating the movement of the elevator (Up/Down)
 * ELEVATOR_STOPPED: State indicating the elevator has reached a floor (Stopped)
 * DOORS_OPENING_PASSENGER_BOARDING: State indicating the elevator Opening Door movement and is also used to indicate
 * the passenger boarding sequence
 * DOORS_CLOSING_PASSENGER_EXITING: State indicating the elevator Door Closing movement and is also used to indicate the
 * passenger exiting sequence
 * ELEVATOR_IDLE_WAITING_FOR_REQUEST: State indicative of the elevator waiting for a request call after movement is complete and
 * indicating the initial state of the elevator and is also
 */
public enum State {
    ELEVATOR_MOVING,
    ELEVATOR_STOPPED,
    DOORS_OPENING,
    DOORS_CLOSING,
    ELEVATOR_IDLE_WAITING_FOR_REQUEST,
    SCHEDULER_SENDS_REQUEST_TO_ELEVATOR,
    SCHEDULER_RECEIVES_REQUEST_FROM_FLOOR;
}
