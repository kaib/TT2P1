package configurator;

import configurator.factories.CarTupleFactory;
import configurator.factories.RoxelTupleFactory;
import interfaces.CarTuple;
import org.openspaces.core.GigaSpace;
import others.Direction;
import tuples.RoxelTuple;
import tuples.config.ConfigurationTupel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tobi on 31.03.14.
 */
public class Initializer {

    private int mapSizeX;
    private int mapSizeY;
    private int numberOfCars;
    private int blockSize;
    private int roxelSizeX;
    private int roxelSizeY;
    private GigaSpace gigaspace;


    public Initializer(GigaSpace gigaspace){
        this.gigaspace = gigaspace;
    }

    public int getMapSizeX() {
        return mapSizeX;
    }

    public void setMapSizeX(int mapSizeX) {
        this.mapSizeX = mapSizeX;
    }

    public int getMapSizeY() {
        return mapSizeY;
    }

    public void setMapSizeY(int mapSizeY) {
        this.mapSizeY = mapSizeY;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getRoxelSizeX() {
        return roxelSizeX;
    }

    public void setRoxelSizeX(int roxelSizeX) {
        this.roxelSizeX = roxelSizeX;
    }

    public int getRoxelSizeY() {
        return roxelSizeY;
    }

    public void setRoxelSizeY(int roxelSizeY) {
        this.roxelSizeY = roxelSizeY;
    }

    public boolean isConfigured(){
        ConfigurationTupel config = gigaspace.readIfExists(new ConfigurationTupel());
        return config == null ? false : true;
    }


    /**
     * Führt die Konfiguration aus, falls kein ConfigurationTuple im TupleSpace liegt
     */
    public void runConfigurationIfNecessary(){
        if (!isConfigured()) {
            configure();
            gigaspace.write(new ConfigurationTupel(1, mapSizeX, mapSizeY, numberOfCars, blockSize, roxelSizeX, roxelSizeY));
        }
    }

    private void configure(){
        CarTupleFactory carTupleFactory = new CarTupleFactory();
        List<CarTuple> cars = carTupleFactory.createCarTuples(numberOfCars);
        List<RoxelTuple> roxels = new RoxelTupleFactory().createRoxelTuples(blockSize, mapSizeX, mapSizeY);
        placeCars(cars,roxels);
        cars.add(carTupleFactory.createNoCarTuple());
        gigaspace.writeMultiple(cars.toArray());
        gigaspace.writeMultiple(roxels.toArray());
    }


    /**
     * Platziert alle Autos aus der Liste cars zufällig auf Roxel aus der Liste Roxels
     * @param cars
     * @param roxels
     */
    private void placeCars(List<CarTuple> cars, List<RoxelTuple> roxels){
        List<RoxelTuple> freeRoxels = new ArrayList<>(roxels);
        int streetOnElem = blockSize+1;
        Random rand = new Random();
        for (CarTuple car : cars){
            RoxelTuple currentRoxel = freeRoxels.remove(rand.nextInt(freeRoxels.size()));
            currentRoxel.setCarId(car.getId());
            // Kreuzung
            if (((currentRoxel.getPositionX()%streetOnElem)==0) && ((currentRoxel.getPositionY()%streetOnElem)==0)){
                if (rand.nextBoolean()){
                    car.setDirection(Direction.SOUTH);
                } else {
                    car.setDirection(Direction.EAST);
                }
            //Nord-Süd-Verbindung
            } else if ((currentRoxel.getPositionX()%streetOnElem)==0){
                car.setDirection(Direction.SOUTH);
            //West-Ost-Verbindung
            } else if ((currentRoxel.getPositionY()%streetOnElem)==0){
                car.setDirection(Direction.EAST);
            }
        }
    }
}