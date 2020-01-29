import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ElevatorSubSystem.Elevator;
import SchedulerSubSystem.Scheduler;

public class Main 
{
	
	public static void main(String[] args) 
	{
		Thread elevatorA;
		Scheduler ss = new Scheduler();

		elevatorA = new Thread(new Elevator(1, ss),"Elevator");
		elevatorA.start();
	}
}
