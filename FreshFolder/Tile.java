import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import szte.mi.Move;


public class Tile extends Rectangle {

    private GamePiece piece;

    public Tile(boolean color, int x, int y){
        setWidth(Gui.tileSize);
        setHeight(Gui.tileSize);
        relocate(x * Gui.tileSize, y * Gui.tileSize);
        if(color) {
            setFill(Color.LIGHTGREEN);
        }
        else {
            setFill(Color.GREEN);
        }

    }

    public boolean hasPiece(){
        return piece != null;
    }

    public GamePiece getPiece(){
        if(this.hasPiece()) {
            return piece;
        }
        return null;
    }

    public void setPiece(GamePiece piece){
        this.piece = piece;
    }


}
