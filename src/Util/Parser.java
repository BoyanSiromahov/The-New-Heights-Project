package Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import ElevatorSubSystem.Direction;

public class Parser {
	private Date startTime;
	private int startFloor;
	private int endFloor;
	private Direction direction;

	public Parser() {
		startTime = null;
		startFloor = -1;
		endFloor = -1;
		direction = null;

	}

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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getStartFloor() {
		return startFloor;
	}

	public void setStartFloor(int startFloor) {
		this.startFloor = startFloor;
	}

	public int getEndFloor() {
		return endFloor;
	}

	public void setEndFloor(int endFloor) {
		this.endFloor = endFloor;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "Floor Call event: Start floor, " + startFloor + " End floor, " + endFloor + " called using, "
				+ direction + " at, " + startTime;
	}
}
