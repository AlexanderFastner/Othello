import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;


public class Tile extends Rectangle {

    private int x;
    private int y;

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                System.out.println(e.getEventType() + " at " + e.getSceneX() + "/" + e.getSceneY());
                x = (int)(e.getSceneX() - 100)/100;
                y = (int)(e.getSceneY() - 135)/100;
                System.out.println(x + " " + y);

            }
            else {
                System.out.println(e.getEventType());
            }
        }

    };

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

        //event handler

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

    }

    public boolean hasPiece(){
        return piece != null;
    }

    public GamePiece getPiece(){
        return piece;
    }

    public void setPiece(GamePiece piece){
        this.piece = piece;
    }


}
