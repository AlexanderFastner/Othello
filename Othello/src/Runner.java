import java.util.Scanner;
import java.util.Random;

public class Runner {

    public static void main(String [] args) {

        Scanner sc = new Scanner(System.in);

        //ask if PVP or PVAI
        System.out.println("Would you like to play PVP or against the AI");
        System.out.println("Respond 0 for PVP and 1 for AI");
        int gametype = sc.nextInt();

        while(gametype < 0 || gametype > 1){
            System.out.println("Please enter 0 for PVP and 1 for AI");
            gametype = sc.nextInt();
        }

        //create players based on selected Gametype
        //default
        Player p1 = new Human();
        Player p2 = new Human();
        //selection
//        if(gametype == 0) {
//            Player p1 = new Human();
//            Player p2 = new Human();
//        }
//        if(gametype == 1) {
//            Player p1 = new Human();
//            Player p2 = new AI();
//        }

        int Rounds = 6;
        //random
        Random rnd = new Random();
        //init Player Objs
        for (int i = 0; i < Rounds; ++i) {
            p1.init(0, 8, rnd);
            p2.init(1, 8, rnd);

        }

        //each player has their own board
        ((Human) p1).getBoard().printBoard();

        //play game method
        void playGame( Player oPlayer1, Player oPlayer2){
            Move oLastMove = null;
        while (bGameRunning){
            oLastMove = oPlayer1.nextMove( oLastMove, timeP1, time);
            if (!checkboard())
                break;
            oLastMove = oPlayer2.nextMove( oLastMove, timeP2, time);
            bGameRunning = checkboard();
            }
        }


        //check valid moves

        //if valid, make move and pass arguements on in nextMove
    }




}
