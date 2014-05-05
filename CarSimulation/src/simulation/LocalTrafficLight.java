package simulation;

import com.j_spaces.core.client.SQLQuery;
import connector.GigaSpaceConnector;
import org.openspaces.core.GigaSpace;
import others.GlobalConstances;
import others.MapLocation;
import tuples.RoxelTuple;
import tuples.StreetPartUpdateTuple;

import java.util.Random;

import static others.Direction.*;

/**
 * Created by tobi on 28.04.14.
 */
public class LocalTrafficLight extends Thread {
    private GigaSpace gigaSpace;


    private static Random rand = new Random();

    @Override
    public void run() {
        gigaSpace = GigaSpaceConnector.getGigaSpace();
        while (true){
            action();
            System.out.println("action");
        }
    }

    private void action(){
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"direction = ?", TODECIDE);

        RoxelTuple event=  gigaSpace.take(sql, GlobalConstances.NO_TIMEOUT);
        System.out.println(event);
        switchSignal(event);
        StreetPartUpdateTuple streetPartUpdateTuple = new StreetPartUpdateTuple( new MapLocation(event.getPositionX(), event.getPositionY()), event.getId(),  0L, event.getDirection(), true,0);

        gigaSpace.write(streetPartUpdateTuple);
        System.out.println(event);
        gigaSpace.write(event);
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
