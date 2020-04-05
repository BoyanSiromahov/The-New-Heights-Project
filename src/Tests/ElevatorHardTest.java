package Tests;

import ElevatorSubSystem.Elevator;
/**
 * ElevatorHardTest
 * @author Britney Baker
 * @author Samantha Tripp
 */
public class ElevatorHardTest {
    public static void main(String[] args) {
        Thread elevatorThread_1, elevatorThread_2, elevatorThread_3;
        elevatorThread_1 = new Thread(new Elevator(1,22),"Elevator NO.1");
        elevatorThread_2 = new Thread(new Elevator(2,24),"Elevator NO.2");

        elevatorThread_1.start();
        elevatorThread_2.start();
    }

}
