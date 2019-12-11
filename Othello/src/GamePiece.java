import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

public class GamePiece extends StackPane {

    public GamePiece(boolean color, int x, int y){

        //make a circle
        Circle c = new Circle();
        c.setRadius(40);
        relocate(x * Gui.tileSize, y * Gui.tileSize);
        c.setTranslateX(10);
        c.setTranslateY(10);

        //color
        if(color){
            c.setFill(Color.BLACK);
            c.setStrokeWidth(3);
            c.setStroke(Color.WHITE);
        }
        else {
            c.setFill(Color.WHITE);
            c.setStrokeWidth(3);
            c.setStroke(Color.BLACK);
        }
        getChildren().add(c);
    }
}
