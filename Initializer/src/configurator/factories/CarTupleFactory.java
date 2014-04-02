package configurator.factories;

import others.IdGenerator;
import interfaces.CarTuple;
import others.Direction;
import tuples.NoCarTuple;
import tuples.RealCarTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for CarTuples
 */
public class CarTupleFactory {

    public static final NoCarTuple noCarTuple = new NoCarTuple();

    public CarTupleFactory() {
    }

    public List<CarTuple> createCarTuples(int numberOfCars){
        List<CarTuple> cars = new ArrayList<>();
        for (int i = 0; i<=numberOfCars; i++){
            cars.add(createCarTuple());
        }
        return cars;
    }

    public CarTuple createCarTuple(){
        return new RealCarTuple(IdGenerator.getNewID(), Direction.TODECIDE);
    }

    public CarTuple createNoCarTuple(){
        return noCarTuple;
    }
}
