package driver;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import others.CarDriver;
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
    private RoxelTuple carRoxel;

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
        System.out.println("You hijacked the following carRoxel:");
        RoxelTuple roxelTuple = carDriver.getCurrentPosition();
        System.out.println(String.format("Car %d, on Street %d|%d, driving in the direction %s!", carRoxel.getId(), roxelTuple.getPositionX(), roxelTuple.getPositionY(), carRoxel.getDirection()));

        while (running){
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please choose, what you wanna do!");
            System.out.println("[1]: Drive!");
            System.out.println("[2]: Leave the carRoxel and run away!");
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
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"car.id != ?",-1);
        carRoxel = gigaSpace.take(sql,NO_TIMEOUT);
        carDriver.setCurrentPosition(carRoxel);
    }

    public void releaseCar() {
        gigaSpace.write(carDriver.getCurrentPosition());
        log.info("You have left the car!");
        carDriver.removeDriver();
        carRoxel = null;
    }
}
