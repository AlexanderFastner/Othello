import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{



    public Tile(boolean color, int x, int y){
        setWidth(x);
        setHeight(y);
        Rectangle border = new Rectangle(100, 100);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(3);
        if(color) {
            border.setFill(null);
        }
        else {
            border.setFill(Color.GREEN);
        }

    }


}
