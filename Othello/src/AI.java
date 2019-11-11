import java.util.Random;
import szte.mi.*;

public class AI implements Player {

    ConsoleGameboard cgb;

    public AI(){
        cgb = new ConsoleGameboard();

    }

    @Override
    public void init(int order, long t, Random rnd) {

    }

    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {
        return null;
    }
}
