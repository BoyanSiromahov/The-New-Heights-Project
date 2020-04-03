package Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Running tests for the smaller classes
 * Class to execute all JUnit Tests
 *
 *
 * @author Muneeb Nasir
 */

@RunWith(Suite.class)
@SuiteClasses({ ArrivalSensorTest.class, DirectionTest.class, ElevatorMotorTest.class, ElevatorButtonTest.class,
        ElevatorDoorTest.class, ParserTest.class, FaultTest.class, SchedulerStateTest.class, CallEventTest.class})

public class TestAll {
}
