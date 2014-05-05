package configurator.factories;

import others.MapLocation;
import tuples.RoxelTuple;
import tuples.StreetPartUpdateTuple;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dude on 05.05.2014.
 */
public class StreetPartUpdateTupleFactory {
    public StreetPartUpdateTupleFactory(){}



    public List<StreetPartUpdateTuple> createStreetPartUpdateTuples(List<RoxelTuple> roxels){
        List<StreetPartUpdateTuple> streetPartUpdateTupleList = new LinkedList<>();
        for(RoxelTuple roxel: roxels) {
            if(roxel.getCrossroad())
            streetPartUpdateTupleList.add(createStreetPartUpdateTuple(roxel));
        }
        return streetPartUpdateTupleList;
    }

    public StreetPartUpdateTuple createStreetPartUpdateTuple(RoxelTuple roxel) {
        return new StreetPartUpdateTuple(new MapLocation(roxel.getPositionX(),roxel.getPositionY()),roxel.getId(),0L, roxel.getDirection(),roxel.getCrossroad(),0);
    }
}
