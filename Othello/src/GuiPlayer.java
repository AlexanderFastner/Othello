import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import szte.mi.Move;
import szte.mi.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GuiPlayer implements Player {

    ConsoleGameboard board;
    ArrayList<Move> possibleMoves = new ArrayList<Move>();
    int pColor;
    ArrayList<Move> toFlip = new ArrayList<Move>();
    private boolean playerColor;

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


    public GuiPlayer(){
        board = new ConsoleGameboard(0);
    }

    @Override
    public void init(int order, long t, Random rnd) {
        if (order ==0){
            playerColor = true;
        }
        else {
            playerColor = false;
        }
    }

    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {

        //check list of possible moves
        Move temp;
        int curY =0;
        for(int i = 0; i < 64; i++){
            temp = new Move(i%8, curY);
            if(i != 0 && temp.x % 8 == 0){
                curY++;
                temp = new Move(0, curY);
            }

            if (board.gameBoardT[i] == false) {
                if (checkValidMove(temp, pColor)) {
                    possibleMoves.add(temp);
                }
            }
        }
        System.out.print("Possible Moves:");
        for(int i = 0; i < possibleMoves.size(); i++){
            System.out.print(possibleMoves.get(i).x + "," + (possibleMoves.get(i).y + " "));
        }
        System.out.println();
        //detect a click for a move
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();
        //TODO get input

        Move m = new Move(x, y);

        //TODO find a way to update Gui.board
        return m;
    }


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
                    pieceColor = board.getColor(x + vector[i].getX(), (y + vector[i].getY()));
                    //if there is a neighboring piece of a different color
                    if (pieceColor != playerColor && pieceColor != 2){

                        //if found continue in that dir until your piece is found
                        if(flipHelper(x + vector[i].getX(), (y + vector[i].getY()), playerColor, i)){
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
            if ((x + vector[direction].getX()) + (8 * (y + vector[direction].getY())) < 64){
                //if at least 1 opposing piece is between the possible placement and another of the same players pieces a flip is possible
                pieceColor = board.getColor(x + vector[direction].getX(), (y + vector[direction].getY()));
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
                    flipHelper(x + vector[direction].getX(), (y + vector[direction].getY()), playerColor, direction);
                }
            }
        }
        //if board edge is reached
        return false;
    }

    public void reverseFlip(int x, int y, int playerColor, int direction){
        Move toFliptemp;
        int pieceColor;

        if ((x + vector[direction].getX() >= 0) && (y + vector[direction].getY() >= 0)) {
            if ((x + vector[direction].getX()) + (8 * (y + vector[direction].getY())) < 64) {
                //now keep checking in the same dir
                pieceColor = board.getColor(x + vector[direction].getX(), (y + vector[direction].getY()));
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
                        board.setColor(toFlip.get(i).x, toFlip.get(i).y, playerColor);
                    }
                    toFlip.clear();
                }
                if (pieceColor == 2) {
                    //if it runs into an empty square none of the previous entries should be flipped, so clear
                    toFlip.clear();
                }
            }
        }
    }

    public boolean getPlayerColor(){
        return playerColor;
    }





}
