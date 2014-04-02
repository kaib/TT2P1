package starter;


import org.openspaces.core.GigaSpace;
import connector.GigaSpaceConnector;
import tuples.RoxelTuple;


/**
 * Created by tobi on 31.03.14.
 */
public class Starter {
    private static GigaSpace gigaspace;

    public static void main(String[] arg){
        gigaspace = GigaSpaceConnector.getGigaSpace();

        RoxelTuple r = gigaspace.read(new RoxelTuple());
        RoxelTuple r2 = gigaspace.read(new RoxelTuple(), 10000);
        System.out.println("End");
    }


}
