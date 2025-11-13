package battleship.vista;

import battleship.controlador.Game;
import battleship.modelo.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.MouseButton;

public class GameView {
    public BorderPane root = new BorderPane();
    private Game game;

    public GameView(Game game) {
        this.game = game;

        HBox boards = new HBox(10);
        boards.setAlignment(Pos.CENTER);

        VBox left = new VBox(10);
        VBox right = new VBox(10);

        left.getChildren().add(new Label("Mi tablero"));
        right.getChildren().add(new Label("Tablero rival"));

        GridPane myGrid = GridBuilder.buildGrid(game.getPlayer().getBoard(), true, (r, c) -> {});
        GridPane oppGrid = GridBuilder.buildGrid(game.getOpponentBoard(), false, (r, c) -> {
            if (game.isMyTurn()) {
                try {
                    boolean hit = game.attack(r, c);
                    Alert a = new Alert(Alert.AlertType.INFORMATION, hit ? "Impacto" : "Agua");
                    a.showAndWait();
                } catch (Exception e) {
                    Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    a.showAndWait();
                }
            }
        });

        left.getChildren().add(myGrid);
        right.getChildren().add(oppGrid);
        boards.getChildren().addAll(left, right);

        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        Button host = new Button("Host");
        Button connect = new Button("Connect");
        TextField ip = new TextField("127.0.0.1");

        host.setOnAction(e -> new Thread(() -> {
            try { game.startServer(); } catch (Exception ex) {}
        }).start());

        connect.setOnAction(e -> new Thread(() -> {
            try { game.connectTo(ip.getText()); } catch (Exception ex) {}
        }).start());

        controls.getChildren().addAll(host, connect, ip);

        root.setCenter(boards);
        root.setBottom(controls);
    }
}
