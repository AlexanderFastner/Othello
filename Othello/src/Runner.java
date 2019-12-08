import java.util.Scanner;
import java.util.Random;
import szte.mi.*;

public class Runner {
    public static void main(String [] args) {

        Scanner sc = new Scanner(System.in);
        //ask if PVP or PVAI
        System.out.println("Would you like to play PVP or against the AI");
        System.out.println("Respond 0 for PVP and 1 for AI");
        int gametype = sc.nextInt();

        //idiot testing
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
            playGame(p1, p2);
            playGame(p2, p1);
        }

    }

    //Is the game still running
       public static boolean checkStatus() {

        int counter = 0;
        //all occupied
           for(int i = 0; i < 64; i++){
               if()
               counter++;
           }

        //both players last moves are null
        return true;
       }

    //play game method
        public static void playGame(Player p1, Player p2) {

        Move lastMove = null;
        boolean gameRunning = true;
        //while the games going
        while(gameRunning) {
            System.out.println("It is Player1's turn. Color is : " + ((Human) p1).pColor);
            lastMove = p1.nextMove(lastMove, 0, 0);

            if (!checkStatus()){
                break;
            }
            System.out.println("It is Player2's turn. Color is : " + ((Human) p2).pColor);
            lastMove = p2.nextMove(lastMove, 0, 0);

            gameRunning = checkStatus();
        }
        System.out.println("Game ended");
    }


}
