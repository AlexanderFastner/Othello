import java.util.Random;

public class Human implements Player {

    ConsoleGameboard cgb;

    //each player has their own board
    //communicate only through moves
    public Human() {
        cgb = new ConsoleGameboard();

    }

    @Override
    public void init(int order, long t, Random rnd) {

    }

    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {

        return null;
    }

    public ConsoleGameboard getBoard() {
        return cgb;
    }

}
