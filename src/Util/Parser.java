package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	

	public static List<String> csvReader()
	{ 
	    String file = "csv.txt";
	    List<String> content = new ArrayList<>();

	    BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
	        String line = "";
	        while ((line = br.readLine()) != null) 
	        {
	        	if(!line.contains("Time,")) {
	        		content.add(line);
	            }
	        }
		} catch (IOException e) {
			System.out.println("fail");
		}

	    return content;
	}
	
}
