package ElevatorSubSystem;

import Util.CallEvent;
import Util.UDPHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The STATE MACHINE Implementation done using the ElevatorState Enum Class. This Class Is Responsible For The
 * Communication Management With The Scheduler. Communicates The State and The Floors Of The Elevator
 *
 * @author Muneeb Nasir
 */

/**
 * The ENUM Class that is responsible for state execution and the control of the Elevator System.
 * MOVING: State indicating the movement of the elevator (Up/Down)
 * IDLE: State indicative of the elevator waiting for a request call after movement is complete and
 * indicating the initial state of the elevator and is also
 * */
enum SystemState{
    MOVING, // EM
    IDLE, // WI
}

public class ElevatorState{

    private static final int ELEVATOR_SCHEDULER_PORT = 30; // Scheduler Port

    private int elevatorLevel;
    private int elevatorPort;
    private int elevatorNumber;
    private SystemState e_State;

    // SubSystem Communicating With The Scheduler (Transmits Elevator's Current State/Floor)
    private UDPHelper elevatorStateHelper;

    // Queue That Is Received From The Scheduler
    //    private List<CallEvent> elevatorStatus;


    public ElevatorState(int port, int number){
        elevatorLevel = 0;
        e_State = SystemState.IDLE; // Initial State
        //The IP Address Of the Elevator is the Same
        this.elevatorStateHelper = new UDPHelper(port);
        elevatorNumber = number;
        elevatorPort = (port - 1); // Dedicated Port For The Elevator
    }

    /**
     * The method is used to return the Current State of the Elevator
     * @return State, The Current State of the elevator
     */
    public SystemState getElevatorSTate(){
        return this.e_State;
    }

    /**
     * The function is used to modify the state of the elevator
     * specified. It also sends the UDP Packet To the scheduler to inform the
     * scheduler about the current state of the elevator
     * @param State, The required changed of the state
     **/
    public void setSystemStateChange(String State){
        switch (State){

            case "EM":
                e_State = SystemState.MOVING;
                sendElevatorStatus();
                break;

            case "EI":
                e_State = SystemState.IDLE;
                sendElevatorStatus();
                break;
        }
    }

    /**
     * The Function Is used to constantly update the current floor level of the
     * elevator
     * @param floorLevel, The level of the Elevator
     */
    public void setCurrentFloor(int floorLevel){
        elevatorLevel = floorLevel;
    }

    /**
     * To Check If the current elevator is idle
     * @return True, If The elevator is IDLE
     * */
    public boolean elevatorIdle(){
        return (e_State == SystemState.IDLE);
    }

    /**
     * Is used to send the current status of the elevator to the Scheduler
     */
    public void sendElevatorStatus(){
        try {
            elevatorStateHelper.send(new byte[]{
                            (byte) elevatorNumber, // Elevator Number
                            (byte) elevatorPort, // Dedicated Elevator Port
                            (byte) e_State.ordinal(), // 0 - Moving, 1 - IDLE
                            (byte) elevatorLevel // The Current Floor Level Of The Elevator
                    }, ELEVATOR_SCHEDULER_PORT, true, InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }


}
