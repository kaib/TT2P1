package simulation;

import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.query.IdQuery;
import com.j_spaces.core.client.SQLQuery;
import connector.GigaSpaceConnector;
import interfaces.CarTuple;
import org.openspaces.core.GigaSpace;
import others.CarLocation;
import others.Direction;
import others.IdGenerator;
import tuples.CarPositionUpdateTuple;
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
    private CarTuple car;
    private int workerId;

    public CarWorker() {
        gigaSpace = GigaSpaceConnector.getGigaSpace();
        workerId = IdGenerator.getNewID();
    }

    @Override
    public void run() {
        // Sicherstellen, das Konfiguration abgeschlossen
        configurationTupel = gigaSpace.read(new ConfigurationTupel(), NO_TIMEOUT);

        log.info(String.format("CarWorker %d starts working", workerId));


        while (! isInterrupted()){

            //entnehme Auto aus dem TuplSpace
            takeCar();

            //Bewege Auto
            move();

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



    private void move() {
        switch (car.getDirection()){
            case EAST:
                driveEast();
                break;
            case WEST:
                driveWest();
                break;
            case SOUTH:
                driveSouth();
                break;
            case NORTH:
                driveNorth();
                break;
        }
    }

    private void drive(Direction direction) {
        switch (car.getDirection()){
            case EAST:
                driveEast();
                break;
            case WEST:
                driveWest();
                break;
            case SOUTH:
                driveSouth();
                break;
            case NORTH:
                driveNorth();
                break;
        }
    }

    private void driveNorth() {
        RoxelTuple current = takeCurrentRoxel();
        int currentY = current.getPositionY();
        int nextY = currentY != 0 ? currentY-1 : configurationTupel.getMapSizeY()-1;
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"carId = ? AND positionY = ? AND positionX = ?",-1,nextY,current.getPositionX());
        RoxelTuple next = takeNextRoxel(sql);
        changePosition(current,next);
        releaseRoxel(current,next);
    }

    private void driveSouth() {
        RoxelTuple current = takeCurrentRoxel();
        int currentY = current.getPositionY();
        int nextY = currentY != configurationTupel.getMapSizeY()-1 ? currentY+1 : 0;
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"carId = ? AND positionY = ? AND positionX = ?",-1,nextY,current.getPositionX());
        RoxelTuple next = takeNextRoxel(sql);
        changePosition(current,next);

        releaseRoxel(current,next);
    }

    private void driveWest() {
        RoxelTuple current = takeCurrentRoxel();
        int currentX = current.getPositionX();
        int nextX = currentX != 0 ? currentX-1 : configurationTupel.getMapSizeY()-1;
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"carId = ? AND positionY = ? AND positionX = ?",-1,current.getPositionY(),nextX);
        RoxelTuple next = takeNextRoxel(sql);
        changePosition(current,next);
        releaseRoxel(current,next);
    }

    private void driveEast() {
        RoxelTuple current = takeCurrentRoxel();
        int currentX = current.getPositionX();
        int nextX = currentX != configurationTupel.getMapSizeY()-1 ? currentX+1 : 0;
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"carId = ? AND positionY = ? AND positionX = ?",-1,current.getPositionY(),nextX);
        RoxelTuple next = takeNextRoxel(sql);
        changePosition(current,next);
        releaseRoxel(current,next);
    }

    private void releaseAllTuple(){}

    private void takeCar(){
        car = gigaSpace.take(new RealCarTuple(), NO_TIMEOUT);
        log.info(String.format("CarWorker %d took car %d", workerId, car.getId()));
    }

    private void releaseCar() {
        gigaSpace.write(car);
        log.info(String.format("CarWorker %d released car %d", workerId, car.getId()));
        car = null;
    }

    private RoxelTuple takeCurrentRoxel(){
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"carId=?",car.getId());
        return gigaSpace.take(sql,NO_TIMEOUT);
    }

    private RoxelTuple takeNextRoxel(SQLQuery<RoxelTuple> sql){
        return gigaSpace.take(sql,NO_TIMEOUT);
    }

    private void changePosition(RoxelTuple from, RoxelTuple to){
        from.setCarId(-1);
        to.setCarId(car.getId());
        log.info(String.format(String.format("CarWorker %d moved car %d from %d|%d to %d|%d", workerId, car.getId(), from.getPositionX(), from.getPositionY(), to.getPositionX(), to.getPositionY())));
    }

    private void releaseRoxel(RoxelTuple from, RoxelTuple to){
        RoxelTuple[] updateRoxels = {from,to};
        gigaSpace.writeMultiple(updateRoxels);
        updateLocation(new CarLocation(to.getPositionX(), to.getPositionY()));
    }

    private void updateLocation(CarLocation location) {
        SQLQuery<CarPositionUpdateTuple> idQuery = new SQLQuery<>(CarPositionUpdateTuple.class,"carId = ?");
        idQuery.setParameter(1,car.getId());
        gigaSpace.change(idQuery, new ChangeSet().increment("logicalTimeStamp", 1L)
                .set("location", location));
    }


}
