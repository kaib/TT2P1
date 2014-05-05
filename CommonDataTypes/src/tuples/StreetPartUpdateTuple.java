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
public class StreetPartUpdateTuple {

    private Long logicalTimeStamp;
    private String id;
    private MapLocation location;
    private Integer roxelId;
    private Direction direction;
    private Boolean crossroad;
    private Integer routing;




    public StreetPartUpdateTuple(MapLocation mapLocation, Integer roxelId, Long logicalTimeStamp, Direction direction, Boolean crossroad, Integer routing) {
        this.roxelId = roxelId;
        this.location = mapLocation;
        this.logicalTimeStamp = logicalTimeStamp;
        this.direction = direction;
        this.crossroad = crossroad;
        this.routing = routing;
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

    public Boolean getCrossroad() {
        return crossroad;
    }

    public void setCrossroad(Boolean crossroad) {
        this.crossroad = crossroad;
    }
    @SpaceRouting
    public Integer getRouting() {
        return routing;
    }

    public void setRouting(Integer routing) {
        this.routing = routing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StreetPartUpdateTuple)) return false;

        StreetPartUpdateTuple that = (StreetPartUpdateTuple) o;

        if (crossroad != null ? !crossroad.equals(that.crossroad) : that.crossroad != null) return false;
        if (direction != that.direction) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (logicalTimeStamp != null ? !logicalTimeStamp.equals(that.logicalTimeStamp) : that.logicalTimeStamp != null)
            return false;
        if (routing != null ? !routing.equals(that.routing) : that.routing != null) return false;
        if (roxelId != null ? !roxelId.equals(that.roxelId) : that.roxelId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = logicalTimeStamp != null ? logicalTimeStamp.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (roxelId != null ? roxelId.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (crossroad != null ? crossroad.hashCode() : 0);
        result = 31 * result + (routing != null ? routing.hashCode() : 0);
        return result;
    }
}
