package configurator.factories;

import interfaces.CarTuple;
import others.CarLocation;
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

    public List<CarPositionUpdateTuple> createCarPositionUpdateTuples(Map<RoxelTuple,CarTuple> roxelCarMap){
        List<CarPositionUpdateTuple> carPositionUpdateTupleList = new LinkedList<>();
        for(Map.Entry<RoxelTuple,CarTuple> entry : roxelCarMap.entrySet()) {
            System.out.println(entry.getKey());
            carPositionUpdateTupleList.add(createCarPostionUpdateTuple(entry.getKey(), entry.getValue()));
        }
        return carPositionUpdateTupleList;
    }

    public CarPositionUpdateTuple createCarPostionUpdateTuple(RoxelTuple roxel, CarTuple car) {
        return new CarPositionUpdateTuple(new CarLocation(roxel.getPositionX(),roxel.getPositionY()),roxel.getCarId(),0L, car.getDirection());
    }
}
