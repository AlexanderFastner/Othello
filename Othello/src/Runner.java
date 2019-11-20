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

        //each player has their own board


        //if valid, make move and pass arguements on in nextMove

    }

    //Is the game still running
       public static boolean checkStatus() {

        //TODO add win condition

            return true;
        }

    //play game method
        public static void playGame(Player p1, Player p2) {

        Move lastMove = null;
        boolean gameRunning = true;
        //while the games going
        while(gameRunning) {
            ((Human) p1).getBoard().printBoard();
            System.out.println("It is Player1's turn. Color is : " + ((Human) p1).pColor);
            lastMove = p1.nextMove(lastMove, 0, 0);
            //update both Players boards with the last move
            ((Human) p1).getBoard().updateBoard(lastMove, ((Human) p1).color);
            ((Human) p2).getBoard().updateBoard(lastMove, ((Human) p1).color);
            //update the flipped pieces
            for (int i = 0; i < ((Human) p1).toFlip.size(); i++) {
                ((Human) p1).getBoard().updateBoard(((Human) p1).toFlip.get(i), ((Human) p1).color);
                ((Human) p2).getBoard().updateBoard(((Human) p1).toFlip.get(i), ((Human) p1).color);
                System.out.println(((Human) p1).toFlip.get(i).x);
                System.out.println(((Human) p1).toFlip.get(i).y);
            }
            ((Human) p1).toFlip.clear();

            if (!checkStatus()){
                break;
            }

            ((Human) p2).getBoard().printBoard();
            System.out.println("It is Player2's turn. Color is : " + ((Human) p2).pColor);
            lastMove = p2.nextMove(lastMove, 0, 0);
            //update both Players boards with the last move
            ((Human) p1).getBoard().updateBoard(lastMove, ((Human) p2).color);
            ((Human) p2).getBoard().updateBoard(lastMove, ((Human) p2).color);
            //update the flipped pieces
            for (int i = 0; i < ((Human) p1).toFlip.size(); i++) {
                ((Human) p2).getBoard().updateBoard(((Human) p2).toFlip.get(i), ((Human) p2).color);
                ((Human) p1).getBoard().updateBoard(((Human) p2).toFlip.get(i), ((Human) p2).color);
            }
            ((Human) p2).toFlip.clear();



            gameRunning = checkStatus();
        }
        System.out.println("Game ended");
    }


}
