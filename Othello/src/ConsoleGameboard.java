import java.util.Arrays;

public class ConsoleGameboard {

    //boolean array
    //gameBoardP is for Player moves
    boolean [] gameBoardP;
    //gameBoardT is for what spaces are already taken
    boolean [] gameBoardT;
    String [] gameBoardC;

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

    //print Board
    public void printBoard() {
        System.out.println("Reference Board");
        for(int i = 0; i < 64; i++) {
            if(i % 8 ==0) {
                System.out.println();
                System.out.print(i + " ");
            }
            else {
                System.out.print(i + " ");
            }
        }
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
