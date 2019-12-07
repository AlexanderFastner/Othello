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
import java.util.Scanner;

public class Gui  extends Application{

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



        //top

        //center/gameboard

        //bottom





        //scene size
        window.setScene(new Scene(mainLayout, 1000, 1000));
        //launch
        window.show();




    }




}
