package battleship.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import battleship.controlador.Game;
import battleship.vista.GameView;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Game game = new Game();
        GameView view = new GameView(game);
        Scene scene = new Scene(view.root, 900, 500);
        stage.setTitle("Battleship");
        stage.setScene(scene);
        stage.show();
    }
}
