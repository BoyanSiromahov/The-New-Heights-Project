package Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import ElevatorSubSystem.Direction;

/**
 * The Parser Class that is responsible for reading the input file and processing for
 * the FLoor Class
 * @author Shaun Gordon
 */
public class Parser {
	private Date startTime;
	private int startFloor;
	private int endFloor;
	private Direction direction;

	/**
	 * The Constructor of the class with default values
	 */
	public Parser() {
		startTime = null;
		startFloor = -1;
		endFloor = -1;
		direction = null;

	}

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
	 * instance types into a List of Parser Object.
	 * @param originaList, The original processed data
	 * @return Parser List, The Parser object that is populated from the input data.
	 */
	public List<Parser> makeList(List<String[]> originaList) {
		List<Parser> newList = new ArrayList<Parser>();
		Parser tempParser;
		int number;
		Direction direction = null;

		SimpleDateFormat standard = new SimpleDateFormat("HH:mm:ss"); // Hours/Minutes/seconds/milliseconds
		Date date = null;

		// Changing data to proper types
		for (int i = 0; i < originaList.size(); i++) {
			tempParser = new Parser();
			for (int j = 0; j < originaList.get(i).length; j++) {
				if (j == 0) {
					try {
						// System.out.println("converting date: " + (String) originaList.get(i)[j]);
						date = standard.parse((String) originaList.get(i)[j]);
						// System.out.println("new date: " + date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					tempParser.setStartTime(date);
				} else if (j == 1) {
					number = Integer.parseInt(originaList.get(i)[j]);
					tempParser.setStartFloor(number);
				} else if (j == 2) {
					number = Integer.parseInt(originaList.get(i)[j]);
					tempParser.setEndFloor(number);
				} else if (j == 3) {
					direction = Direction.valueOf(originaList.get(i)[j]);
					tempParser.setDirection(direction);
				}
			}
			newList.add(tempParser);
		}
		return newList;
	}

	/**
	 * Getter Method for the Start Time
	 * @return Date, The time at which the command is to be sent
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Setter Method for the Start Time
	 * @param startTime, The time at which the command is to be sent
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Getter Method for the request originated floor
	 * @return startFloor, The requested floor
	 */
	public int getStartFloor() {
		return startFloor;
	}

	/**
	 * Setter Method for the Start Floor
	 * @param startFloor, The floor from where the request originates
	 */
	public void setStartFloor(int startFloor) {
		this.startFloor = startFloor;
	}

	/**
	 * Getter Method for the destination floor
	 * @return endFloor, The Destination Floor
	 */
	public int getEndFloor() {
		return endFloor;
	}

	/**
	 * Setter Method for the destination floor
	 * @return endFloor, The requested destination
	 */
	public void setEndFloor(int endFloor) {
		this.endFloor = endFloor;
	}

	/**
	 * Getter Method for the Command Direction
	 * @return direction, The requested direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Setter Method for the Command Direction
	 * @param direction, The requested direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * The method overrides the Object String method
	 * @return String, The formatted string containing the Parser Object Data.
	 */
	@Override
	public String toString() {
		return "Floor Call event: Start floor, " + startFloor + " End floor, " + endFloor + " called using, "
				+ direction + " at, " + startTime;
	}
}
