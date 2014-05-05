package configurator;

import configurator.factories.CarPositionUpdateTupleFactory;
import configurator.factories.CarTupleFactory;
import configurator.factories.RoxelTupleFactory;
import interfaces.CarTuple;
import org.openspaces.core.GigaSpace;
import tuples.CarPositionUpdateTuple;
import tuples.RoxelTuple;
import tuples.config.ConfigurationTupel;

import java.util.*;

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

        }
    }

    private void configure(){
        CarTupleFactory carTupleFactory = new CarTupleFactory();
        List<CarTuple> cars = carTupleFactory.createCarTuples(numberOfCars);
        RoxelTupleFactory roxelTupleFactory = new RoxelTupleFactory();
        List<RoxelTuple> roxels = roxelTupleFactory.createRoxelTuples(blockSize, mapSizeX, mapSizeY);
        List<RoxelTuple> roxelCarList = placeCars(cars, roxels);
        List<CarPositionUpdateTuple> carPostionUpdates = new CarPositionUpdateTupleFactory().createCarPositionUpdateTuples(roxelCarList);

        for(RoxelTuple roxel:roxels) {
            System.out.println(roxel);
        }
        gigaspace.writeMultiple(roxels.toArray());
        gigaspace.writeMultiple(carPostionUpdates.toArray());

        gigaspace.write(new ConfigurationTupel(1, mapSizeX, mapSizeY, numberOfCars, blockSize, roxelSizeX, roxelSizeY, roxels));
    }




    /**
     * Platziert alle Autos aus der Liste cars zufällig auf Roxel aus der Liste Roxels
     * @param cars asd
     * @param roxels asd
     */
    private List<RoxelTuple> placeCars(List<CarTuple> cars, List<RoxelTuple> roxels){
        List<RoxelTuple> freeRoxels = new ArrayList<>(roxels);
        List<RoxelTuple> result = new LinkedList<>();
        Random rand = new Random();
        for (CarTuple car : cars){
            RoxelTuple currentRoxel = getNonCrossRoadRoxel(freeRoxels, rand);
            //Auto soll in vorgegebene Richtung fahren
            car.setDirection(currentRoxel.getDirection());
            currentRoxel.setCar(car);
            result.add(currentRoxel);
        }
        return result;
    }

    private RoxelTuple getNonCrossRoadRoxel(List<RoxelTuple> freeRoxels, Random rand){
        RoxelTuple result = null;
        boolean foundNonCrossRoadRoxel = false;

        while (!foundNonCrossRoadRoxel) {
            if (!freeRoxels.isEmpty()) {
                result = freeRoxels.remove(rand.nextInt(freeRoxels.size()));
                if (result.getCrossroad()!= null && !result.getCrossroad()){
                    foundNonCrossRoadRoxel = true;
                }
            } else {
                break;
            }
        }
        return result;
    }
}
