package trafficLights;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import others.MapLocation;
import tuples.RoxelTuple;
import tuples.StreetPartUpdateTuple;

import java.util.Random;

import static others.Direction.*;

@EventDriven
@Notify(performTakeOnNotify = true, ignoreEventOnNullTake = true)
@NotifyType(write = true, update = true)
public class TrafficLight {

    private static Random rand = new Random();
    @GigaSpaceContext
    GigaSpace gigaSpace;

    @EventTemplate
    public SQLQuery<RoxelTuple> template(){
         return new SQLQuery<>(RoxelTuple.class,"direction = 'TODECIDE'");
    }

    @SpaceDataEvent
    public StreetPartUpdateTuple eventListener(RoxelTuple event){
        switchSignal(event);
        gigaSpace.write(event);
       return new StreetPartUpdateTuple( new MapLocation(event.getPositionX(), event.getPositionY()), event.getId(),  0L, event.getDirection(), true);
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
