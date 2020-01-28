import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main 
{
	public List<String[]> csvReader()
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
	    return content;
	}
	
	public static void main(String[] args) 
	{
		List<String[]> elevatorData = new ArrayList<String[]>();
		Main main = new Main();
		elevatorData = main.csvReader();
	}
}
