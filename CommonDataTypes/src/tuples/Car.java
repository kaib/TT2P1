package tuples;

import com.gigaspaces.annotation.pojo.SpaceId;
import others.Direction;



/**
 * Created by tobi on 31.03.14.
 */
public class Car implements interfaces.Car {

    private Direction direction;
    private int id;

    public Car(){};

    public Car(int id, Direction direction){
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
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
