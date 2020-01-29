package SchedulerSubSystem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Scheduler implements Runnable{
	 private List<String> content;
	
	public Scheduler(List<String> list) {
		this.content = list;
		
		for(String s:list) {
			System.out.println("obtained: " + s);
		}
		
		
		System.out.println("obtained final: " + content);
	}
	

	@Override
	public void run() {
		
		
	}
}
