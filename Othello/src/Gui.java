import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;

public class Gui  extends Application{

    public static final int width = 8;
    public static final int height = 8;



    //constructor
    public Gui() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage window) {

        //generate gui of board
        BorderPane mainLayout = new BorderPane();
        GridPane layout = new GridPane();

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                //make new tiles, even tiles are white
                Tile tile = new Tile((x + y) % 2 == 0, x, y);

            }
        }



        //title
        window.setTitle("Othello");
        //top

        //center/gameboard

        //bottom




        //scene size
        window.setScene(new Scene(mainLayout, 1000, 1000));
        //launch
        window.show();




    }




}
