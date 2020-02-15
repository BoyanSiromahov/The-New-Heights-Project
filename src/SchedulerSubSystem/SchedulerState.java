package SchedulerSubSystem;

/**
 * The STATE MACHINE Implementation done using the SchedulerState Enum Class
 *
 * The ENUM Class that is responsible for state execution and the control of the Scheduler System.
 * IDLE: Indicated that the elevator has finished it quequed up actions and is waiting for a call
 * E_ARRIVED: Indicates that the elevator has reached its designated floor and will be picking up passengers
 * E_BOARDED: Indicates that passengers are inside the elevator
 * E_MOVING: Indicates that the elevator is busy and moving 
 * E_REQUESTED: Indicates that the elevator has a pending call
 *
 * @author Boyan Siromahov
 */
public enum SchedulerState {
	IDLE,
	E_ARRIVED,
	E_BOARDED,
	E_MOVING,
	E_REQUESTED
}
