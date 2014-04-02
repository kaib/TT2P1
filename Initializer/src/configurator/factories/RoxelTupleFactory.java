package configurator.factories;

import configurator.IdGenerator;
import tuples.RoxelTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tobi on 02.04.14.
 */
public class RoxelTupleFactory {

    public RoxelTupleFactory() {
    }

    public List<RoxelTuple> createRoxelTuples(int blockSize, int mapSizeX, int mapSizeY) {
        List<RoxelTuple> roxels = new ArrayList<>();
        int streetOnElem = blockSize+1;
        for (int x = 0; x<= mapSizeX; x++){
            if ((x%streetOnElem)==0){
                for (int y = 0; y<= mapSizeY; y++){
                    if ((y%streetOnElem)==0){
                        roxels.add(createRoxelTuple(x,y));
                    }
                }
            }

        }
        return roxels;
    }

    public RoxelTuple createRoxelTuple(int positionX, int positionY){
        return new RoxelTuple(IdGenerator.getNewID(),positionX,positionY,-1);
    }
}
