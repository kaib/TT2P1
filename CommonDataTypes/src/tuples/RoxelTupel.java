package tuples;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import java.util.UUID;

/**
 * Created by tobi on 31.03.14.
 */
public class RoxelTupel{
    private Integer positionX;
    private Integer PositionY;
    private Integer id;
    private Integer carId;

    public RoxelTupel() {
    }

    public RoxelTupel(Integer id, Integer positionX, Integer positionY, Integer carId) {
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
}
