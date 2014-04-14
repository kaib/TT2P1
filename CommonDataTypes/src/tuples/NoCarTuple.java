package tuples;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import interfaces.CarTuple;
import others.Direction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by tobi on 31.03.14.
 */
@SpaceClass
public class NoCarTuple implements CarTuple{

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

    @SpaceIndex
    @SpaceId
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        throw new NotImplementedException();
    }
}
