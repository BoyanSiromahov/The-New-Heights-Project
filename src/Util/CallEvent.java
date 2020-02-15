package Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import ElevatorSubSystem.Direction;

/**
 * The CallEvent class contains information about the elevator events that will be read from the csv
 * @author Shaun Gordon
 */
public class CallEvent {
	private Date startTime;
	private int startFloor;
	private int endFloor;
	private Direction direction;

	/**
	 * The Constructor of the class with default values
	 */
	public CallEvent(Date startTime, int startFloor, int endFloor, Direction direction) 
	{
		this.startTime = startTime;
		this.startFloor = startFloor;
		this.endFloor = endFloor;
		this.direction = direction;

	}


	/**
	 * Getter Method for the Start Time
	 * @return Date, The time at which the command is to be sent
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Setter Method for the Start Time
	 * @param startTime, The time at which the command is to be sent
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Getter Method for the request originated floor
	 * @return startFloor, The requested floor
	 */
	public int getStartFloor() {
		return startFloor;
	}

	/**
	 * Setter Method for the Start Floor
	 * @param startFloor, The floor from where the request originates
	 */
	public void setStartFloor(int startFloor) {
		this.startFloor = startFloor;
	}

	/**
	 * Getter Method for the destination floor
	 * @return endFloor, The Destination Floor
	 */
	public int getEndFloor() {
		return endFloor;
	}

	/**
	 * Setter Method for the destination floor
	 * @return endFloor, The requested destination
	 */
	public void setEndFloor(int endFloor) {
		this.endFloor = endFloor;
	}

	/**
	 * Getter Method for the Command Direction
	 * @return direction, The requested direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Setter Method for the Command Direction
	 * @param direction, The requested direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * The method overrides the Object String method
	 * @return String, The formatted string containing the Parser Object Data.
	 */
	@Override
	public String toString() {
		return "Floor Call event: Start floor, " + startFloor + " End floor, " + endFloor + " called using, "
				+ direction + " at, " + startTime;
	}
}
