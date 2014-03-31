package tuples;

import com.gigaspaces.annotation.pojo.SpaceId;

import java.util.UUID;

/**
 * Created by tobi on 31.03.14.
 */
public class Roxel implements interfaces.Roxel {
    private int positionX;
    private int PositionY;
    private int id;

    public Roxel() {
    }

    public Roxel(int id,int positionX, int positionY) {
        this.positionX = positionX;
        this.PositionY = positionY;
        this.id = id;
    }

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    @Override
    public int getPositionY() {
        return PositionY;
    }

    @Override
    public void setPositionY(int positionY) {
        PositionY = positionY;
    }

    @Override
    @SpaceId
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
