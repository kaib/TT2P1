package driver;

import interfaces.CarTuple;
import org.openspaces.core.GigaSpace;
import others.CarDriver;
import tuples.RealCarTuple;
import tuples.RoxelTuple;
import tuples.config.ConfigurationTupel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import static others.GlobalConstances.NO_TIMEOUT;

/**
 * Created by tobi on 08.04.14.
 */
public class ExternelDriver {

    private static final Logger log = Logger.getLogger( ExternelDriver.class.getName() );

    private GigaSpace gigaSpace;
    private ConfigurationTupel configurationTupel;
    private CarDriver carDriver;
    private CarTuple car;

    public ExternelDriver(GigaSpace gigaSpace) {
        this.gigaSpace = gigaSpace;
    }


    public void start() {
        // Sicherstellen, das Konfiguration abgeschlossen
        configurationTupel = gigaSpace.read(new ConfigurationTupel(), NO_TIMEOUT);
        carDriver = new CarDriver(gigaSpace,configurationTupel);

        System.out.println("Hey Guy!");


        boolean running = true;

        //entnehme Auto aus dem TuplSpace
        takeCar();
        System.out.println("You hijacked the following car:");
        RoxelTuple roxelTuple = carDriver.readCurrentRoxel();
        System.out.println(String.format("Car %d, on Street %d|%d, driving in the direction %s!", car.getId(), roxelTuple.getPositionX(), roxelTuple.getPositionY(), car.getDirection()));

        while (running){
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please choose, what you wanna do!");
            System.out.println("[1]: Drive!");
            System.out.println("[2]: Leave the car and run away!");
            String zeile = null;
            try {
                zeile = console.readLine();
            } catch (IOException e) {
                // Sollte eigentlich nie passieren
                e.printStackTrace();
            }
            if (zeile.trim().startsWith("1"))
            {
                carDriver.move();
            }
            else if (zeile.trim().startsWith("2")){
                running = false;
            } else {
                System.out.println("I can't understand you!");
            }

        }
        //lege das Auto wieder in den TupleSpace
        releaseCar();

    }

    public void takeCar(){
        car = gigaSpace.take(new RealCarTuple(), NO_TIMEOUT);
        carDriver.setCar(car);
    }

    public void releaseCar() {
        gigaSpace.write(car);
        log.info("You have left the car!");
        carDriver.removeCar();
        car = null;
    }
}
