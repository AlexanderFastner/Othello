import java.util.ArrayList;
import java.util.Random;
import szte.mi.*;

public class AI implements Player {

    ConsoleGameboard cgb;
    int pColor;
    int intoColor;
    boolean oColor;
    boolean color;
    ArrayList<Move> possibleMoves = new ArrayList<Move>();
    ArrayList<Move> betterPossibleMoves = new ArrayList<>();
    ArrayList<Move> edgePossibleMoves = new ArrayList<>();
    ArrayList<Move> toFlip = new ArrayList<Move>();

    //create dir vectors
    Pair TOPLEFT = new Pair(-1, -1);
    Pair TOPCENTER = new Pair(0, -1);
    Pair TOPRIGHT = new Pair(1, -1);
    Pair CENTERLEFT = new Pair(-1, 0);
    Pair CENTERRIGHT = new Pair(1, 0);
    Pair BOTTOMLEFT = new Pair(-1, 1);
    Pair BOTTOMCENTER = new Pair(0, 1);
    Pair BOTTOMRIGHT = new Pair(1, 1);
    Pair [] vector = {TOPLEFT, TOPCENTER, TOPRIGHT, CENTERLEFT, CENTERRIGHT, BOTTOMLEFT, BOTTOMCENTER, BOTTOMRIGHT};

    //edges not next to corners
    Move e02 = new Move(0, 2);
    Move e03 = new Move(0, 3);
    Move e04 = new Move(0, 4);
    Move e05 = new Move(0, 5);
    Move e20 = new Move(2, 0);
    Move e30 = new Move(3, 0);
    Move e40 = new Move(4, 0);
    Move e50 = new Move(5, 0);
    Move e72 = new Move(7, 2);
    Move e73 = new Move(7, 3);
    Move e74 = new Move(7, 4);
    Move e75 = new Move(7, 5);
    Move e27 = new Move(2, 7);
    Move e37 = new Move(3, 7);
    Move e47 = new Move(4, 7);
    Move e57 = new Move(5, 7);
    //next to corners
    Move bm01 = new Move(0, 1);
    Move bm10 = new Move(1, 0);
    Move bm11 = new Move(1, 1);
    Move bm60 = new Move(6, 0);
    Move bm61 = new Move(6, 1);
    Move bm71 = new Move(7, 1);
    Move bm06 = new Move(0, 6);
    Move bm16 = new Move(1, 6);
    Move bm17 = new Move(1, 7);
    Move bm66 = new Move(6, 6);
    Move bm67 = new Move(6, 7);
    Move bm76 = new Move(7, 6);
    //corners
    Move gm00 = new Move(0, 0);
    Move gm07 = new Move(0, 7);
    Move gm70 = new Move(7, 0);
    Move gm77 = new Move(7, 7);
    //weight arrays
    Move [] gweights = {gm00,gm07,gm70,gm77};
    Move [] bweights = {bm01,bm10,bm11,bm60,bm61,bm71,bm06,bm16,bm17,bm66,bm67,bm76};
    Move [] eweights = {e02,e03,e04,e05,e20,e30,e40,e50,e72,e73,e74,e75,e27,e37,e47,e57};

    public AI(){
        //TODO player color
        cgb = new ConsoleGameboard(1);

    }

    @Override
    public void init(int order, long t, Random rnd) {
        pColor = order;
        if(pColor == 0){
            intoColor = 1;
            oColor = false;
            color = true;
        }
        else {
            intoColor = 0;
            oColor = true;
            color = false;
        }
    }

    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {

        //update board with previous move
        cgb.updateBoard(prevMove, oColor);

        //update opponents flips
        if (prevMove != null) {
            for (int i = 0; i < vector.length; i++) {
                reverseFlip(prevMove.x, prevMove.y, intoColor,i);
            }
        }

        //check list of possible moves
        //making array of valid moves for the current game State
        Move temp;
        int curY =0;
        for(int i = 0; i < 64; i++){
            temp = new Move(i%8, curY);
            if(i != 0 && temp.x % 8 == 0){
                curY++;
                temp = new Move(0, curY);
            }

            if (cgb.gameBoardT[i] == false) {
                if (checkValidMove(temp, pColor)) {
                    possibleMoves.add(temp);
                }
            }
        }

        cgb.printBoard();
        //debugging
        System.out.print("Possible Moves:");
        for(int i = 0; i < possibleMoves.size(); i++){
            System.out.print(possibleMoves.get(i).x + "," + (possibleMoves.get(i).y + " "));
        }
        System.out.println();
        //if no moves return null
        if(possibleMoves.size() == 0){
            System.out.println("There are no possible moves for you");
            return null;
        }

        Move selected = null;
            int selection = -1;
            boolean betterMove = false;
            while (selected == null) {
                //prioritize corners
                for (int i = 0; i < gweights.length; i++) {
                    for (int j = 0; j < possibleMoves.size(); j++) {
                        if ((gweights[i].x == possibleMoves.get(j).x) && (gweights[i].y == possibleMoves.get(j).y)) {
                            System.out.println("My corner bitch");
                            selected = possibleMoves.get(j);
                            betterMove = true;
                        }
                    }
                }
                if (!betterMove) {
                    //try to get on edges while not next to corner
                    for (int i = 0; i < eweights.length; i++) {
                        for (int j = 0; j < possibleMoves.size(); j++) {
                            if (((eweights[i].x == possibleMoves.get(j).x) && (eweights[i].y == possibleMoves.get(j).y))) {
                                edgePossibleMoves.add(possibleMoves.get(j));
                            }
                        }
                    }
                    System.out.print("Possible Edge Moves: ");
                    for (int i = 0; i < edgePossibleMoves.size(); i++) {
                        System.out.print(edgePossibleMoves.get(i).x + "," + (edgePossibleMoves.get(i).y + " "));
                    }

                    //select from edge moves at random
                    if (edgePossibleMoves.size() > 0) {
                        selection = new Random().nextInt(edgePossibleMoves.size());
                        selected = edgePossibleMoves.get(selection);
                        betterMove = true;
                    }

                }
                if (!betterMove) {
                    //avoid spaces next to corners
                    //generate a new arraylist - the bad moves
                    for (int i = 0; i < possibleMoves.size(); i++) {
                        betterPossibleMoves.add(possibleMoves.get(i));
                    }

                    for (int i = 0; i < bweights.length; i++) {
                        for (int j = 0; j < possibleMoves.size(); j++) {
                            if (((bweights[i].x == possibleMoves.get(j).x) && (bweights[i].y == possibleMoves.get(j).y))) {
                                betterPossibleMoves.remove(possibleMoves.get(j));
                            }
                        }
                    }
                    System.out.print("Better Possible Moves: ");
                    for (int i = 0; i < betterPossibleMoves.size(); i++) {
                        System.out.print(betterPossibleMoves.get(i).x + "," + (betterPossibleMoves.get(i).y + " "));
                    }
                    System.out.println();
                    //select from better moves at random
                    if (betterPossibleMoves.size() > 0) {
                        selection = new Random().nextInt(betterPossibleMoves.size());
                        selected = betterPossibleMoves.get(selection);
                        betterMove = true;
                    }
                }
                if (!betterMove) {
                    //else just select random
                    if (selected == null && possibleMoves.size() > 0) {
                        selection = new Random().nextInt(possibleMoves.size());
                        selected = possibleMoves.get(selection);
                    }
                    if (selected == null) {
                        return null;
                    }
                }
        }

        //debugging
        if(selected != null) {
            System.out.println(selected.x + " " + selected.y);
        }

        //update with own move
        cgb.updateBoard(selected, color);

        //update own flips
        for (int i = 0; i < vector.length; i++){
            reverseFlip(selected.x, selected.y, pColor,i);
        }
        //clear possiblemoves
        possibleMoves.clear();
        betterPossibleMoves.clear();
        edgePossibleMoves.clear();
        cgb.printBoard();
        return selected;
    }


    //check if a move is Valid
    public boolean checkValidMove(Move move, int pColor) {
        int x = move.x;
        int y = move.y;
        //check if there is a possible flip
        if (possibleFlip(x, y, pColor)){
            return true;
        }
        return false;
    }
    //checks if you can get a flip on this xy
    public boolean possibleFlip(int x, int y, int playerColor){
        int pieceColor = 2;

        //for each dir in vector
        for(int i = 0; i < vector.length; i++){
            //check if would be in bounds
            if ((x + vector[i].getX() >= 0) && (y + vector[i].getY() >= 0)) {
                if ((x + vector[i].getX() <= 7) && (y + vector[i].getY() <= 7)){
                    //color of the piece in the dir of vector is pieceColor
                    pieceColor = cgb.getColor(x + vector[i].getX(), (y + vector[i].getY()));
                    //if there is a neighboring piece of a different color
                    if ((pieceColor != playerColor) && (pieceColor != 2)){

                        //if found continue in that dir until your piece is found
                        if(flipHelper(x, y, playerColor, i)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean flipHelper(int x, int y, int playerColor, int direction){

        int pieceColor;
        //check bounds
        if ((x + vector[direction].getX() >= 0) && (y + vector[direction].getY() >= 0)) {
            if ((x + vector[direction].getX() <= 7) && ((y + vector[direction].getY()) <= 7)){
                //if at least 1 opposing piece is between the possible placement and another of the same players pieces a flip is possible
                pieceColor = cgb.getColor(x + vector[direction].getX(), (y + vector[direction].getY()));
                //if another piece of player color is found exit with true
                if (pieceColor == playerColor){
                    return true;
                }
                //if an empty square is found before another players tile return false
                if(pieceColor == 2){
                    return false;
                }
                //if another piece of opposing player color is found go again
                else {
                    return flipHelper(x + vector[direction].getX(), (y + vector[direction].getY()), playerColor, direction);
                }
            }
            return false;
        }
        //if board edge is reached
        return false;
    }

    //if a move is selected there is a flip
    //call flipHelper in all 8 dir until all same color pieces are found
    //if the same players piece is found is found go in opposite of direction and flip every piece on the way
    public void reverseFlip(int x, int y, int playerColor, int direction){
        Move toFliptemp;
        int pieceColor;

        if ((x + vector[direction].getX() >= 0) && (y + vector[direction].getY() >= 0)) {
            if ((x + vector[direction].getX() <= 7) && ((y + vector[direction].getY()) <= 7)){
                //now keep checking in the same dir
                pieceColor = cgb.getColor(x + vector[direction].getX(), (y + vector[direction].getY()));
                //if another enemy piece is found do again
                if ((pieceColor != playerColor) && (pieceColor != 2)) {
                    toFliptemp = new Move(x + vector[direction].getX(), (y + vector[direction].getY()));
                    toFlip.add(toFliptemp);
                    reverseFlip(x + vector[direction].getX(), (y + vector[direction].getY()), playerColor, direction);
                }
                //This method is only called when at least 1 enemy piece was detected in this dir first
                //so if a friendly piece is detected, end and carry out the flip
                if ((pieceColor == playerColor) && (toFlip.size() > 0)) {
                    //for every element in toFlip
                    for (int i = 0; i < toFlip.size(); i++) {
                        cgb.setColor(toFlip.get(i).x, toFlip.get(i).y, playerColor);
                    }
                    toFlip.clear();
                }
                if (pieceColor == 2) {
                    //if it runs into an empty square none of the previous entries should be flipped, so clear
                    toFlip.clear();
                }
            }
            //if it runs into an edge its not a possible flip
            else {
                toFlip.clear();
            }
        }
        //if it runs into an edge its not a possible flip
        else {
            toFlip.clear();
        }
    }
    public ConsoleGameboard getBoard() {
        return cgb;
    }
    public int getpColor(){
        return pColor;
    }
}
