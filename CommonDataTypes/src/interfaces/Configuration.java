package interfaces;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Created by tobi on 31.03.14.
 */
public interface Configuration {

    int getMapSizeX();

    void setMapSizeX(int mapSizeX);

    int getMapSizeY();

    void setMapSizeY(int mapSizeY);

    int getNumberOfCars();

    void setNumberOfCars(int numberOfCars);

    int getBlockSize();

    void setBlockSize(int blockSize);

    int getRoxelSizeX();

    void setRoxelSizeX(int roxelSizeX);

    int getRoxelSizeY();

    void setRoxelSizeY(int roxelSizeY);

    int getId();

    @SpaceId
    void setId(int id);
}
