package trafficLights;

import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.client.WriteModifiers;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;

import others.MapLocation;
import tuples.CarPositionUpdateTuple;
import tuples.RoxelTuple;
import tuples.StreetPartUpdateTuple;
import tuples.config.ConfigurationTupel;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static others.Direction.*;

@EventDriven
@Notify(performTakeOnNotify = true, ignoreEventOnNullTake = true)
@NotifyType(write = true, update = true)
public class TrafficLight {


    private static Random rand = new Random();
    @GigaSpaceContext
    GigaSpace gigaSpace;

    ConfigurationTupel ct;

    @EventTemplate
    public SQLQuery<RoxelTuple> template(){
         return new SQLQuery<>(RoxelTuple.class,"direction = 'TODECIDE'");
    }

    @SpaceDataEvent
    public RoxelTuple eventListener(RoxelTuple event){

        switchSignal(event);
        StreetPartUpdateTuple streetPartUpdateTuple = new StreetPartUpdateTuple(new MapLocation(event.getPositionX(),event.getPositionY()),event.getId(),0l,event.getDirection(),event.getCrossroad(),0);
        if(streetPartUpdateTuple != null) {
            streetPartUpdateTuple.setDirection(event.getDirection());
        }
       // gigaSpace.write(streetPartUpdateTuple);
        SQLQuery<StreetPartUpdateTuple> idQuery = new SQLQuery<>(StreetPartUpdateTuple.class,"roxelId = ?");
        idQuery.setParameter(1,event.getId());
        gigaSpace.change(idQuery, new ChangeSet().increment("logicalTimeStamp", 1L)
                .set("direction", streetPartUpdateTuple.getDirection()));

       return event;
    }

    private void switchSignal(RoxelTuple roxelTuple){
        CarPositionUpdateTuple[] carPositions;
        carPositions = gigaSpace.readMultiple(new CarPositionUpdateTuple());
        int countSouth = 0;
        int countEast = 0;

        //Determine Cars which want to pass the traffic light
        for(CarPositionUpdateTuple car:carPositions){

            if((car.getDirection() == SOUTH) && carNearTrafficLight(roxelTuple,car)) {
                countSouth++;
            } else if(car.getDirection() == EAST && carNearTrafficLight(roxelTuple,car)) {
                countEast++;
            }
        }
        if(countSouth>countEast) {
            roxelTuple.setDirection(SOUTH);
        } else if(countSouth<countEast) {
            roxelTuple.setDirection(EAST);
        } else if(countSouth == countEast && (countSouth != 0)) {
             if(rand.nextBoolean()) {
                roxelTuple.setDirection(SOUTH);
            } else {
                roxelTuple.setDirection(EAST);
            }
        } else{
            roxelTuple.setDirection(TODECIDE);
        }

    }
    private boolean carNearTrafficLight(RoxelTuple trafficLight, CarPositionUpdateTuple car) {
        if(ct == null) {
            ct = gigaSpace.read(new ConfigurationTupel());
        }
        boolean result = false;
        int maxX = ct.getMapSizeX();
        int maxY = ct.getMapSizeY();
        int carX = car.getLocation().getX();
        int carY = car.getLocation().getY();
        int lightX = trafficLight.getPositionX();
        int lightY = trafficLight.getPositionY();
        if(car.getDirection() == SOUTH) {
            result = (carX == lightX) && (((carY<lightY) && (lightY - carY) <= 2) || (lightY == 0 && maxY - carY <=2 ));
        } else if(car.getDirection() == EAST) {
            result = (carY == lightY) && (((carX<lightX) && (lightX - carX) <= 2) || (lightX == 0 && maxX - carX <=2 ));
        }
        return result;
    }
}
