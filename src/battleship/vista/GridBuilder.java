package battleship.vista;

import battleship.modelo.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GridBuilder {
    public interface CellHandler { void handle(int r, int c); }

    public static GridPane buildGrid(Board board, boolean showShips, CellHandler handler) {
        GridPane grid = new GridPane();
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Rectangle rect = new Rectangle(30, 30);
                Cell cell = board.getCell(r, c);
                updateRect(rect, cell, showShips);
                int rr = r, cc = c;
                rect.setOnMouseClicked((EventHandler<MouseEvent>) (ev) -> {
                    if (ev.getButton().equals(javafx.scene.input.MouseButton.PRIMARY))
                        handler.handle(rr, cc);
                });
                grid.add(rect, c, r);
            }
        }
        return grid;
    }

    private static void updateRect(Rectangle rect, Cell cell, boolean showShips) {
        if (cell.isHit()) rect.setStyle("-fx-fill: gray; -fx-stroke: black;");
        else if (showShips && cell.hasShip()) rect.setStyle("-fx-fill: lightblue; -fx-stroke: black;");
        else rect.setStyle("-fx-fill: lightgray; -fx-stroke: black;");
    }
}
