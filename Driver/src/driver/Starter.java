package driver;

import connector.GigaSpaceConnector;
import org.openspaces.core.GigaSpace;
import tuples.config.ConfigurationTupel;

import static others.GlobalConstances.NO_TIMEOUT;

/**
 * Created by tobi on 08.04.14.
 */
public class Starter {
    public static void main(String[] args){
        GigaSpace gigaSpace = GigaSpaceConnector.getGigaSpace();

        ConfigurationTupel ct = gigaSpace.read(new ConfigurationTupel());
        while(ct == null) {
            ct = gigaSpace.read(new ConfigurationTupel());
        }
        ExternelDriver externelDriver = new ExternelDriver(gigaSpace);

        externelDriver.start();
    }


}
