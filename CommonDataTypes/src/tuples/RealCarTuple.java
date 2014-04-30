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
}
