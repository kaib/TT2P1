package tuples;

import com.gigaspaces.annotation.pojo.SpaceId;
import others.Direction;



/**
 * Created by tobi on 31.03.14.
 */
public class CarTupel {

    private Direction direction;
    private Integer id;

    public CarTupel(){};

    public CarTupel(int id, Direction direction){
        this.direction = direction;
        this.id = id;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
