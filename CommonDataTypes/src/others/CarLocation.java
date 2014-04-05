package others;

import java.io.Serializable;

/**
 * Created by Sebastian on 05.04.2014.
 */
public class CarLocation implements Serializable {
    private Integer x;
    private Integer y;

    public CarLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
