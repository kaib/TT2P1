package tuples;

import com.gigaspaces.annotation.pojo.SpaceId;
import interfaces.CarTuple;
import others.Direction;



/**
 * Created by tobi on 31.03.14.
 */
public class RealCarTuple implements CarTuple {

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

    @Override
    @SpaceId
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
