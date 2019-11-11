import java.util.*;

import szte.mi.*;
import java.util.Scanner;


public class Human implements Player {

    ConsoleGameboard cgb;

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

    int [] possibleMoves;

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

    public boolean checkValidMove(Move move) {

        //check if occupied space
        int x = move.x;
        int y = move.y;
        if(cgb.gameBoardT[x+(8*y)] == true){
            System.out.println("This space is occupied. Please select another square");
            checkValidMove(move);
        }
        //check if theres a possible flip
        if (!possibleFlip(x, y)){
            System.out.println("Not a valid move");
            checkValidMove(move);
        }
        return true;
    }

    public boolean possibleFlip(int x, int y){

        //for each dir in vector
        for(int i = 0; i < vector.length; i++){
            //check for opposing piece

            if (cgb.gameBoardC[x + 8*y] ==(vector[i].getX() + x)


        }


        //if found continue until your piece is found

        //else stop
        return false;
    }



    public ConsoleGameboard getBoard() {
        return cgb;
    }

}
