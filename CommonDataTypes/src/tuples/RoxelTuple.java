package tuples;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import interfaces.CarTuple;
import others.Direction;

import java.io.Serializable;

/**
 * Created by tobi on 31.03.14.
 */
@SpaceClass
public class RoxelTuple implements Serializable {
    private Integer positionX;
    private Integer PositionY;
    private Integer id;
    private CarTuple car;
    private Direction direction;
    private Boolean crossroad;

    public RoxelTuple() {
    }

    public RoxelTuple(Integer id, Integer positionX, Integer positionY, CarTuple car, Direction direction, Boolean crossroad) {
        this.positionX = positionX;
        this.PositionY = positionY;
        this.id = id;
        this.car = car;
        this.direction = direction;
        this.crossroad = crossroad;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public CarTuple getCar() {
        return car;
    }

    public void setCar(CarTuple car) {
        this.car = car;
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

    public Boolean getCrossroad() {
        return crossroad;
    }

    public void setCrossroad(Boolean crossroad) {
        this.crossroad = crossroad;
    }

    @SpaceRouting
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
                ", car=" + car +
                ", direction=" + direction +
                ", crossroad=" + crossroad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoxelTuple)) return false;

        RoxelTuple that = (RoxelTuple) o;

        if (PositionY != null ? !PositionY.equals(that.PositionY) : that.PositionY != null) return false;
        if (car != null ? !car.equals(that.car) : that.car != null) return false;
        if (crossroad != null ? !crossroad.equals(that.crossroad) : that.crossroad != null) return false;
        if (direction != that.direction) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (positionX != null ? !positionX.equals(that.positionX) : that.positionX != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = positionX != null ? positionX.hashCode() : 0;
        result = 31 * result + (PositionY != null ? PositionY.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (crossroad != null ? crossroad.hashCode() : 0);
        return result;
    }
}
