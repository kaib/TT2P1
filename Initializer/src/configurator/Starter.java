package configurator;

import connector.GigaSpaceConnector;
import org.openspaces.core.GigaSpace;

import java.rmi.RemoteException;

/**
 * Created by tobi on 01.04.14.
 */
public class Starter {
    /*
        Wird der Initializer gestartet, obwohl das Grid bereits initializiert wird, passiert nichts.
        Wird die Variable resetTupleSpaceOnStart jedoch auf true gesetzt, wird beim Start der TupleSpace bereinigt, d.h.
        es werdenalle aktuell im TupleSpace befindlichen Daten gelöscht!
        ACHTUNG: Wenn die Simulation gerade läuft und Tuple aus dem TupleSpace gelesen hat und diese nach der
        Bereinigung zurückschreibt, kommt es u.U. zu einem Inkonsisteneten Zustand da alte und neue Tuple im Space liegen!


     */
    private static final boolean resetTupleSpaceOnStart = false;

    private static GigaSpace gigaspace;

    public static void main(String[] args){
        gigaspace = GigaSpaceConnector.getGigaSpace();

        if (resetTupleSpaceOnStart){
            try {
                gigaspace.getSpace().clean();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        startConfiguration();
    }


    private static void startConfiguration() {
        Initializer config = new Initializer(gigaspace);
        config.setBlockSize(4);
        config.setMapSizeX(20);
        config.setMapSizeY(20);
        config.setRoxelSizeX(1);
        config.setRoxelSizeY(1);
        config.setNumberOfCars(5);
        config.runConfigurationIfNecessary();
    }
}
