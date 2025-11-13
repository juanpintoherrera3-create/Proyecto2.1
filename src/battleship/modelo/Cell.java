package battleship.modelo;

import java.io.Serializable;

public class Cell implements Serializable {
    private int row, col;
    private boolean hit = false;
    private Ship ship;

    public Cell(int r, int c) { row = r; col = c; }

    public boolean hasShip() { return ship != null; }
    public void setShip(Ship s) { ship = s; }
    public Ship getShip() { return ship; }
    public boolean isHit() { return hit; }
    public void hit() { if (!hit) { hit = true; if (ship != null) ship.hit(); } }
}
