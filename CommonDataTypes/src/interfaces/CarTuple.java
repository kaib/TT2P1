package interfaces;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import others.Direction;

import java.io.Serializable;

/**
 * Created by tobi on 02.04.14.
 */
public interface CarTuple extends Serializable{
    Direction getDirection();

    void setDirection(Direction direction);

    @SpaceIndex
    @SpaceId
    Integer getId();

    void setId(Integer id);
}
