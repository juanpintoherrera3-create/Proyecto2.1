package battleship.modelo;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private Board board = new Board();

    public Player(String name) {
        this.name = name;
    }

    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }
}
