package others;

import com.gigaspaces.annotation.pojo.SpaceClass;

import java.io.Serializable;

/**
 * Created by tobi on 31.03.14.
 */
@SpaceClass
public enum Direction implements Serializable{
    EAST, WEST, NORTH, SOUTH, BLOCKED, TODECIDE;
}
