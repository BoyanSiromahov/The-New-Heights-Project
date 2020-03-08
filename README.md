# The-New-Heights-Project

## Elevator Simulation System: Iteration 3


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
    EventHandler.java - The Helper Class For The Scheduler 
    
### Util
    Author: Shaun Gordon
    
    UDPHelper.java - The UDP Communication Helper Class
    Parser.java - The Class representing the Parser for the input command file (Input Processing Unit)
    
====================================================================

Responsibilities

    1. Boyan Siromahov - SchedulerSubSystem - UDP Communication Development 
    2. Shaun Gordon - SchedulerSubSystem & ElevatorSubSystem - Multi-Elevator System Development
    3. Muneeb Nasur - SchedulerSubSystem & ElevatorSubSystem - Multi-Elevator System Development - Unit Tests
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
    