package Util;

/**
 * The Enum Class For Faults, Fault Handling Is Done Based On The Levels
 * DOOR: Elevator Doors Stuck (Minor Fault)
 * ELEVATOR: Elevator Stuck B/w Floors (Major Fault)
 * SENSOR: Arrival Sensor Fault (Minor Fault)
 *
 * These Faults are specified in the CSV Files as -1 & -2
 * -2 Represents Hard Faults
 * -1 Represents Soft Faults
 *
 * @author Muneeb Nasir
 * @version 4.0
 **/

public enum Faults {
    SENSOR,
    ELEVATOR,
    DOOR;
}
