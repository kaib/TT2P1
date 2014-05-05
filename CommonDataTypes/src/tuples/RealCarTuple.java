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
public class RealCarTuple implements CarTuple, Serializable {

    private Direction direction;
    private Integer id;

    public RealCarTuple(){};

    public RealCarTuple(int id, Direction direction){
        this.direction = direction;
        this.id = id;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    @SpaceRouting
    @Override
    @SpaceId
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RealCarTuple{" +
                "direction=" + direction +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealCarTuple)) return false;

        RealCarTuple that = (RealCarTuple) o;

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
