package starter;

import configurator.Configurator;
import org.openspaces.core.GigaSpace;
import connector.GigaSpaceConnector;

import java.rmi.RemoteException;


/**
 * Created by tobi on 31.03.14.
 */
public class Starter {
    private static final boolean resetTupleSpaceOnStart = true;
    private static GigaSpace gigaspace;

    public static void main(String[] arg){
        gigaspace = GigaSpaceConnector.getGigaSpace();

        if (resetTupleSpaceOnStart){
            try {
                gigaspace.getSpace().clean();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        startConfiguration();
        System.out.println("End");
    }

    private static void startConfiguration() {
        Configurator config = new Configurator(gigaspace);
        config.setBlockSize(4);
        config.setMapSizeX(20);
        config.setMapSizeY(20);
        config.setRoxelSizeX(1);
        config.setRoxelSizeY(1);
        config.setNumberOfCars(4);
        config.runConfigurationIfNecessary();
    }
}
