package trafficLights;

import com.j_spaces.core.client.SQLQuery;
import connector.GigaSpaceConnector;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import others.MapLocation;
import tuples.RoxelTuple;
import tuples.StreetPartUpdateTuple;

import java.util.Random;

import static others.Direction.*;

@EventDriven
@Notify
public class TrafficLight {

    private static Random rand = new Random();
    private GigaSpace gigaSpace = GigaSpaceConnector.getGigaSpace();

    @EventTemplate
    public SQLQuery<RoxelTuple> template(){
         return new SQLQuery<>(RoxelTuple.class,"direction = ?", TODECIDE);
    }

    @SpaceDataEvent
    public RoxelTuple eventListener(RoxelTuple event){
        switchSignal(event);
        StreetPartUpdateTuple streetPartUpdateTuple = new StreetPartUpdateTuple( new MapLocation(event.getPositionX(), event.getPositionY()), event.getId(),  0L, event.getDirection(), true);
        gigaSpace.write(streetPartUpdateTuple);
        gigaSpace.write(event);
        return event;
    }

    private void switchSignal(RoxelTuple roxelTuple){
        if (roxelTuple.getDirection().equals(TODECIDE)){
            boolean south = true;
            if (south){
                roxelTuple.setDirection(SOUTH);
            } else {
                roxelTuple.setDirection(EAST);
            }
        }
    }
}
