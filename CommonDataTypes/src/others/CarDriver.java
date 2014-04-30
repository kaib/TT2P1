package others;

import com.gigaspaces.client.ChangeSet;
import com.j_spaces.core.client.SQLQuery;
import interfaces.CarTuple;
import org.openspaces.core.GigaSpace;
import tuples.CarPositionUpdateTuple;
import tuples.NoCarTuple;
import tuples.RoxelTuple;
import tuples.config.ConfigurationTupel;

import java.util.logging.Logger;

import static others.Direction.*;
import static others.GlobalConstances.NO_TIMEOUT;

/**
 * Created by tobi on 08.04.14.
 */
public class CarDriver {

    private static final Logger log = Logger.getLogger( CarDriver.class.getName() );

    private GigaSpace gigaSpace;
    private RoxelTuple currentPosition;
    private ConfigurationTupel configurationTupel;
    public CarDriver(GigaSpace gigaSpace, ConfigurationTupel configurationTupel) {
        this.gigaSpace = gigaSpace;
        this.configurationTupel = configurationTupel;
    }

    public RoxelTuple getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(RoxelTuple currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void move() {
        move(currentPosition.getCar().getDirection());
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
            case TODECIDE:
                doNotDrive();
                break;
        }
    }
    private void doNotDrive(){
        System.out.println("Cannot Drive with Empty Car Tuple?!?!");

    }

    private void driveNorth() {
        int currentY = currentPosition.getPositionY();
        int nextY = currentY != 0 ? currentY-1 : configurationTupel.getMapSizeY()-1;
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"car.id = ? AND positionY = ? AND positionX = ? AND direction = ?",-1,nextY,currentPosition.getPositionX(), NORTH);
        RoxelTuple next = takeNextRoxel(sql);
        changePosition(currentPosition,next);
        releaseRoxel(currentPosition,next);
    }

    private void driveSouth() {
        int currentY = currentPosition.getPositionY();
        int nextY = currentY != configurationTupel.getMapSizeY()-1 ? currentY+1 : 0;
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"car.id = ? AND positionY = ? AND positionX = ? AND direction = ?",-1,nextY,currentPosition.getPositionX(), SOUTH);
        RoxelTuple next = takeNextRoxel(sql);
        changePosition(currentPosition,next);

        releaseRoxel(currentPosition,next);
    }

    private void driveWest() {
     
        int currentX = currentPosition.getPositionX();
        int nextX = currentX != 0 ? currentX-1 : configurationTupel.getMapSizeY()-1;
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"car.id = ? AND positionY = ? AND positionX = ? AND direction = ?",-1,currentPosition.getPositionY(),nextX, WEST);
        RoxelTuple next = takeNextRoxel(sql);
        changePosition(currentPosition,next);
        releaseRoxel(currentPosition,next);
    }

    private void driveEast() {

        int currentX = currentPosition.getPositionX();
        int nextX = currentX != configurationTupel.getMapSizeY()-1 ? currentX+1 : 0;
        SQLQuery<RoxelTuple> sql = new SQLQuery<>(RoxelTuple.class,"car.id = ? AND positionY = ? AND positionX = ? AND direction = ?",-1,currentPosition.getPositionY(),nextX, EAST);
        RoxelTuple next = takeNextRoxel(sql);
        changePosition(currentPosition,next);
        releaseRoxel(currentPosition,next);
    }

    private RoxelTuple takeNextRoxel(SQLQuery<RoxelTuple> sql){
        return gigaSpace.take(sql,NO_TIMEOUT);
    }

    private void changePosition(RoxelTuple from, RoxelTuple to) {
        if (to.getDirection() == from.getCar().getDirection()) {
            to.setCar(from.getCar());
            from.setCar(new NoCarTuple());
            log.info(String.format(String.format("CarDriver moved car %d from [%d,%d] to [%d,%d]", to.getCar().getId(), from.getPositionX(), from.getPositionY(), to.getPositionX(), to.getPositionY())));
        }
    }

    private void releaseRoxel(RoxelTuple from, RoxelTuple to){
        if (from.isCrossroad()){
            from.setDirection(TODECIDE);
        }
        //
       // RoxelTuple[] updateRoxels = {from,to};
       // gigaSpace.writeMultiple(updateRoxels);
        gigaSpace.write(from);
        this.setCurrentPosition(to);
        updateLocation(new MapLocation(to.getPositionX(), to.getPositionY()));
    }

    private void updateLocation(MapLocation location) {
        SQLQuery<CarPositionUpdateTuple> idQuery = new SQLQuery<>(CarPositionUpdateTuple.class,"carId = ?");
        idQuery.setParameter(1,currentPosition.getCar().getId());
        gigaSpace.change(idQuery, new ChangeSet().increment("logicalTimeStamp", 1L)
                .set("location", location));
    }

    public void removeDriver() {

        currentPosition = null;

    }
}
