package Tests;

import FloorSubSystem.Floor;
import Util.CallEvent;
import Util.Parser;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * FloorFaultTest (Fault Test)
 *
 * @author Britney Baker
 * @author Samantha Tripp
 */

public class FloorFaultTest {
    public static void main(String[] args) throws ParseException {

        Parser parser = new Parser();
        List<CallEvent> elevatorEvents = new ArrayList<CallEvent>();
        List<String[]> csvData = new ArrayList<String[]>();
        csvData = Parser.faultReader();
        elevatorEvents = parser.makeList(csvData);

        Floor f = new Floor(elevatorEvents);
        try {
            f.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
