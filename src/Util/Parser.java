package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import ElevatorSubSystem.Direction;

/**
 *
 * The Parser Class that is responsible for reading the input file and creates the list of callEvents for the elevator.  
 * @author Shaun Gordon
 */
public class Parser 
{


	/**
	 * The function reads in the input data and processes it into a List to be sent
	 * to the Floor Class
	 * @return List, the processed csv file data.
	 */
	public static List<String[]> csvReader() {

		String file = String.format("csv.txt");
		List<String[]> content = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				content.add(line.split(","));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		content.remove(0); // Deletes title.
		return content;
	}

	/**
	 * The main function that further process the input parsed data in Floor class compatible
	 * instance types into a List of CallEvent Object.
	 * @param originaList, The original processed data.
	 * @return CallEvent List, The Parser object that is populated from the input data.
	 */
	public List<CallEvent> makeList(List<String[]> originaList) {
		List<CallEvent> newList = new ArrayList<CallEvent>();
		CallEvent tempParser;
		int startFloor = 0;
		int endFloor = 0;
		Direction direction = null;
		Date date = null;

		SimpleDateFormat standard = new SimpleDateFormat("HH:mm:ss"); // Hours/Minutes/seconds/milliseconds

		// Changing data to proper types
		for (int i = 0; i < originaList.size(); i++) {
			//
			for (int j = 0; j < originaList.get(i).length; j++) {
				if (j == 0) {
					try {

						date = standard.parse((String) originaList.get(i)[j]);

					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (j == 1) {
					startFloor = Integer.parseInt(originaList.get(i)[j]);
				} else if (j == 2) {
					endFloor = Integer.parseInt(originaList.get(i)[j]);
				} else if (j == 3) {
					direction = Direction.valueOf(originaList.get(i)[j]);
				}
			}
			tempParser = new CallEvent(date, startFloor, endFloor, direction);
			newList.add(tempParser);
		}
		return newList;
	}


}
