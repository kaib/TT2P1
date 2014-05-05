package tuples;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import others.MapLocation;
import others.Direction;

/**
 * Created by Sebastian on 05.04.2014.
 */
@SpaceClass
public class CarPositionUpdateTuple {

    private Long logicalTimeStamp;
    private String id;
    private MapLocation location;
    private Integer carId;
    private Direction direction;
    private Integer routing;


    public CarPositionUpdateTuple(MapLocation mapLocation, Integer carId, Long logicalTimeStamp, Direction direction, Integer routing) {
        this.carId = carId;
        this.location = mapLocation;
        this.logicalTimeStamp = logicalTimeStamp;
        this.direction = direction;
        this.routing = routing;
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

    public MapLocation getLocation() {
        return location;
    }

    public void setLocation(MapLocation location) {
        this.location = location;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @SpaceRouting
    public Integer getRouting() {
        return routing;
    }

    public void setRouting(Integer routing) {
        this.routing = routing;
    }
}
