/**
	 * @author Shaun Gordon
	 */
import java.text.ParseException;
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
