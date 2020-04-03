package ElevatorSubSystem;

/**
 * This class is used to track Elevator Movement. The indicator can be used
 * to check the arrival and signal the arrival of the elevator to the floor.
 * REACHED_FLOOR indicates the elevator has reached the floor.
 * NOT_REACHED_FLOOR indicates the elevator has not reached the floor.
 * 
 * @author Muneeb Nasir
 */

enum SensorState{
	REACHED_FLOOR,
	NOT_REACHED_FLOOR
}


public class ArrivalSensor {

	private SensorState sensor;

	/**
	 * The Initial State (Default State) Constructor for the Arrival Sensor State
	 */
	public ArrivalSensor(){
		sensor = SensorState.NOT_REACHED_FLOOR;
	}

	/**
	 * The Getter Method Of The Current State of The
	 * Sensor
	 */
	public SensorState getSensor() {
		return sensor;
	}

	/**
	 *
	 *  Is used to track the elevator arrival on the specified floor
	 *  and the elevator movement.
	 * @param reached, True - If the elevator has reached the floor
	 *                 False - If the elevator has not reached the floor
	 * @return True, If the sensor state is changed
	 *
	 */
	public boolean setArrivalSensor(boolean reached){

		if(reached){
			sensor = SensorState.REACHED_FLOOR;
		}else{
			sensor = SensorState.NOT_REACHED_FLOOR;
		}
		return true;
	}

}
