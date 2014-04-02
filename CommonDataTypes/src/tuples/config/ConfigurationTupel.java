package tuples.config;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

/**
 * Created by tobi on 31.03.14.
 */
public class ConfigurationTupel {

    private Integer mapSizeX;
    private Integer mapSizeY;
    private Integer numberOfCars;
    private Integer blockSize;
    private Integer roxelSizeX;
    private Integer roxelSizeY;

    private Integer id;

    public ConfigurationTupel(){};

    public ConfigurationTupel(Integer id, Integer mapSizeX, Integer mapSizeY, Integer numberOfCars, Integer blockSize, Integer roxelSizeX, Integer roxelSizeY) {
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        this.numberOfCars = numberOfCars;
        this.blockSize = blockSize;
        this.roxelSizeX = roxelSizeX;
        this.roxelSizeY = roxelSizeY;
        this.id = id;
    }



    public Integer getMapSizeX() {
        return mapSizeX;
    }


    public void setMapSizeX(Integer mapSizeX) {
        this.mapSizeX = mapSizeX;
    }


    public Integer getMapSizeY() {
        return mapSizeY;
    }


    public void setMapSizeY(Integer mapSizeY) {
        this.mapSizeY = mapSizeY;
    }

    public Integer getNumberOfCars() {
        return numberOfCars;
    }


    public void setNumberOfCars(Integer numberOfCars) {
        this.numberOfCars = numberOfCars;
    }


    public Integer getBlockSize() {
        return blockSize;
    }


    public void setBlockSize(Integer blockSize) {
        this.blockSize = blockSize;
    }


    public Integer getRoxelSizeX() {
        return roxelSizeX;
    }


    public void setRoxelSizeX(Integer roxelSizeX) {
        this.roxelSizeX = roxelSizeX;
    }


    public Integer getRoxelSizeY() {
        return roxelSizeY;
    }


    public void setRoxelSizeY(Integer roxelSizeY) {
        this.roxelSizeY = roxelSizeY;
    }

    @SpaceIndex(type= SpaceIndexType.BASIC)
    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
