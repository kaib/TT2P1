package tuples;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import interfaces.CarTuple;
import others.Direction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;

/**
 * Created by tobi on 31.03.14.
 */
@SpaceClass
public class NoCarTuple implements CarTuple, Serializable{

    private Direction direction = Direction.TODECIDE;
    private Integer id = -1;

    public NoCarTuple() {}

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        throw new NotImplementedException();
    }

    @SpaceRouting
    @SpaceIndex
    @SpaceId
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return "NoCarTuple{" +
                "direction=" + direction +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NoCarTuple)) return false;

        NoCarTuple that = (NoCarTuple) o;

        if (direction != that.direction) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = direction != null ? direction.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
