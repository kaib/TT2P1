package tuples;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import others.MapLocation;
import others.Direction;

/**
 * Created by Sebastian on 05.04.2014.
 */
@SpaceClass
public class StreetPartUpdateTuple {

    private Long logicalTimeStamp;
    private String id;
    private MapLocation location;
    private Integer roxelId;
    private Direction direction;


    public StreetPartUpdateTuple(MapLocation mapLocation, int roxelId, long logicalTimeStamp, Direction direction) {
        this.roxelId = roxelId;
        this.location = mapLocation;
        this.logicalTimeStamp = logicalTimeStamp;
        this.direction = direction;
    }

    public StreetPartUpdateTuple() {  }

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

    public Integer getRoxelId() {
        return roxelId;
    }

    public void setRoxelId(Integer roxelId) {
        this.roxelId = roxelId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
