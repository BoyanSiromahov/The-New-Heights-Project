/**
	 * @author Shaun Gordon
	 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class Main.
 */
public class Main 
{
	/**
	 * Reads CSV file into a list of arrays.  
	 *
	 * @return the list
	 * @throws ParseException the parse exception
	 */
	public List<String[]> csvReader() throws ParseException
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
	
	/**
	 * Converts a list of string arrays to an array of dates (minutes, seconds, milliseconds).  Can be used for parsed info from CSV   
	 *
	 * @param list the list
	 * @return the date[]
	 * @throws ParseException the parse exception
	 */
	public Date[] toDateArray(List<String[]> list) throws ParseException
	{
		DateFormat standard = new SimpleDateFormat("mm:ss.SSS");
		Date date = null;	
		Date[] array =  new Date[list.size()];
		
		
		for (int i = 0; i<list.size(); i++)
		{
			date = standard.parse(list.get(i)[0]);
			array[i] = date;
			System.out.println(array[i]);
		}
		return array;
	}
	
	/**
	 * Converts a list of string arrays to an array of Integers.  Can be used for parsed info from CSV   
	 *
	 * @param list the list
	 * @param column the column
	 * @return the integer[]
	 */
	public Integer[] toIntArray(List<String[]> list, int column)
	{
		Integer[] array =  new Integer[list.size()];
		for (int i = 0; i<list.size(); i++)
		{
			array[i] = Integer.parseInt(list.get(i)[column]);
			System.out.println(array[i]);
		}
		return array;
		
	}


	public static void main(String[] args) throws ParseException 
	{
		List<String[]> elevatorData = new ArrayList<>();
		Main main = new Main();
		elevatorData = main.csvReader();
	}
}
