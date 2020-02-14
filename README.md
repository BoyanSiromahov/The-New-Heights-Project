# The-New-Heights-Project

## Elevator Simulation System: Iteration 2


### ElevatorSubSystem
    Author: Muneeb Nasir
    
    Elevator.java - The Class represanting elevator car
		
	ArrivalSensor.java - The Enum Class represanting the arrival sensor for the elevator 
	
	Direction.java - The Enum Class represanting the Direction for the elevator 
    	    
    ElevatorButton.java - The Enum Class represanting the floor buttons inside the elevator 
    
    ElevatorDoor.java - The Enum Class represanting the elevator doors 
           
    ElevatorMotor.java - The Enum Class represanting the elevator motor 
    
    State.java - The Class represanting different states of the System (STATE MACHINE) 
   
### FloorSubSystem
	Author: Samantha Tripp
	
	Floor.java - The Class represanting Floors of the building
	    
			
### SchedulerSubSystem
    Author: Boyan Siromahov
    
    Scheduler.java - The Class representing the Scheduler (Communication Channel B/w Elevator and Floor)

### Util
    Author: Shaun Gordon
        
    Parser.java - The Class representing the Parser for the input command file (Input Processing Unit)
    
====================================================================

Responsibilities

    1. Boyan Siromahov - SchedulerSubSystem Development 
    2. Shaun Gordon - Util (Parser) Development
    3. Muneeb Nasur - ElevatorSubSystem - State Machine Development
    4. Samantha Tripp - FloorSubSystem Development - State Machine Diagram 
    5. Britney Baker - UML Diagrams
    
====================================================================

#### Run Instructions

1. Run Main.java

#### Testing Instructions

1. Run TestAll.java (Runs JUnit Test for classes)

    The TestAll.java is responsible for running all the JUnit Test for individual classes in the project.
    
    Expected Output from the successful run of the JUnit Test:
    
       [JUNIT TESTING]

	JUnit Test: The Elevator Class successfully identifies the Invalid Request


2. Run Floor_Scheduler_ElevatorTest.java (Test Case for Communication Channel)
    
    This is used to check the running of the entire system as a single component with active threads incorporated 
    with the State Machine 
    
    Expected Outcome: Showing all the states of the machine and the timing overview  
    
    	Floor sending event to scheduler:
        Floor Call event: Start floor, 1 End floor, 2 called using, UP at, Thu Jan 01 00:00:05 EST 1970
        Request recieved
        Scheduler sending event to elevator:
        Floor Call event: Start floor, 1 End floor, 2 called using, UP at, Thu Jan 01 00:00:05 EST 1970
        [TIME: 00:00:05] [ELEVATOR] [INFO] Elevator 1 Currently In Service Receives Request
        [TIME: 00:00:07] [ELEVATOR] [INFO] Elevator 1 Boarding
        [TIME: 00:00:09] [ELEVATOR] [INFO] Elevator 1 Doors Closing
        [TIME: 00:00:13] [ELEVATOR] [INFO] Elevator 1 Moving UP To Floor Number: 2 From: 1
        [TIME: 00:00:13] [ELEVATOR] [INFO] Elevator 1 Has Reached Floor Number: 2
        [TIME: 00:00:15] [ELEVATOR] [INFO] Elevator 1 Doors Opening
    
        [TIME: 00:00:17] [ELEVATOR] [INFO] Passengers Exiting Elevator 1
        [TIME: 00:00:17] [ELEVATOR] [INFO] Elevator arrived at floor: 2