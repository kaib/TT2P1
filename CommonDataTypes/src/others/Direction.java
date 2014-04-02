package others;

import com.gigaspaces.annotation.pojo.SpaceClass;

/**
 * Created by tobi on 31.03.14.
 */
@SpaceClass
public enum Direction {
    EAST, WEST, NORTH, SOUTH, BLOCKED, TODECIDE;
}
