package starter;


import connector.GigaSpaceConnector;
import org.openspaces.core.GigaSpace;
import simulation.CarPool;
import simulation.LocalTrafficLight;
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
        ConfigurationTupel ct = null;
        while(ct==null) {
            ct = gigaspace.read(new ConfigurationTupel());
            System.out.println("Configuration:pending");
        }
        System.out.println("Configuration:finished");
        //... und erst dann gehts weiter.

        //CarPool mit X Threads erzeugen...
        CarPool carPool = new CarPool(ct.getNumberOfCars()+1);
       // CarPool carPool = new CarPool(5);

        //Carpools starten alle CarThreads
        carPool.startAllCars();

        LocalTrafficLight tl = new LocalTrafficLight();
        //tl.start();

        System.out.println("End");
    }


}
