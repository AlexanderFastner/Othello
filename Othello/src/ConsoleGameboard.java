import szte.mi.Move;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleGameboard {

    //gameBoardP is for Player moves
    boolean [] gameBoardP;
    //gameBoardT is for what spaces are already taken
    boolean [] gameBoardT;
    //BoardC is to look pretty
    String [] gameBoardC;

    //unicode for boardC
    String black = "\u25CF";
    String white = "\u25CB";
    String empty = "\u2610";

    //Constructor
    public ConsoleGameboard() {
        gameBoardP = new boolean[64];
        gameBoardT = new boolean[64];
        gameBoardC = new String[64];
        initCBP();
        initCBT();
        fillGameBoardC();
    }


    public int setup(){
        Scanner sc = new Scanner(System.in);
        int p1Color = 0;
        System.out.println("What color would you like to be?");
        System.out.println("Please enter 0 for Black and 1 for White");
        p1Color = sc.nextInt();
        return p1Color;
    }


    //get color for possible 0
    // flip method
    public int getColor(int x, int y){
        // player 1 is black
        if (gameBoardC[x + (8 * y)].equals(black)){
            return 0;
        }
        //player 2 is white
        if (gameBoardC[x + (8 * y)].equals(white)){
            return 1;
        }
        //empty tile
        else {
            return 2;
        }
    }

    //initialize player board
    //this keeps track of player moves
    //true is for player1 and false is for player2& not filled spaces
    //you can find player2 positions by comparing to gameBoardT
    //all fields in gameBoardT that are not player1, are by default player2
    public void initCBP() {
        //Player1 is Black fill initial starting positions
        gameBoardP[28] = true;
        gameBoardP[35] = true;
    }
    //initialize taken board
    //keeps track of ay filled space
    //True = filled
    public void initCBT() {
        gameBoardT[27] = true;
        gameBoardT[28] = true;
        gameBoardT[35] = true;
        gameBoardT[36] = true;
    }

    //initial setup of cmd line visual board
    public void fillGameBoardC(){
        for(int i = 0; i < 64; i++){
            gameBoardC[i] = empty;
        }
        gameBoardC[27] = black;
        gameBoardC[28] = white;
        gameBoardC[35] = white;
        gameBoardC[36] = black;
    }

    //check whos move it was
    //update all boards
    public void updateBoard(Move move){
        int x = move.x;
        int y = move.y;


    }


    //print Board
    //lots of prinouts
    public void printBoard() {
        System.out.println();
        System.out.println("gameBoardP");
        for (int i = 0; i < 64; i++) {
            if(i % 8 == 0) {
                System.out.println();
            }
            if (gameBoardP[i] == false) {
                System.out.print(" 0");
            } else {
                System.out.print(" 1");
            }

        }
        System.out.println();
        System.out.println("gameBoardT");
        for (int i = 0; i < 64; i++) {
            if(i % 8 == 0) {
                System.out.println();
            }
            if (gameBoardT[i] == false) {
                System.out.print(" 0");
            } else {
                System.out.print(" 1");
            }

        }
        System.out.println();
        System.out.println("gameBoardC");
        for (int i = 0; i < 64; i++) {
            if(i % 8 == 0) {
                System.out.println();
            }
            System.out.print(gameBoardC[i] + " ");
        }
        System.out.println();
    }



}
