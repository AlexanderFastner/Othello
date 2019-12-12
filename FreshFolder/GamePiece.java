import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

public class GamePiece extends StackPane {

    private int pieceColor;
    Circle c;

    public GamePiece(boolean color, int x, int y){

        //make a circle
        c = new Circle();
        c.setRadius(40);
        relocate(x * Gui.tileSize, y * Gui.tileSize);
        c.setTranslateX(10);
        c.setTranslateY(10);

        //color
        if(color){
            pieceColor = 0;
        }

        else {
            pieceColor = 1;
        }
        if(pieceColor == 0){
            c.setFill(Color.BLACK);
            c.setStrokeWidth(3);
            c.setStroke(Color.WHITE);
        }
        if(pieceColor == 1){
            c.setFill(Color.WHITE);
            c.setStrokeWidth(3);
            c.setStroke(Color.BLACK);
        }

        getChildren().add(c);
    }

    public int getPieceColor(){
        if(pieceColor == 0 || pieceColor == 1) {
            return pieceColor;
        }
        return -1;
    }
    public void setPieceColor(int pC){
        pieceColor = pC;

        if(pieceColor == 0){
            c.setFill(Color.BLACK);
            c.setStrokeWidth(3);
            c.setStroke(Color.WHITE);
        }
        if(pieceColor == 1){
            c.setFill(Color.WHITE);
            c.setStrokeWidth(3);
            c.setStroke(Color.BLACK);
        }

    }

}
