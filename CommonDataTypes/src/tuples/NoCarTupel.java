package tuples;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import interfaces.*;
import others.Direction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by tobi on 31.03.14.
 */
public class NoCarTupel{

    Integer id = -1;

    public NoCarTupel() {
    }

    public Direction getDirection() {
        return Direction.TODECIDE;
    }

    public void setDirection(Direction direction) {
        throw new NotImplementedException();
    }

    @SpaceIndex
    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        throw new NotImplementedException();
    }
}
