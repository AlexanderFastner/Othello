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
    ArrayList<Move> betterPossibleMoves = new ArrayList<>();
    ArrayList<Move> toFlip = new ArrayList<Move>();
    ArrayList<Move> edgePossibleMoves = new ArrayList<>();

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

    //edges not next to corners
    Move e02 = new Move(0, 2);
    Move e03 = new Move(0, 3);
    Move e04 = new Move(0, 4);
    Move e05 = new Move(0, 5);
    Move e20 = new Move(2, 0);
    Move e30 = new Move(3, 0);
    Move e40 = new Move(4, 0);
    Move e50 = new Move(5, 0);
    Move e72 = new Move(7, 2);
    Move e73 = new Move(7, 3);
    Move e74 = new Move(7, 4);
    Move e75 = new Move(7, 5);
    Move e27 = new Move(2, 7);
    Move e37 = new Move(3, 7);
    Move e47 = new Move(4, 7);
    Move e57 = new Move(5, 7);
    //next to corners
    Move bm01 = new Move(0, 1);
    Move bm10 = new Move(1, 0);
    Move bm11 = new Move(1, 1);
    Move bm60 = new Move(6, 0);
    Move bm61 = new Move(6, 1);
    Move bm71 = new Move(7, 1);
    Move bm06 = new Move(0, 6);
    Move bm16 = new Move(1, 6);
    Move bm17 = new Move(1, 7);
    Move bm66 = new Move(6, 6);
    Move bm67 = new Move(6, 7);
    Move bm76 = new Move(7, 6);
    //corners
    Move gm00 = new Move(0, 0);
    Move gm07 = new Move(0, 7);
    Move gm70 = new Move(7, 0);
    Move gm77 = new Move(7, 7);
    //weight arrays
    Move [] gweights = {gm00,gm07,gm70,gm77};
    Move [] bweights = {bm01,bm10,bm11,bm60,bm61,bm71,bm06,bm16,bm17,bm66,bm67,bm76};
    Move [] eweights = {e02,e03,e04,e05,e20,e30,e40,e50,e72,e73,e74,e75,e27,e37,e47,e57};

    public static void main(String[] args) {
        launch(args);
    }

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                x = (int) (e.getScreenX() - 560) / 100;
                y = (int) (e.getScreenY() - 170) / 100;
                System.out.println(x + " " + y);

                //reset pCOlor
                pColor = 0;
                playerColor = true;

                //generate possible moves
                //check list of possible moves
                Move temp;
                int curY = 0;
                for (int i = 0; i < 64; i++) {
                    temp = new Move(i % 8, curY);
                    if (i != 0 && temp.x % 8 == 0) {
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
                for (int i = 0; i < possibleMoves.size(); i++) {
                    System.out.print(possibleMoves.get(i).x + "," + (possibleMoves.get(i).y + " "));
                }

                //if no possible moves say so
                if (possibleMoves.size() == 0) {
                    System.out.println("There are no possible moves for you");
                }

                //First do player move then ai move back to back

                //check if the player selected move is in possible moves

                for (int i = 0; i < possibleMoves.size(); i++) {
                    if ((x == possibleMoves.get(i).x) && (y == possibleMoves.get(i).y)) {
                        // make the move
                        Move selMove = new Move(x, y);
                        //update gui cmdboard
                        guiGB.updateBoard(selMove, true);
                        //TODO update GUI
                        updateGui();
                        //TODO do flips

                        break;
                    }
                    if (board[x][y].hasPiece()) {
                        System.out.println("This tile is occupied");
                    }
                }

                //clear possible moves
                possibleMoves.clear();

                //change color
                pColor = 1;
                playerColor = false;

                //get AI possible moves
                //generate possible moves
                //check list of possible moves
                Move AItemp;
                int AIcurY = 0;
                for (int i = 0; i < 64; i++) {
                    AItemp = new Move(i % 8, AIcurY);
                    if (i != 0 && AItemp.x % 8 == 0) {
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
                System.out.print("Possible Moves for AI:");
                for (int i = 0; i < possibleMoves.size(); i++) {
                    System.out.print(possibleMoves.get(i).x + "," + (possibleMoves.get(i).y + " "));
                }

                //if no possible moves say so
                boolean AInoMoves = false;
                if (possibleMoves.size() == 0) {
                    System.out.println("There are no possible moves for player 2");
                    AInoMoves = true;
                }

                //make AI move
                Move selected = null;
                int selection = -1;
                boolean betterMove = false;
                while (selected == null) {
                    //prioritize corners
                    for (int i = 0; i < gweights.length; i++) {
                        for (int j = 0; j < possibleMoves.size(); j++) {
                            if ((gweights[i].x == possibleMoves.get(j).x) && (gweights[i].y == possibleMoves.get(j).y)) {
                                System.out.println("My corner bitch");
                                selected = possibleMoves.get(j);
                                betterMove = true;
                            }
                        }
                    }
                    if (!betterMove) {
                        //try to get on edges while not next to corner
                        for (int i = 0; i < eweights.length; i++) {
                            for (int j = 0; j < possibleMoves.size(); j++) {
                                if (((eweights[i].x == possibleMoves.get(j).x) && (eweights[i].y == possibleMoves.get(j).y))) {
                                    edgePossibleMoves.add(possibleMoves.get(j));
                                }
                            }
                        }
                        System.out.print("Possible Edge Moves: ");
                        for (int i = 0; i < edgePossibleMoves.size(); i++) {
                            System.out.print(edgePossibleMoves.get(i).x + "," + (edgePossibleMoves.get(i).y + " "));
                        }

                        //select from edge moves at random
                        if (edgePossibleMoves.size() > 0) {
                            selection = new Random().nextInt(edgePossibleMoves.size());
                            selected = edgePossibleMoves.get(selection);
                            betterMove = true;
                        }

                    }
                    if (!betterMove) {
                        //avoid spaces next to corners
                        //generate a new arraylist - the bad moves
                        for (int i = 0; i < possibleMoves.size(); i++) {
                            betterPossibleMoves.add(possibleMoves.get(i));
                        }

                        for (int i = 0; i < bweights.length; i++) {
                            for (int j = 0; j < possibleMoves.size(); j++) {
                                if (((bweights[i].x == possibleMoves.get(j).x) && (bweights[i].y == possibleMoves.get(j).y))) {
                                    betterPossibleMoves.remove(possibleMoves.get(j));
                                }
                            }
                        }
                        System.out.print("Better Possible Moves: ");
                        for (int i = 0; i < betterPossibleMoves.size(); i++) {
                            System.out.print(betterPossibleMoves.get(i).x + "," + (betterPossibleMoves.get(i).y + " "));
                        }
                        System.out.println();
                        //select from better moves at random
                        if (betterPossibleMoves.size() > 0) {
                            selection = new Random().nextInt(betterPossibleMoves.size());
                            selected = betterPossibleMoves.get(selection);
                            betterMove = true;
                        }
                    }
                    if (!betterMove) {
                        //else just select random
                        if (selected == null && possibleMoves.size() > 0) {
                            selection = new Random().nextInt(possibleMoves.size());
                            selected = possibleMoves.get(selection);
                        }
                        if (selected == null) {
                            break;
                        }
                    }
                }

                //clear betterPossibleMoves
                betterPossibleMoves.clear();
                if (AInoMoves) {
                    System.out.println("AI passes");

                }
                else {
                    System.out.println(selected.x + " " + selected.y);
                    //make move
                    Move AIMove = new Move(selected.x, selected.y);
                    //update gui
                    //TODO THIS DOESNT WORK
                    updateGui();

                    //make AI flips
                    guiGB.updateBoard(selected, playerColor);
                    //check player possible moves, if null then go again
                }
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

    public GamePiece makePiece(boolean color, int x, int y, Tile t){
        GamePiece p = new GamePiece(color, x, y);
        t.setPiece(p);
        piecesG.getChildren().remove(t);
        piecesG.getChildren().add(t);
        p.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        return p;
    }

    public void updateGui(){

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){

                //if gui doesnt show a piece but guiGB does, then update
                if((board[i][j].getPiece() == null) && (guiGB.getColor(i, j) != 2)){
                    boolean c = false;
                    if(guiGB.getColor(i, j) == 0){
                        c = true;
                    }
                    if(guiGB.getColor(i, j) == 1){
                        c = false;
                    }

                    GamePiece p = makePiece(c, i, j, board[i][j]);
                    board[i][j].setPiece(p);
                    piecesG.getChildren().add(p);
                }

                //if a flip happened, update
                if(board[i][j].getPiece() != null) {
                    if (board[i][j].getPiece().getPieceColor() != guiGB.getColor(i, j)) {
                        board[i][j].getPiece().setPieceColor(guiGB.getColor(i, j));
                    }
                }
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
