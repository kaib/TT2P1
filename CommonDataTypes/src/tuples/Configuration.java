package tuples;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Created by tobi on 31.03.14.
 */
public class Configuration implements interfaces.Configuration {

    private int mapSizeX;
    private int mapSizeY;
    private int numberOfCars;
    private int blockSize;
    private int roxelSizeX;
    private int roxelSizeY;
    private boolean alreadyConfigured;

    private int id;

    public Configuration(){};

    public Configuration(int id,int mapSizeX, int mapSizeY, int numberOfCars, int blockSize, int roxelSizeX, int roxelSizeY) {
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        this.numberOfCars = numberOfCars;
        this.blockSize = blockSize;
        this.roxelSizeX = roxelSizeX;
        this.roxelSizeY = roxelSizeY;
        this.alreadyConfigured = false;
        this.id = id;
    }

    @Override
    public boolean isAlreadyConfigured() {
        return alreadyConfigured;
    }

    @Override
    public void setAlreadyConfigured(boolean alreadyConfigured) {
        this.alreadyConfigured = alreadyConfigured;
    }

    @Override
    public int getMapSizeX() {
        return mapSizeX;
    }

    @Override
    public void setMapSizeX(int mapSizeX) {
        this.mapSizeX = mapSizeX;
    }

    @Override
    public int getMapSizeY() {
        return mapSizeY;
    }

    @Override
    public void setMapSizeY(int mapSizeY) {
        this.mapSizeY = mapSizeY;
    }

    @Override
    public int getNumberOfCars() {
        return numberOfCars;
    }

    @Override
    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }

    @Override
    public int getBlockSize() {
        return blockSize;
    }

    @Override
    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    @Override
    public int getRoxelSizeX() {
        return roxelSizeX;
    }

    @Override
    public void setRoxelSizeX(int roxelSizeX) {
        this.roxelSizeX = roxelSizeX;
    }

    @Override
    public int getRoxelSizeY() {
        return roxelSizeY;
    }

    @Override
    public void setRoxelSizeY(int roxelSizeY) {
        this.roxelSizeY = roxelSizeY;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    @SpaceId
    public void setId(int id) {
        this.id = id;
    }
}
