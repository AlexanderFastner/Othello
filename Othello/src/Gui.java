import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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

    private Parent makeBoard(){
        Pane root = new Pane();
        root.setPrefSize(width * tileSize, height * tileSize);
        root.getChildren().addAll(tileGrid, piecesG);

        //populate grid
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                //make new tiles, even tiles are white
                Tile tile = new Tile(((x + y) % 2 == 0), x, y);
                tileGrid.getChildren().add(tile);
            }
        }
        return root;
    }

    public void start(Stage window) throws Exception {

        //generate gui of board
        Scene board = new Scene(makeBoard());

        //title
        window.setTitle("Othello");
        //top

        //put makeboard in layout

        //bottom

        //scene
        window.setScene(board);
        //launch
        window.show();

    }




}
