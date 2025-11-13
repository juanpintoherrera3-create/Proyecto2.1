package battleship.modelo;

import java.io.Serializable;

public class Move implements Serializable {
    public int row, col;
    public Move(int r, int c) {
        row = r;
        col = c;
    }
}
