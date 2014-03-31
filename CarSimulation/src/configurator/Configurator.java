package configurator;

import interfaces.Car;
import org.openspaces.core.GigaSpace;
import others.ConfigurationStatus;
import others.Direction;
import tuples.CarTupel;
import tuples.RoxelTupel;
import tuples.config.ConfigStatusTupel;
import tuples.config.ConfigurationTupel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tobi on 31.03.14.
 */
public class Configurator {

    private int mapSizeX;
    private int mapSizeY;
    private int numberOfCars;
    private int blockSize;
    private int roxelSizeX;
    private int roxelSizeY;
    private GigaSpace gigaspace;


    public Configurator(GigaSpace gigaspace){
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

    public ConfigurationStatus getConfigurationStatus(){
        ConfigStatusTupel cst = gigaspace.read(new ConfigStatusTupel(), 0);
        return cst.getConfigurationStatus();
    }

    public void runConfigurationIfNecessary(){
        ConfigurationTupel ct = gigaspace.read(new ConfigurationTupel());
        if (ct == null && gigaspace.read(new ConfigStatusTupel(), 0) == null){
            gigaspace.write(new ConfigStatusTupel(ConfigurationStatus.REQUIRED));
        }
        ConfigStatusTupel cst = gigaspace.take(new ConfigStatusTupel(), 0);

        if (ConfigurationStatus.REQUIRED.equals(cst.getConfigurationStatus()))
        {
            gigaspace.write(new ConfigStatusTupel(ConfigurationStatus.IN_PROGRESS));
            // configurate

            gigaspace.write(new ConfigurationTupel(0, mapSizeX, mapSizeY, numberOfCars, blockSize, roxelSizeX, roxelSizeY));
            gigaspace.take(new ConfigStatusTupel(), 0);
            gigaspace.write(new ConfigStatusTupel(ConfigurationStatus.DONE));

        } else {
            gigaspace.write(cst);
        }
    }

    private void configure(){
        List<CarTupel> cars =createCars();
        List<RoxelTupel> roxels = createRoxels();
        placeCars(cars,roxels);
        gigaspace.writeMultiple(cars.toArray());
        gigaspace.writeMultiple(roxels.toArray());
    }

    private List<RoxelTupel> createRoxels() {
        List<RoxelTupel> roxels = new ArrayList<>();
        int roxelID=0;
        int streetOnElem = blockSize+1;
        for (int x = 0; x<= mapSizeX; x++){
            if ((x%streetOnElem)==0){
                for (int y = 0; y<= mapSizeY; y++){
                    if ((y%streetOnElem)==0){
                        roxels.add(new RoxelTupel(roxelID++,x,y,-1));
                    }
                }
            }

        }
        return roxels;
    }

    private List<CarTupel> createCars(){
        List<CarTupel> cars = new ArrayList<>();
        for (int i = 0; i<=numberOfCars; i++){
            cars.add(new CarTupel(i,Direction.TODECIDE));
        }
        return cars;
    }

    private void placeCars(List<CarTupel> cars, List<RoxelTupel> roxels){
        List<RoxelTupel> freeRoxels = new ArrayList<>(roxels);
        int streetOnElem = blockSize+1;
        Random rand = new Random();
        for (CarTupel car : cars){
            RoxelTupel currentRoxel = freeRoxels.remove(rand.nextInt(freeRoxels.size()));
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
