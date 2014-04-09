package simulation;

import connector.GigaSpaceConnector;
import interfaces.CarTuple;
import org.openspaces.core.GigaSpace;
import others.CarDriver;
import others.IdGenerator;
import tuples.RealCarTuple;
import tuples.config.ConfigurationTupel;

import java.util.logging.Logger;

import static others.GlobalConstances.NO_TIMEOUT;

/**
 * Die CarWorker-Threads nehmen sich jeweils ein zuälliges Car, "fahren" es auf das nächste Roxel und legen das Car wieder
 *  in den TupleSpace.
 *  Dann beginnen sie von vorne...
 * Created by tobi on 02.04.14.
 */
public class CarWorker extends Thread {

    private static final Logger log = Logger.getLogger( CarWorker.class.getName() );
    private GigaSpace gigaSpace;
    private ConfigurationTupel configurationTupel;
    private int workerId;
    private CarTuple car;
    private CarDriver carDriver;

    public CarWorker() {
        gigaSpace = GigaSpaceConnector.getGigaSpace();
        workerId = IdGenerator.getNewID();
    }

    @Override
    public void run() {
        // Sicherstellen, das Konfiguration abgeschlossen
        configurationTupel = gigaSpace.read(new ConfigurationTupel(), NO_TIMEOUT);
        carDriver = new CarDriver(gigaSpace,configurationTupel);

        log.info(String.format("CarWorker %d starts working", workerId));


        while (! isInterrupted()){

            //entnehme Auto aus dem TuplSpace
            takeCar();

            //Bewege Auto
            carDriver.move();

            // Warte X Millisekunden
            try {
                sleep(500);
            } catch (InterruptedException e) {

            }

            //lege das Auto wieder in den TupleSpace
            releaseCar();
        }

        log.info(String.format("CarWorker %d stopped working", workerId));
    }

    public void takeCar(){
        car = gigaSpace.take(new RealCarTuple(), NO_TIMEOUT);
        log.info(String.format("CarWorker %d took car %d", workerId, car.getId()));
        carDriver.setCar(car);
    }

    public void releaseCar() {
        gigaSpace.write(car);
        log.info(String.format("CarWorker %d released car %d", workerId, car.getId()));
        carDriver.removeCar();
        car = null;
    }






}
