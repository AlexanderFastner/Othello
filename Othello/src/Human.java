import java.util.*;

import szte.mi.*;
import java.util.Scanner;


public class Human implements Player {

    ConsoleGameboard cgb;
    //color of this Human
    int pColor;

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
        int curY = 0;
        for(int i = 0; i < 64; i++){
            temp = new Move(i, curY);
            if(i != 0 && temp.x % 8 == 0){
                temp = new Move(0, curY+1);
            }

            if(cgb.gameBoardT[i] == false){
                if (checkValidMove(temp)){
                    possibleMoves.add(temp);
                }
            }
        }
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
        //check if occupied space
        int x = move.x;
        int y = move.y;
        if(cgb.gameBoardT[x+(8*y)] == true){
            System.out.println("This space is occupied. Please select another square");
            checkValidMove(move);
        }
        //check if theres a possible flip
        if (!possibleFlip(x, y, pColor, 0, 0)){
            System.out.println("Not a valid move");
            checkValidMove(move);
        }
        return true;
    }

    //checks if you can get a flip on this xy
    public boolean possibleFlip(int x, int y, int playerColor, int start, int counter){

        //for each dir in vector
        for(int i = 0; i < vector.length; i++){

            //check for opposing piece
            int pieceColor = 0;

            //check if would be in bounds

            if ((x + vector[i].getX() >= 0) && (8 * (y + vector[i].getY()) >= 0)) {
                pieceColor = cgb.getColor(x + vector[i].getX(), (8 * (y + vector[i].getY())));
            }

            //if there is a neighboring piece of a different color
            //recursivly call possible flip in the same direction and add 1 to counter
            if (pieceColor != playerColor){
                possibleFlip(x + vector[i].getX(), (8 *(y + vector[i].getY())), playerColor, i, counter+1);
            }
            //if at least 1 opposing piece is between the possible placement and another of the same players pieces a flip is possible
            if (counter > 0 && pieceColor == playerColor){
                return true;
            }
            //if an empty square is found before another players tile return false
            if(counter > 0 && pieceColor == 2){
                return false;
            }

        }


        //if found continue in that dir until your piece is found
        //if not found break

        //if no possible flips are found
        return false;
    }

    //getter
    public ConsoleGameboard getBoard() {
        return cgb;
    }

}
