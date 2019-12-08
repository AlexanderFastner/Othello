import szte.mi.Move;
import szte.mi.Player;
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
    String white = "\u25CF";
    String black = "\u25CB";
    String empty = "\u2610";

    int pColor;

    //Constructor
    public ConsoleGameboard(int pC) {
        gameBoardP = new boolean[64];
        gameBoardT = new boolean[64];
        gameBoardC = new String[64];
        initCBP();
        initCBT();
        initCBC();
        pColor = pC;
    }

    //get color for possible flip method
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

    public void setColor(int x, int y, int color){

        if(color == 0){
            gameBoardC[x + (8 * y)] = black;
        }
        else {
            gameBoardC[x + (8 * y)] = white;
        }

    }

    //initialize player board
    //this keeps track of player moves
    //true is for player1 and false is for player2& not filled spaces
    //you can find player2 positions by comparing to gameBoardT
    //all fields in gameBoardT that are not player1, are by default player2
    public void initCBP() {
        //Black fills initial starting positions
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
    public void initCBC(){
        for(int i = 0; i < 64; i++){
            gameBoardC[i] = empty;
        }
        gameBoardC[27] = white;
        gameBoardC[28] = black;
        gameBoardC[35] = black;
        gameBoardC[36] = white;
    }

    //if a move is not null
    //check whose move it was
    //update all boards
    //find pieces to flip
    public void updateBoard(Move move, boolean playerC){
        if(move == null){
            return;
        }

        int x = move.x;
        int y = move.y;

        if (playerC) {
            gameBoardP[x + (8*y)] = true;
            gameBoardC[x + (8*y)] = black;
        }
        else {
            gameBoardC[x + (8*y)] = white;
        }
        gameBoardT[x + (8*y)] = true;

    }

    //checks if board is full
    public boolean isFull(){
        int counter = 0;
        for(int i = 0; i < 64; i++) {
            if (gameBoardT[i]){
                counter++;
            }
        }
        if(counter == 64){
            return true;
        }
        else {
            return false;
        }
    }

    //print Board
    //lots of prinouts
    public void printBoard() {
        System.out.print("gameBoardC");
        for (int i = 0; i < 64; i++) {
            if(i % 8 == 0) {
                System.out.println();
            }
            System.out.print(gameBoardC[i] + " ");
        }
        System.out.println();
    }



}
