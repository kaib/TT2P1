package simulation;

import com.j_spaces.core.client.SQLQuery;
import connector.GigaSpaceConnector;
import interfaces.CarTuple;
import org.openspaces.core.GigaSpace;
import others.CarDriver;
import others.IdGenerator;
import tuples.RealCarTuple;
import tuples.RoxelTuple;
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
    private RoxelTuple carRoxel;
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
                    sleep(1500);
                } catch (InterruptedException e) {

                }

                //lege das Auto wieder in den TupleSpace
                releaseCar();
            }



        log.info(String.format("CarWorker %d stopped working", workerId));
    }

    public void takeCar(){

        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"car.id != ?",-1);
        carRoxel = gigaSpace.take(sql,NO_TIMEOUT);

            log.info(String.format("CarWorker %d took car %d from [%d,%d]", workerId, carRoxel.getCar().getId(),carRoxel.getPositionX(),carRoxel.getPositionY()));
            carDriver.setCurrentPosition(carRoxel);
    }

    public void releaseCar() {
        carRoxel = carDriver.getCurrentPosition();
        gigaSpace.write(carRoxel);
        log.info(String.format("CarWorker %d released car %d on [%d,%d]", workerId, carRoxel.getCar().getId(),carRoxel.getPositionX(),carRoxel.getPositionY()));
        carDriver.removeDriver();
        carRoxel = null;
    }






}
