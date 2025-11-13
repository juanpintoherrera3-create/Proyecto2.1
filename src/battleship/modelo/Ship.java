package battleship.modelo;

import java.io.Serializable;

public abstract class Ship implements Serializable {
    private int length;
    private int hits = 0;

    protected Ship(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void hit() {
        hits++;
    }

    public boolean isSunk() {
        return hits >= length;
    }
}
