import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import javafx.application.Application;
import szte.mi.*;

public class Runner {

    public static void main(String [] args) {

        Scanner sc = new Scanner(System.in);
        //ask if PVP or PVAI
        System.out.println("Would you like to play PVP or against the AI");
        System.out.println("Respond 0 for PVP and 1 for AI");
        int gametype = sc.nextInt();
        int displaytype = -1;

        //idiot testing
        while(gametype != 0 && gametype != 1){
            System.out.println("Please enter 0 for PVP and 1 for AI");
            gametype = sc.nextInt();
        }

        //create players based on selected Gametype
        //default
        Player p1;
        Player p2;
        //selection
        if(gametype == 0) {
             p1 = new Human();
             p2 = new Human();
        }
        else {
             p1 = new Human();
             p2 = new AI();
        }

        int Rounds = 6;
        Random rnd = new Random();
        //default to console game
        for (int i = 0; i < Rounds; ++i) {
            System.out.println("Starting round " + (i+1));
            //init Player Objs
            p1.init(0, 8, rnd);
            p2.init(1, 8, rnd);
            if(gametype == 0) {
                playGame(p1, p2);
                playGame(p2, p1);
            }
            else {
                playGameAI(p1, p2);
                playGameAI(p2, p1);


            }
        }

    }

    //Is the game still running
       public static boolean checkStatus(Human p, int nullMoves) {
            //all occupied
           for(int i = 0; i < 64; i++){
               if(p.getBoard().isFull()){
                   return false;
               }
           }
            //both players last moves are null
           if(nullMoves == 2){
               System.out.println("There are no more possible moves for either player");
               return false;
           }
        return true;
       }

       public static boolean checkStatusAI(AI ai, int nullMoves){
           //all occupied
           for(int i = 0; i < 64; i++){
               if(ai.getBoard().isFull()){
                   return false;
               }
           }
           //both players last moves are null
           if(nullMoves == 2){
               System.out.println("There are no more possible moves for either player");
               return false;
           }
           return true;
       }

        //player game method
        public static void playGame(Player p1, Player p2) {

            //nullMoves is for win condition
            int nullMoves = 0;
            Move lastMove = null;
            boolean gameRunning = true;
            //while the games going
            while(gameRunning) {
                System.out.println("It is Player1's turn. Color is : " + ((Human) p1).pColor);
                lastMove = p1.nextMove(lastMove, 0, 0);

                if (lastMove == null) {
                    nullMoves++;
                }
                if (!checkStatus((((Human) p1)), nullMoves)){
                    break;
                }
                System.out.println("It is Player2's turn. Color is : " + ((Human) p2).pColor);
                lastMove = p2.nextMove(lastMove, 0, 0);

                if (lastMove == null) {
                    nullMoves++;
                }
                gameRunning = checkStatus(((Human) p2), nullMoves);
            }
            System.out.println("Game ended");
    }


    //AI game method
    public static void playGameAI(Player p1, Player p2) {

        //nullMoves is for win condition
        int nullMoves = 0;
        Move lastMove = null;
        boolean gameRunning = true;
        //while the games going
        while(gameRunning) {
            System.out.println("It is Player1's turn. Color is : " + ((Human) p1).pColor);
            lastMove = p1.nextMove(lastMove, 0, 0);

            if (lastMove == null) {
                nullMoves++;
            }
            if (!checkStatus((((Human) p1)), nullMoves)){
                break;
            }
            System.out.println("It is Player2's turn. Color is : " + ((AI) p2).pColor);
            lastMove = p2.nextMove(lastMove, 0, 0);

            if (lastMove == null) {
                nullMoves++;
            }
            gameRunning = checkStatusAI(((AI) p2), nullMoves);
        }
        System.out.println("Game ended");
    }

}
