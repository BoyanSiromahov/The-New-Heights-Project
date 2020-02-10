package Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Running tests for the smaller classes
 */
@RunWith(Suite.class)
@SuiteClasses({ ArrivalSensorTest.class, DirectionTest.class, ElevatorMotorTest.class, ElevatorButtonTest.class,
		ElevatorDoorTest.class, ElevatorTest.class, ParserTest.class, StateTest.class})

/**
 * Class to execute all JUnit Tests
 * 
 * @author Muneeb Nasir
 */

public class TestAll {

}