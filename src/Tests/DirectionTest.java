package Tests;

import ElevatorSubSystem.Direction;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Direction Enum JUnit Test Case
 * 
 * @author Muneeb Nasir
 */
public class DirectionTest {

	@Test
	public void enumStringTest() {
		assertEquals("UP", Direction.UP.toString());
		assertEquals("DOWN", Direction.DOWN.toString());
	}

}