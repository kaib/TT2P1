package tuples;

import interfaces.*;
import others.Direction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by tobi on 31.03.14.
 */
public class NoCar implements interfaces.Car{

    public NoCar() {
    }

    @Override
    public Direction getDirection() {
        return Direction.TODECIDE;
    }

    @Override
    public void setDirection(Direction direction) {
        throw new NotImplementedException();
    }

    @Override
    public int getId() {
        return -1;
    }

    @Override
    public void setId(int id) {
        throw new NotImplementedException();
    }
}
