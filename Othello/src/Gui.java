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

public class Gui  extends Application{

    public static final int width = 8;
    public static final int height = 8;
    public static final int tileSize = 100;
    private Group tileGrid = new Group();
    private Group piecesG = new Group();

    public static void main(String[] args) {
        launch(args);
    }

    private int x;
    private int y;

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                x = (int)(e.getSceneX() - 100)/100;
                y = (int)(e.getSceneY() - 135)/100;
                System.out.println(x + " " + y);

                //check if its an occupied tile

                //check if the move is in possible moves

                //make the move and subsequent flips

            }
            else {
                System.out.println(e.getEventType());
            }
        }

    };

    private Parent makeBoard(){
        Pane root = new Pane();
        root.setPrefSize(width * tileSize, height * tileSize);
        root.getChildren().addAll(tileGrid, piecesG);

        //make grid
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                //make new tiles, even tiles are white
                Tile tile = new Tile(((x + y) % 2 == 0), x, y);
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

    private GamePiece makePiece(boolean color, int x, int y){
        GamePiece p = new GamePiece(color, x, y);
        return p;
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

        Scene screen = new Scene(p);
        //title
        window.setTitle("Othello");
        //scene
        window.setScene(screen);
        //launch
        window.show();
    }




}
