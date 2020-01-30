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
import java.util.List;
import Util.Parser;

/**
 * The Class Main.
 */
public class Main 
{

	public static void main(String[] args) throws ParseException 
	{
		List<String[]> csvContent = new ArrayList<String[]>();
		List<Parser> elevatorData = new ArrayList<Parser>();
		Parser parser = new Parser();
		
		csvContent = parser.csvReader();
		elevatorData = parser.makeList(csvContent);
	}
}
