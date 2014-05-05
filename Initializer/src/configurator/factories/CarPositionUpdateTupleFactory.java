package configurator.factories;

import interfaces.CarTuple;
import others.MapLocation;
import tuples.CarPositionUpdateTuple;
import tuples.RoxelTuple;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Dient dem Erstellen der CarPositionUpdateTuple. Diese können von einem entsprechenden Viewer gelesen
 * und zur Darstellung verwendet werden.
 * Created by dude on 07.04.2014.
 */
public class CarPositionUpdateTupleFactory {

    public CarPositionUpdateTupleFactory(){}

    public List<CarPositionUpdateTuple> createCarPositionUpdateTuples(List<RoxelTuple> roxelCarList){
        List<CarPositionUpdateTuple> carPositionUpdateTupleList = new LinkedList<>();
        for(RoxelTuple roxel: roxelCarList) {
            System.out.println(roxel);
            carPositionUpdateTupleList.add(createCarPostionUpdateTuple(roxel));
        }
        return carPositionUpdateTupleList;
    }

    public CarPositionUpdateTuple createCarPostionUpdateTuple(RoxelTuple roxel) {
        return new CarPositionUpdateTuple(new MapLocation(roxel.getPositionX(),roxel.getPositionY()),roxel.getCar().getId(),0L, roxel.getCar().getDirection(),0);
    }
}
