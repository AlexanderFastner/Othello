public interface Player {

    //initialize board
    //Order selects player color
    //t is remaining runtime
    //rnd is random num gen
    void init(int order, long t, java.util.Random rnd );

    //move
    //prevMov is the previous move, Can be null
    //tOpponent is Oppenents runtime
    //t is remaining time for this player
    //should return the next move
    Move nextMove(Move prevMove, long tOpponent, long t);


}
