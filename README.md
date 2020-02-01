# The-New-Heights-Project

## Elevator Simulation System: Iteration 1


### ElevatorSubSystem
    Author: Muneeb Nasir
    
    Elevator.java - The Class represanting elevator car
		
	ArrivalSensor.java - The Enum Class represanting the arrival sensor for the elevator 
	
	Direction.java - The Enum Class represanting the Direction for the elevator 
    	    
    ElevatorButton.java - The Enum Class represanting the floor buttons inside the elevator 
    
    ElevatorDoor.java - The Enum Class represanting the elevator doors 
           
    ElevatorMotor.java - The Enum Class represanting the elevator motor 

   
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
    3. Muneeb Nasur - ElevatorSubSystem Development
    4. Samantha Tripp - FloorSubSystem Development
    5. Britney Baker - UML Diagram
    
====================================================================

#### Run Instructions

1. Run Main.java

#### Testing Instructions

1. Run TestAll.java (Runs JUnit Test for classes)

    The TestAll.java is responsible for running all the JUnit Test for individual classes in the project.
    
    Expected Output from the successful run of the JUnit Test:
    
        JUnit Test: The Elevator Class successfully identifies the Invalid Request
        
        Invalid Request Sent By Scheduler

2. Run Floor_Scheduler_ElevatorTest.java (Test Case for Communication Channel)
    
    This is used to check the running of the entire system as a single component with active threads