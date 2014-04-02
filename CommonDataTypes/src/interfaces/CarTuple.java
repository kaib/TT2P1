package interfaces;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import others.Direction;

/**
 * Created by tobi on 02.04.14.
 */
public interface CarTuple {
    Direction getDirection();

    void setDirection(Direction direction);

    @SpaceIndex
    @SpaceId
    Integer getId();

    void setId(Integer id);
}
