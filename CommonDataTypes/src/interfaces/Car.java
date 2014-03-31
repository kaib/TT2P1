package interfaces;

import com.gigaspaces.annotation.pojo.SpaceId;
import others.Direction;

/**
 * Created by tobi on 31.03.14.
 */
public interface Car {
    Direction getDirection();

    void setDirection(Direction direction);

    @SpaceId
    int getId();

    void setId(int id);
}
