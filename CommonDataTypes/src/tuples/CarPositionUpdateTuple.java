package tuples;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import others.CarLocation;
import others.Direction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Sebastian on 05.04.2014.
 */
public class CarPositionUpdateTuple {

    private Long logicalTimeStamp;
    private String id;
    private CarLocation location;
    private Integer carId;

    public CarPositionUpdateTuple(CarLocation carLocation, int carId, long logicalTimeStamp) {
        this.carId = carId;
        this.location = carLocation;
        this.logicalTimeStamp = logicalTimeStamp;
    }

    public CarPositionUpdateTuple() {  }

    @SpaceId(autoGenerate = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLogicalTimeStamp() {
        return logicalTimeStamp;
    }

    public void setLogicalTimeStamp(Long logicalTimeStamp) {
        this.logicalTimeStamp = logicalTimeStamp;
    }

    public CarLocation getLocation() {
        return location;
    }

    public void setLocation(CarLocation location) {
        this.location = location;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }


}
