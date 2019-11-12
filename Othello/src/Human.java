import java.util.*;

import szte.mi.*;
import java.util.Scanner;


public class Human implements Player {

    ConsoleGameboard cgb;
    //color of this Human
    int pColor;
    //helper for next Move
    int curY = 0;

    //create dir vectors
    Pair TOPLEFT = new Pair(-1, -1);
    Pair TOPCENTER = new Pair(0, -1);
    Pair TOPRIGHT = new Pair(1, -1);
    Pair CENTERLEFT = new Pair(-1, 0);
    Pair CENTERRIGHT = new Pair(1, 0);
    Pair BOTTOMLEFT = new Pair(-1, 1);
    Pair BOTTOMCENTER = new Pair(0, 1);
    Pair BOTTOMRIGHT = new Pair(1, 1);
    Pair [] vector = {TOPLEFT, TOPCENTER, TOPRIGHT, CENTERLEFT, CENTERRIGHT, BOTTOMCENTER, BOTTOMLEFT, BOTTOMRIGHT};

    ArrayList<Move> possibleMoves = new ArrayList<Move>();

    //each player has their own board
    //communicate only through moves
    public Human() {
        cgb = new ConsoleGameboard();
        pColor = cgb.setup();

    }

    @Override
    public void init(int order, long t, Random rnd) {

    }


    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {

        //making array of valid moves for the current game State
        Move temp = new Move(0, 0);

        for(int i = 0; i < 64; i++){
            temp = new Move(i%8, curY);
            if(i != 0 && temp.x % 8 == 0){
                curY++;
                temp = new Move(0, curY);
            }

            if(cgb.gameBoardT[i] == false){
                if (checkValidMove(temp)){
                    possibleMoves.add(temp);
                }
            }
        }
        System.out.println(possibleMoves);

        Move posMove = moveInputs();

        while(!possibleMoves.contains(posMove)){
            System.out.println("This is not a valid move. Please select another");
            posMove = moveInputs();
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
        //check if m would be in an array of possible moves else error
        Move m = new Move(x, y);
        return m;
    }

    //check if a move is Valid
    public boolean checkValidMove(Move move) {
        int x = move.x;
        int y = move.y;

        //check if occupied space
        if(cgb.gameBoardT[x+(8*y)] == true){
            System.out.println("This space is occupied. Please select another square");
            return false;
        }
        //check if theres a possible flip
        if (!possibleFlip(x, y, pColor)){
            return false;

        }
        return true;
    }

    //checks if you can get a flip on this xy
    public boolean possibleFlip(int x, int y, int playerColor){

        int pieceColor = 2;

        //for each dir in vector
        for(int i = 0; i < vector.length; i++){

            //check for opposing piece

            //check if would be in bounds
            if ((x + vector[i].getX() >= 0) && (8 * (y + vector[i].getY()) >= 0)) {
                if ((x + vector[i].getX()) + (8 * (y + vector[i].getY())) < 64){
                    pieceColor = cgb.getColor(x + vector[i].getX(), (y + vector[i].getY()));
                }
            }
            //if there is a neighboring piece of a different color
            if (pieceColor != playerColor && pieceColor != 2){
                //if found continue in that dir until your piece is found
                return flipHelper(x + vector[i].getX(), (y + vector[i].getY()), playerColor, i);
            }
            return false;
        }
        return false;
    }

    public boolean flipHelper(int x, int y, int playerColor, int direction){

        int pieceColor = 2;
        //check bounds
        if ((x + vector[direction].getX() >= 0) && (8 * (y + vector[direction].getY()) >= 0)) {
            if ((x + vector[direction].getX()) + (8 * (y + vector[direction].getY())) < 64){
                //if at least 1 opposing piece is between the possible placement and another of the same players pieces a flip is possible
                pieceColor = cgb.getColor(x + vector[direction].getX(), (y + vector[direction].getY()));
                if (pieceColor == playerColor){
                    return true;
                }
                //if an empty square is found before another players tile return false
                if(pieceColor == 2){
                    return false;
                }
                //if another piece of player color is found
                else {
                    flipHelper(x + vector[direction].getX(), (y + vector[direction].getY()), playerColor, direction);
                }
            }
        }
        //if board edge is reached
        return false;
    }


    //getter
    public ConsoleGameboard getBoard() {
        return cgb;
    }

}
