import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

import java.sql.SQLSyntaxErrorException;
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
    ConsoleGameboard guiGB = new ConsoleGameboard(0);
    private boolean playerColor;
    GuiPlayer p1;
    AI p2;
    ArrayList<Move> possibleMoves = new ArrayList<Move>();
    ArrayList<Move> toFlip = new ArrayList<Move>();
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
    Pair [] vector = {TOPLEFT, TOPCENTER, TOPRIGHT, CENTERLEFT, CENTERRIGHT, BOTTOMLEFT, BOTTOMCENTER, BOTTOMRIGHT};

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

                //reset pCOlor
                pColor = 0;

                //generate possible moves
                //check list of possible moves
                Move temp;
                int curY =0;
                for(int i = 0; i < 64; i++){
                    temp = new Move(i%8, curY);
                    if(i != 0 && temp.x % 8 == 0){
                        curY++;
                        temp = new Move(0, curY);
                    }

                    if (guiGB.gameBoardT[i] == false) {
                        if (checkValidMove(temp, pColor)) {
                            possibleMoves.add(temp);
                        }
                    }
                }

                //debugging output
                System.out.print("Possible Moves:");
                for(int i = 0; i < possibleMoves.size(); i++){
                    System.out.print(possibleMoves.get(i).x + "," + (possibleMoves.get(i).y + " "));
                }

                //if no possible moves say so
                if(possibleMoves.size() == 0){
                    System.out.println("There are no possible moves for you");
                }

                //First do player move then ai move back to back

                //check if the player selected move is in possible moves
                Move t = new Move(x, y);
                for(int i = 0; i < possibleMoves.size(); i ++){
                    if((t.x == possibleMoves.get(i).x)&&(t.y == possibleMoves.get(i).y)){
                        // make the move
                        Move selMove = new Move(x, y);
                        //update Gui Tile board
                        updateBoard(selMove, playerColor, board[x][y]);
                        //update gui cmdboard
                        guiGB.updateBoard(selMove, true);
                        //update gui
                        updateGui();
                        //TODO do flips

                        break;
                    }
                    else {
                        System.out.println("Not an available tile");
                    }
                }
                //clear possible moves
                possibleMoves.clear();

                //change color
                pColor = 1;
                //get AI possible moves
                //generate possible moves
                //check list of possible moves
                Move AItemp;
                int AIcurY =0;
                for(int i = 0; i < 64; i++){
                    AItemp = new Move(i%8, AIcurY);
                    if(i != 0 && AItemp.x % 8 == 0){
                        AIcurY++;
                        AItemp = new Move(0, AIcurY);
                    }

                    if (guiGB.gameBoardT[i] == false) {
                        if (checkValidMove(AItemp, pColor)) {
                            possibleMoves.add(AItemp);
                        }
                    }
                }

                //debugging output
                System.out.println();
                System.out.print("Possible Moves:");
                for(int i = 0; i < possibleMoves.size(); i++){
                    System.out.print(possibleMoves.get(i).x + "," + (possibleMoves.get(i).y + " "));
                }

                //if no possible moves say so
                if(possibleMoves.size() == 0){
                    System.out.println("There are no possible moves for player 2");
                }

                //make AI move

                //make AI flips

                //check player possible moves, if null then go again
            }
            else {
                System.out.println(e.getEventType());
            }
        }
    };



    public boolean checkValidMove(Move move, int pColor) {
        int x = move.x;
        int y = move.y;
        //check if there is a possible flip
        if (possibleFlip(x, y, pColor)){
            return true;
        }
        return false;
    }

    //checks if you can get a flip on this xy
    public boolean possibleFlip(int x, int y, int playerColor){
        int pieceColor = 2;

        //for each dir in vector
        for(int i = 0; i < vector.length; i++){
            //check if would be in bounds
            if ((x + vector[i].getX() >= 0) && (y + vector[i].getY() >= 0)) {
                if ((x + vector[i].getX() <= 7) && (y + vector[i].getY() <= 7)){
                    //color of the piece in the dir of vector is pieceColor
                    pieceColor = guiGB.getColor(x + vector[i].getX(), (y + vector[i].getY()));
                    //if there is a neighboring piece of a different color
                    if (pieceColor != playerColor && pieceColor != 2){
                        //if found continue in that dir until your piece is found
                        if(flipHelper(x + vector[i].getX(), (y + vector[i].getY()), playerColor, i)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean flipHelper(int x, int y, int playerColor, int direction){

        int pieceColor;
        //check bounds
        if ((x + vector[direction].getX() >= 0) && (y + vector[direction].getY() >= 0)) {
            if ((x + vector[direction].getX()) + (8 * (y + vector[direction].getY())) < 64){
                //if at least 1 opposing piece is between the possible placement and another of the same players pieces a flip is possible
                pieceColor = guiGB.getColor(x + vector[direction].getX(), (y + vector[direction].getY()));
                //if another piece of player color is found exit with true
                if (pieceColor == playerColor){
                    return true;
                }
                //if an empty square is found before another players tile return false
                if(pieceColor == 2){
                    return false;
                }
                //if another piece of opposing player color is found go again
                else {
                    flipHelper(x + vector[direction].getX(), (y + vector[direction].getY()), playerColor, direction);
                }
            }
        }
        //if board edge is reached
        return false;
    }

    public void reverseFlip(int x, int y, int playerColor, int direction){
        Move toFliptemp;
        int pieceColor;

        if ((x + vector[direction].getX() >= 0) && (y + vector[direction].getY() >= 0)) {
            if ((x + vector[direction].getX()) + (8 * (y + vector[direction].getY())) < 64) {
                //now keep checking in the same dir
                pieceColor = guiGB.getColor(x + vector[direction].getX(), (y + vector[direction].getY()));
                //if another enemy piece is found do again
                if ((pieceColor != playerColor) && (pieceColor != 2)) {
                    toFliptemp = new Move(x + vector[direction].getX(), (y + vector[direction].getY()));
                    toFlip.add(toFliptemp);
                    reverseFlip(x + vector[direction].getX(), (y + vector[direction].getY()), playerColor, direction);
                }
                //This method is only called when at least 1 enemy piece was detected in this dir first
                //so if a friendly piece is detected, end and carry out the flip
                if ((pieceColor == playerColor) && (toFlip.size() > 0)) {
                    //for every element in toFlip
                    for (int i = 0; i < toFlip.size(); i++) {
                        guiGB.setColor(toFlip.get(i).x, toFlip.get(i).y, playerColor);
                    }
                    toFlip.clear();
                }
                if (pieceColor == 2) {
                    //if it runs into an empty square none of the previous entries should be flipped, so clear
                    toFlip.clear();
                }
            }
        }
    }

    public void updateBoard(Move move, boolean playerColor, Tile t){
        int x = move.x;
        int y = move.y;
        makePiece(playerColor, x, y, t);
    }

    public GamePiece makePiece(boolean color, int x, int y, Tile t){
        GamePiece p = new GamePiece(color, x, y);
        t.setPiece(p);
        piecesG.getChildren().remove(t);
        piecesG.getChildren().add(t);
        p.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        return p;
    }

    public void updateGui(){
        for(int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j++){
                tileGrid.getChildren().clear();
            }
        }
        //make grid new
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                //make new tiles, even tiles are white
                Tile tile = board[x][y];
                //event handler
                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
                tileGrid.getChildren().add(tile);
            }
        }
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
                    piece = makePiece(false, 3, 3, tile);
                }
                if(x == 4 && y == 4){
                    piece = makePiece(false, 4, 4, tile);
                }
                if(x == 3 && y == 4){
                    piece = makePiece(true, 3, 4, tile);
                }
                if(x == 4 && y == 3){
                    piece = makePiece(true, 4, 3, tile);
                }
                if(piece != null) {
                    tile.setPiece(piece);
                    piecesG.getChildren().add(piece);
                }
            }
        }
        return root;
    }

    public static boolean checkStatus(GuiPlayer p, int nullMoves) {
        return true;
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
        //TODO THIS ISNT WORKING
        String s = "";
        for(int i = 0; i < possibleMoves.size(); i++){
             s += (possibleMoves.get(i).x + "," + (possibleMoves.get(i).y + " "));
        }

        Label PosMoves = new Label("Possible Moves: " + s);

        PosMoves.setFont(new Font("Arial", 30));
        p.setBottom(PosMoves);

        Scene screen = new Scene(p);
        window.setResizable(false);
        //title
        window.setTitle("Othello");
        //scene
        window.setScene(screen);

        //launch
        window.show();

        //gui game
         p1 = new GuiPlayer();
         p2 = new AI();

        int Rounds = 6;
        Random rnd = new Random();
        //gui game 2 Players5
        for (int i = 0; i < Rounds; ++i) {
            //init Player Objs
            p1.init(0, 8, rnd);
            p2.init(1, 8, rnd);
        }
    }
}
