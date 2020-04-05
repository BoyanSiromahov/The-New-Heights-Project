package Tests;

import SchedulerSubSystem.Scheduler;

import java.net.UnknownHostException;
/**
 * SchedulerHardTest
 * @author Britney Baker
 * @author Samantha Tripp
 */

public class SchedulerHardTest {
    public static void main(String[] args) throws UnknownHostException {

        Scheduler schedulerControl = new Scheduler();

        //Thread 1 - Communication Link B/w Scheduler & Floor
        Thread floor_To_Scheduler = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        schedulerControl.elevatorRequest();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Floor_Scheduler_Communication_Link");
        floor_To_Scheduler.start();

        //Thread 2 - Communication Link B/w Scheduler & Elevator
        Thread scheduler_To_Elevator = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    schedulerControl.elevatorStatus();
                }
            }
        }, "Scheduler_Elevator_Communication_Link");
        scheduler_To_Elevator.start();

        //Thread 3 - Communication Link B/w Scheduler & Elevator For Fault Handling
        Thread scheduler_Elevator_Fault_Handler = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    schedulerControl.handleElevatorFault();
                }
            }
        }, "scheduler_Elevator_Fault_Handler");
        scheduler_Elevator_Fault_Handler.start();

    }
}
