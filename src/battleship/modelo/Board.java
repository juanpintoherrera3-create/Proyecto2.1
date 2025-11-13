package battleship.modelo;

import java.io.Serializable;

public class Board implements Serializable {
    public static final int SIZE = 10;
    private Cell[][] cells = new Cell[SIZE][SIZE];

    public Board() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                cells[i][j] = new Cell(i, j);
    }

    public Cell getCell(int r, int c) { return cells[r][c]; }

    public boolean placeShip(Ship s, int r, int c, boolean horizontal) throws InvalidPlacementException {
        if (horizontal) {
            if (c + s.getLength() > SIZE) throw new InvalidPlacementException();
            for (int i = 0; i < s.getLength(); i++)
                if (cells[r][c + i].hasShip()) throw new InvalidPlacementException();
            for (int i = 0; i < s.getLength(); i++)
                cells[r][c + i].setShip(s);
        } else {
            if (r + s.getLength() > SIZE) throw new InvalidPlacementException();
            for (int i = 0; i < s.getLength(); i++)
                if (cells[r + i][c].hasShip()) throw new InvalidPlacementException();
            for (int i = 0; i < s.getLength(); i++)
                cells[r + i][c].setShip(s);
        }
        return true;
    }

    public boolean allSunk() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (cells[i][j].hasShip() && !cells[i][j].getShip().isSunk()) return false;
        return true;
    }
}
