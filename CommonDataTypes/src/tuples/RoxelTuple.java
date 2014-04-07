package tuples;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Created by tobi on 31.03.14.
 */
public class RoxelTuple {
    private Integer positionX;
    private Integer PositionY;
    private Integer id;
    private Integer carId;

    public RoxelTuple() {
    }

    public RoxelTuple(Integer id, Integer positionX, Integer positionY, Integer carId) {
        this.positionX = positionX;
        this.PositionY = positionY;
        this.id = id;
        this.carId = carId;
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
                '}';
    }
}
