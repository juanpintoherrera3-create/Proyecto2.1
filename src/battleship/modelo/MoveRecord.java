package battleship.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MoveRecord implements Serializable {
    private String player;
    private int row, col;
    private boolean hit;
    private LocalDateTime time;

    public MoveRecord(String player, int r, int c, boolean hit) {
        this.player = player;
        this.row = r;
        this.col = c;
        this.hit = hit;
        this.time = LocalDateTime.now();
    }

    public String toString() {
        return player + ": (" + row + "," + col + ") hit=" + hit + " at " + time;
    }
}
