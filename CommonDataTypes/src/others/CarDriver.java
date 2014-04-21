package others;

import com.gigaspaces.client.ChangeSet;
import com.j_spaces.core.client.SQLQuery;
import interfaces.CarTuple;
import org.openspaces.core.GigaSpace;
import tuples.CarPositionUpdateTuple;
import tuples.RoxelTuple;
import tuples.config.ConfigurationTupel;

import java.util.logging.Logger;

import static others.GlobalConstances.NO_TIMEOUT;

/**
 * Created by tobi on 08.04.14.
 */
public class CarDriver {

    private static final Logger log = Logger.getLogger( CarDriver.class.getName() );

    private GigaSpace gigaSpace;



    private CarTuple car;
    private ConfigurationTupel configurationTupel;
    public CarDriver(GigaSpace gigaSpace, ConfigurationTupel configurationTupel) {
        this.gigaSpace = gigaSpace;
        this.configurationTupel = configurationTupel;
    }

    public CarTuple getCar() {
        return car;
    }

    public void setCar(CarTuple car) {
        this.car = car;
    }

    public void move() {
        move(car.getDirection());
    }

    private void move(Direction direction) {
        switch (direction){
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






    private RoxelTuple takeCurrentRoxel(){
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"carId=?",car.getId());
        return gigaSpace.take(sql,NO_TIMEOUT);
    }

    public RoxelTuple readCurrentRoxel(){
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"carId=?",car.getId());
        return gigaSpace.read(sql,NO_TIMEOUT);
    }

    private RoxelTuple takeNextRoxel(SQLQuery<RoxelTuple> sql){
        return gigaSpace.take(sql,NO_TIMEOUT);
    }

    private void changePosition(RoxelTuple from, RoxelTuple to){
        from.setCarId(-1);
        to.setCarId(car.getId());
        log.info(String.format(String.format("CarDriver moved car %d from %d|%d to %d|%d", car.getId(), from.getPositionX(), from.getPositionY(), to.getPositionX(), to.getPositionY())));
    }

    private void releaseRoxel(RoxelTuple from, RoxelTuple to){
        RoxelTuple[] updateRoxels = {from,to};
        gigaSpace.writeMultiple(updateRoxels);
        updateLocation(new MapLocation(to.getPositionX(), to.getPositionY()));
    }

    private void updateLocation(MapLocation location) {
        SQLQuery<CarPositionUpdateTuple> idQuery = new SQLQuery<>(CarPositionUpdateTuple.class,"carId = ?");
        idQuery.setParameter(1,car.getId());
        gigaSpace.change(idQuery, new ChangeSet().increment("logicalTimeStamp", 1L)
                .set("location", location));
    }

    public void removeCar() {
        setCar(null);
    }
}
