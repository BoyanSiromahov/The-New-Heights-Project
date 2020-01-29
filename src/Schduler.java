import java.util.LinkedList;
import java.util.Queue;

public class Schduler {

    public synchronized Queue<Floor> move() {
        Queue<Floor> temp =  new LinkedList<>();
        temp.add(new Floor(5, Direction.UP));
        temp.add(new Floor(1, Direction.DOWN));
        return temp;
    }
}
