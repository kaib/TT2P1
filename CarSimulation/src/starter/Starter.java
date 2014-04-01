package starter;


import org.openspaces.core.GigaSpace;
import connector.GigaSpaceConnector;
import tuples.RoxelTupel;

import java.rmi.RemoteException;


/**
 * Created by tobi on 31.03.14.
 */
public class Starter {
    private static GigaSpace gigaspace;

    public static void main(String[] arg){
        gigaspace = GigaSpaceConnector.getGigaSpace();

        RoxelTupel r = gigaspace.read(new RoxelTupel());
        RoxelTupel r2 = gigaspace.read(new RoxelTupel(), 10000);
        System.out.println("End");
    }


}
