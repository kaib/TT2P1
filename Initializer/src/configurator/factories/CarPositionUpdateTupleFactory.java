package configurator.factories;

import interfaces.CarTuple;
import others.CarLocation;
import tuples.CarPositionUpdateTuple;
import tuples.RoxelTuple;

import java.util.LinkedList;
import java.util.List;

/**
 * Dient dem Erstellen der CarPositionUpdateTuple. Diese können von einem entsprechenden Viewer gelesen
 * und zur Darstellung verwendet werden.
 * Created by dude on 07.04.2014.
 */
public class CarPositionUpdateTupleFactory {

    public CarPositionUpdateTupleFactory(){}

    public List<CarPositionUpdateTuple> createCarPositionUpdateTuples(List<RoxelTuple> roxels){
        List<CarPositionUpdateTuple> carPositionUpdateTupleList = new LinkedList<>();
        for(RoxelTuple roxel:roxels) {
            System.out.println(roxel);
            if(roxel.getCarId() != -1) {
                carPositionUpdateTupleList.add(createCarPostionUpdateTuple(roxel));
            }
        }
        return carPositionUpdateTupleList;
    }

    public CarPositionUpdateTuple createCarPostionUpdateTuple(RoxelTuple roxel) {
        return new CarPositionUpdateTuple(new CarLocation(roxel.getPositionX(),roxel.getPositionY()),roxel.getCarId(),0L);
    }
}
