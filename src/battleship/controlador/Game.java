package battleship.controlador;

import battleship.modelo.*;
import java.io.IOException;
import java.util.*;

public class Game {
    private Player player = new Player("Jugador");
    private Board opponentBoard = new Board();
    private List<MoveRecord> records = new ArrayList<>();
    private boolean myTurn = true;
    private NetworkServer server;
    private NetworkClient client;

    public Game() {
        try { initialPlacement(); } catch (Exception e) {}
    }

    private void initialPlacement() throws InvalidPlacementException {
        player.getBoard().placeShip(new Destroyer(), 0, 0, true);
        player.getBoard().placeShip(new Destroyer(), 1, 0, true);
        player.getBoard().placeShip(new Cruiser(), 2, 0, true);
        player.getBoard().placeShip(new BattleshipShip(), 3, 0, true);
        player.getBoard().placeShip(new Carrier(), 4, 0, true);
    }

    public Player getPlayer() { return player; }
    public Board getOpponentBoard() { return opponentBoard; }
    public boolean isMyTurn() { return myTurn; }

    public boolean attack(int r, int c) throws Exception {
        if (!myTurn) throw new Exception("No es tu turno");
        Move m = new Move(r, c);
        boolean hit = false;

        if (opponentBoard.getCell(r, c).hasShip()) {
            opponentBoard.getCell(r, c).hit();
            hit = true;
        } else opponentBoard.getCell(r, c).hit();

        records.add(new MoveRecord(player.getName(), r, c, hit));
        FileManager.saveGameLog(records, "game_" + System.currentTimeMillis() + ".bin");

        myTurn = false;
        if (server != null) server.send(m);
        if (client != null) client.send(m);
        return hit;
    }

    public void startServer() throws IOException, ClassNotFoundException {
        server = new NetworkServer();
        server.start(5555);
        new Thread(() -> {
            try {
                while (true) {
                    Object o = server.receive();
                    if (o instanceof Move) processRemoteMove((Move) o);
                }
            } catch (Exception e) {}
        }).start();
    }

    public void connectTo(String host) throws IOException, ClassNotFoundException {
        client = new NetworkClient();
        client.connect(host, 5555);
        new Thread(() -> {
            try {
                while (true) {
                    Object o = client.receive();
                    if (o instanceof Move) processRemoteMove((Move) o);
                }
            } catch (Exception e) {}
        }).start();
    }

    private void processRemoteMove(Move m) {
        try {
            int r = m.row, c = m.col;
            boolean hit = false;
            if (player.getBoard().getCell(r, c).hasShip()) {
                player.getBoard().getCell(r, c).hit();
                hit = true;
            } else player.getBoard().getCell(r, c).hit();

            records.add(new MoveRecord("Rival", r, c, hit));
            FileManager.saveGameLog(records, "game_" + System.currentTimeMillis() + ".bin");

            myTurn = true;
            if (server != null) server.send(new Result(hit));
            if (client != null) client.send(new Result(hit));
        } catch (Exception e) {}
    }
}
