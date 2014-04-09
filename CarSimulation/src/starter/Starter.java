package starter;


import connector.GigaSpaceConnector;
import org.openspaces.core.GigaSpace;
import simulation.CarPool;
import tuples.config.ConfigurationTupel;

import static others.GlobalConstances.NO_TIMEOUT;


/**
 * Created by tobi on 31.03.14.
 */
public class Starter {
    private static GigaSpace gigaspace;

    public static void main(String[] arg){
        gigaspace = GigaSpaceConnector.getGigaSpace();

        //Warten, bis initialisierung abgeschlossen wurde...
        ConfigurationTupel ct = gigaspace.read(new ConfigurationTupel(), NO_TIMEOUT);
        //... und erst dann gehts weiter.

        //CarPool mit X Threads erzeugen...
        CarPool carPool = new CarPool(ct.getNumberOfCars()+1);

        //Carpools starten alle CarThreads
        carPool.startAllCars();

        System.out.println("End");
    }


}
