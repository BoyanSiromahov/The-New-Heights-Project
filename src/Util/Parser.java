/*
 * 
 */
package Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Parser 
{
	private Date startTime;
	private int startFloor;
	private int endFloor;
	private String direction;
	public Parser()
	{
		startTime = null;
		startFloor = -1;
		endFloor = -1;
		direction = null;
		
	}

	public static List<String[]> csvReader()
	{ 
		String file = "../csv.txt";
	    List<String[]> content = new ArrayList<>();
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(file))) 
	    {
	        String line = "";
	        while ((line = br.readLine()) != null) 
	        {
	            content.add(line.split(","));
	        }
	    } catch (FileNotFoundException e) {
	    } catch (IOException e1) {
			e1.printStackTrace();
		}
	    content.remove(0);  //Deletes titles.
	    return content;
	}
	
	
	public List<Parser> makeList(List<String[]> originaList)
	{
		List<Parser> newList = new ArrayList<Parser>();
		Parser tempParser;
		int number;
		
		DateFormat standard = new SimpleDateFormat("mm:ss.SSS");
	    Date date = null;
		for (int i=0; i<originaList.size(); i++)
	    {
			tempParser = new Parser();
	    	for (int j=0; j<originaList.get(i).length; j++)
	    	{
	    		if (j==0)
	    		{		
	    			try {
						date = standard.parse((String) originaList.get(i)[j]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
	    			tempParser.setStartTime(date);
	    		}
	    		else if (j==1)
	    		{
	    			number = Integer.parseInt(originaList.get(i)[j]);
	    			tempParser.setStartFloor(number);
	    		}
	    		else if (j==2)
	    		{
	    			number = Integer.parseInt(originaList.get(i)[j]);
	    			tempParser.setEndFloor(number);
	    		}
	    		else if (j==3)
	    		{
	    			tempParser.setDirection(originaList.get(i)[j]);
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

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
