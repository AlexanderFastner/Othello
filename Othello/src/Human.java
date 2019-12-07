import java.util.*;

import szte.mi.*;
import java.util.Scanner;


public class Human implements Player {

    ConsoleGameboard cgb;
    //color of this Human
    int pColor;
    int intoColor;
    boolean oColor;
    boolean color;

    int c = 0;
    ArrayList<Move> toFlip = new ArrayList<Move>();

    ArrayList<Move> possibleMoves = new ArrayList<Move>();

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

    //each player has their own board
    //communicate only through moves
    public Human() {
        cgb = new ConsoleGameboard(pColor);
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

        //update the board with prevMove
        cgb.updateBoard(prevMove, oColor);
        //TODO carry out opponents flips

        //call fliphelper in all 8 dir for the opponents move
        if (prevMove != null) {
            for (int i = 0; i < vector.length; i++) {
                flipHelper(prevMove.x, prevMove.y, intoColor, i, true);
            }
        }

        //output board
        cgb.printBoard();

        //making array of valid moves for the current game State
        possibleMoves.clear();
        Move temp;
        int curY =0;
        for(int i = 0; i < 64; i++){
            temp = new Move(i%8, curY);
            if(i != 0 && temp.x % 8 == 0){
                curY++;
                temp = new Move(0, curY);
            }


            if (cgb.gameBoardT[i] == false) {
                if (checkValidMove(temp, this.pColor)) {
                    possibleMoves.add(temp);
                }
            }
        }
        //debugging
        System.out.print("Possible Moves:");
        for(int i = 0; i < possibleMoves.size(); i++){
            System.out.print(possibleMoves.get(i).x + "," + (possibleMoves.get(i).y + " "));
        }
        System.out.println();

        //check input
        //check if posMove would be in an array of possible moves else error
        Move posMove = moveInputs();
        boolean ValidMove = false;
        while(!ValidMove) {
            for (int i = 0; i < possibleMoves.size(); i++) {
                if ((possibleMoves.get(i).x == posMove.x) && (possibleMoves.get(i).y == posMove.y)) {
                    System.out.println("This is a valid move");
                    ValidMove = true;
                }
            }
            if(possibleMoves.size() == 0){
                System.out.println("There are no possible moves for you");
                return null;
            }
            if(!ValidMove) {
                System.out.println("This is not a valid move. Please select another");
                posMove = moveInputs();
            }
        }

        //update board with this players move
        cgb.updateBoard(posMove, color);
        //TODO update board with this players flip
        for (int i = 0; i < vector.length; i++){
            flipHelper(posMove.x, posMove.y, pColor, i, true);
        }



        return posMove;
    }

    public Move moveInputs(){
        //inputs
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your move");
        int x = sc.nextInt();
        int y = sc.nextInt();
        while(x < 0|| x > 7) {
            System.out.println("Please enter a valid input 0-7");
            x = sc.nextInt();
        }
        while(y < 0|| y > 7) {
            System.out.println("Please enter a valid input 0-7");
            y = sc.nextInt();
        }

        Move m = new Move(x, y);
        return m;
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
            if ((x + vector[i].getX() >= 0) && (8 * (y + vector[i].getY()) >= 0)) {
                if ((x + vector[i].getX() <= 7) && (y + vector[i].getY() <= 7)){
                    //color of the piece in the dir of vector is pieceColor
                    pieceColor = cgb.getColor(x + vector[i].getX(), (y + vector[i].getY()));
                    //if there is a neighboring piece of a different color
                    if (pieceColor != playerColor && pieceColor != 2){

                        //if found continue in that dir until your piece is found
                        return flipHelper(x + vector[i].getX(), (y + vector[i].getY()), playerColor, i, false);

                    }
                }
            }
        }
        return false;
    }

    //known parameter is only used for checking on tiles where it is known that a flip is possible
    public boolean flipHelper(int x, int y, int playerColor, int direction, boolean known){

        int pieceColor;
        //check bounds
        if ((x + vector[direction].getX() >= 0) && (8 * (y + vector[direction].getY()) >= 0)) {
            if ((x + vector[direction].getX()) + (8 * (y + vector[direction].getY())) < 64){
                //if at least 1 opposing piece is between the possible placement and another of the same players pieces a flip is possible
                pieceColor = cgb.getColor(x + vector[direction].getX(), (y + vector[direction].getY()));
                //if another piece of player color is found exit with true
                if (pieceColor == playerColor){
                    return true;
                }

                if ((pieceColor != playerColor) && known && pieceColor != 2){
                    reverseFlip(x , y , playerColor, direction);
                }
                //if an empty square is found before another players tile return false
                if(pieceColor == 2){
                    return false;
                }
                //if another piece of opposing player color is found go again
                else {
                    flipHelper(x + vector[direction].getX(), (y + vector[direction].getY()), playerColor, direction, known);
                }
            }
        }
        //if board edge is reached
        return false;
    }

    //if a move is selected there is a flip
    //call flipHelper in all 8 dir until all same color pieces are found
    //if the same players piece is found is found go in opposite of direction and flip every piece on the way
    public void reverseFlip(int x, int y, int playerColor, int direction){
        int px;
        int py;
        Move toFliptemp;
        int pieceColor;

        pieceColor = cgb.getColor(x + vector[direction].getX(), (y + vector[direction].getY()));
        if (pieceColor != playerColor){
            c++;
            toFliptemp = new Move(x + vector[direction].getX(),(y + vector[direction].getY()));
            toFlip.add(toFliptemp);
            reverseFlip(x + vector[direction].getX(), (y + vector[direction].getY()), playerColor, direction);
        }
        if (pieceColor == playerColor){
            //reset c
            c = 0;
        }
        if (pieceColor == 2){
            //if it runs into an empty square none of the previous entries should be flipped, so remove those
            while (c > 0) {
                toFlip.remove(toFlip.size());
                c--;
            }
        }

    }

    //getters
    public ConsoleGameboard getBoard() {
        return cgb;
    }

    public int getpColor(){
        return this.pColor;
    }


}
