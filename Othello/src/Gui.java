import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import szte.mi.Move;
import szte.mi.Player;

import java.util.ArrayList;
import java.util.PrimitiveIterator;
import java.util.Random;

public class Gui  extends Application{

    private int x;
    private int y;
    public static final int width = 8;
    public static final int height = 8;
    public static final int tileSize = 100;
    private Group tileGrid = new Group();
    private Group piecesG = new Group();
    Tile [][] board = new Tile[width][height];
    private boolean playerColor;

    public static void main(String[] args) {
        launch(args);
    }

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                x = (int)(e.getScreenX() - 560)/100;
                y = (int)(e.getScreenY() - 170)/100;
                System.out.println(e.getScreenX() + " " + e.getScreenY());
                System.out.println(x + " " + y);

                //check if the move is in possible moves


                //make the move
                System.out.println(board[x][y].hasPiece());
                //change color

                Move temp = new Move(x, y);
                updateBoard(temp, playerColor);
                //make subsequent flips

            }
            else {
                System.out.println(e.getEventType());
            }
        }

    };



    public void updateBoard(Move move, boolean playerColor){
        int x = move.x;
        int y = move.y;
        makePiece(playerColor, x, y);
    }

    private Parent makeBoard(){
        Pane root = new Pane();
        root.setPrefSize(width * tileSize, height * tileSize);
        root.getChildren().addAll(tileGrid, piecesG);

        //make grid
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                //make new tiles, even tiles are white
                Tile tile = new Tile(((x + y) % 2 == 0), x, y);
                board [x][y] = tile;
                //event handler
                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
                tileGrid.getChildren().add(tile);

                GamePiece piece = null;
                //add starting pieces
                if(x == 3 && y == 3){
                    piece = makePiece(false, 3, 3);
                }
                if(x == 4 && y == 4){
                    piece = makePiece(false, 4, 4);
                }
                if(x == 3 && y == 4){
                    piece = makePiece(true, 3, 4);
                }
                if(x == 4 && y == 3){
                    piece = makePiece(true, 4, 3);
                }
                if(piece != null) {
                    tile.setPiece(piece);
                    piecesG.getChildren().add(piece);
                }

            }
        }
        return root;
    }


    public GamePiece makePiece(boolean color, int x, int y){
        GamePiece p = new GamePiece(color, x, y);
        p.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        return p;
    }

    public static boolean checkStatus(GuiPlayer p, int nullMoves) {
        return true;
    }

    //gui game method
    public void playGuiGame(Player p1, Player p2){


        //start the gui


        //nullMoves is for win condition
        int nullMoves = 0;
        Move lastMove = null;

        boolean gameRunning = true;
        //while the games going
        while(gameRunning) {
            playerColor = true;
            lastMove = p1.nextMove(lastMove, 0, 0);

            if (lastMove == null) {
                nullMoves++;
            }
            if (!checkStatus((((GuiPlayer) p1)), nullMoves)){
                break;
            }
            playerColor = false;
            lastMove = p2.nextMove(lastMove, 0, 0);

            if (lastMove == null) {
                nullMoves++;
            }
            gameRunning = checkStatus(((GuiPlayer) p2), nullMoves);
        }
        System.out.println("Game ended");
    }



    public void start(Stage window) throws Exception {

        //generate gui of board
        BorderPane p = new BorderPane();
        p.setPrefSize(1000, 1000);
        //score label
        //TODO add SCOREBOARD for multiple rounds
        Label score = new Label("Score is: ");
        score.setFont(new Font("Arial", 30));
        p.setTop(score);
        p.setAlignment(score, Pos.CENTER);
        //game
        Parent board = makeBoard();
        p.setCenter(board);
        p.setMargin(board, new Insets(100));
        //bottom
        Label PosMoves = new Label("Possible Moves: ");
        p.setBottom(PosMoves);

        Scene screen = new Scene(p);
        //title
        window.setTitle("Othello");
        //scene
        window.setScene(screen);

        //launch
        window.show();

        //gui game
        GuiPlayer p1 = new GuiPlayer();
        GuiPlayer p2 = new GuiPlayer();

        int Rounds = 6;
        Random rnd = new Random();
        //gui game 2 Players5
        for (int i = 0; i < Rounds; ++i) {
            //init Player Objs
            p1.init(0, 8, rnd);
            p2.init(1, 8, rnd);
            playGuiGame(p1, p2);
        }



    }




}
