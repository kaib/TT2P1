package tuples;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import others.Direction;

import java.io.Serializable;

/**
 * Created by tobi on 31.03.14.
 */
@SpaceClass
public class RoxelTuple implements Serializable{
    private Integer positionX;
    private Integer PositionY;
    private Integer id;
    private Integer carId;
    private Direction direction;
    private boolean isCrossroad;

    public RoxelTuple() {}

    public RoxelTuple(Integer id, Integer positionX, Integer positionY, Integer carId, Direction direction, boolean isCrossroad) {
        this.positionX = positionX;
        this.PositionY = positionY;
        this.id = id;
        this.carId = carId;
        this.direction = direction;
        this.isCrossroad = isCrossroad;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return PositionY;
    }

    public void setPositionY(Integer positionY) {
        PositionY = positionY;
    }

    public boolean isCrossroad() {
        return isCrossroad;
    }

    public void setCrossroad(boolean isCrossroad) {
        this.isCrossroad = isCrossroad;
    }

    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RoxelTuple{" +
                "positionX=" + positionX +
                ", PositionY=" + PositionY +
                ", id=" + id +
                ", carId=" + carId +
                ", Direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoxelTuple that = (RoxelTuple) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
