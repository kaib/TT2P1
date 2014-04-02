package configurator.factories;

import others.IdGenerator;
import tuples.RoxelTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tobi on 02.04.14.
 */
public class RoxelTupleFactory {

    public RoxelTupleFactory() {
    }

    /**
     * Straßennetz anhand der Map-Größe und Häuser-Block-Größe erzeugen
     * @param blockSize
     * @param mapSizeX
     * @param mapSizeY
     * @return
     */
    public List<RoxelTuple> createRoxelTuples(int blockSize, int mapSizeX, int mapSizeY) {
        List<RoxelTuple> roxels = new ArrayList<>();
        int roxelDistance = blockSize+1;
        for (int x = 0; x < mapSizeX; x++) {
            for (int y = 0; y < mapSizeY; y++) {
                if (x%roxelDistance==0 || y%roxelDistance==0){
                    roxels.add(createRoxelTuple(x,y));
                }
            }
        }
        return roxels;
    }

    public RoxelTuple createRoxelTuple(int positionX, int positionY){
        return new RoxelTuple(IdGenerator.getNewID(),positionX,positionY,-1);
    }
}
