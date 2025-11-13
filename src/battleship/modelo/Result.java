package battleship.modelo;

import java.io.Serializable;

public class Result implements Serializable {
    public boolean hit;
    public Result(boolean hit) {
        this.hit = hit;
    }
}
