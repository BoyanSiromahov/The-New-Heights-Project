# The-New-Heights-Project

## Elevator Simulation System: Final Iteration


### ElevatorSubSystem
    Author: Muneeb Nasir
    
    Elevator.java - The Class represanting elevator car
		
    ArrivalSensor.java - The  Class represanting the arrival sensor for the elevator 
	
    Direction.java - The Enum Class represanting the Direction for the elevator 
    	    
    ElevatorDoor.java - The Class represanting the Elevator Doors 
    
    ElevatorMotor.java - The Class represanting the elevator Motor  
           
    ElevatorState.java - The Class represanting the elevator subsystem & state  (State Machine)  
   
    Faults.java - The Enum Class representing the faults associated with the system 
   
### FloorSubSystem
    Author: Samantha Tripp
	
    UDPHelper.java - The UDP Communication Helper Class
	
    Floor.java - The Class represanting Floors of the building
	    
			
### SchedulerSubSystem
    Author: Boyan Siromahov
    
    Scheduler.java - The Class representing the Scheduler (Communication Channel B/w Elevator and Floor)
    
    EventHandler.java - The Helper Class For The Scheduler 
    
### Util
    Author: Shaun Gordon
    
    Parser.java - The Class representing the Parser for the input command file (Input Processing Unit)
    
====================================================================

Responsibilities

    1. Boyan Siromahov - SchedulerSubSystem - UDP Communication Development 
    2. Shaun Gordon - SchedulerSubSystem & ElevatorSubSystem 
    3. Muneeb Nasir - SchedulerSubSystem & ElevatorSubSystem - Fault Handling - Unit Tests
    4. Samantha Tripp - UDP Communication Development 
    5. Britney Baker - UML Diagrams
    
====================================================================

#### Run Instructions

1. Run Scheduler.java
2. Run Elevator.java
3. Run Floor.java

#### Testing Instructions

1. Run TestAll.java (Runs JUnit Test for classes)

    The TestAll.java is responsible for running all the JUnit Test for individual classes in the project.
    
    Expected Output: 
    
        UDP Handler: waiting for packet
        Port 22: Packet received
        From host: /192.168.56.1
        Host port: 30
        Data as string: Thu Jan 01 00:00:39 EST 1970,2,1,DOWN                                                               
        Data in bytes: 84, 104, 117, 32, 74, 97, 110, 32, 48, 49, 32, 48, 48, 58, 48, 48, 58, 51, 57, 32, 69, 83, 84, 32, 49, 57, 55, 48, 44, 50, 44, 49, 44, 68, 79, 87, 78, 
        
        Request received: Thu Jan 01 00:00:39 EST 1970,2,1,DOWN                                                               
        [TIME: 00:00:39] [ELEVATOR] [INFO] Elevator 1 Currently In Service Receives Request
        [TIME: 00:00:41] [ELEVATOR] [INFO] Elevator 1 Boarding
        [TIME: 00:00:43] [ELEVATOR] [INFO] Elevator 1 Doors Closing
        [TIME: 00:00:47] [ELEVATOR] [INFO] Elevator 1 Moving Down to Floor Number: 1 From: 2
        [TIME: 00:00:47] [ELEVATOR] [INFO] Elevator 1 Has Reached Floor Number: 1
        [TIME: 00:00:49] [ELEVATOR] [INFO] Elevator 1 Doors Opening
        
        [TIME: 00:00:51] [ELEVATOR] [INFO] Passengers Exiting Elevator 1
        [TIME: 00:00:51] [ELEVATOR] [INFO] Elevator arrived at floor: 1
        
        
====================================================================


