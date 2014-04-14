package configurator.factories;

import others.Direction;
import others.IdGenerator;
import tuples.RoxelTuple;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tobi on 02.04.14.
 * Edited by Kai 14.04.14.
 */
public class RoxelTupleFactory {

    public RoxelTupleFactory() {
    }

    /**

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
    }*/


    public RoxelTuple createRoxelTuple(int positionX, int positionY, Direction direction, boolean isCrossing){
        return new RoxelTuple(IdGenerator.getNewID(),positionX,positionY,-1, direction, isCrossing);
    }

    /**
     * New Roxel Tuple Factory with directions!
     * @param blockSize
     * @param mapSizeX
     * @param mapSizeY
     * @return
     */
    public List<RoxelTuple> createRoxelTuples(int blockSize, int mapSizeX, int mapSizeY) {
        List<RoxelTuple> result = new LinkedList<>();
        for (int x = 0; x < mapSizeX; x++) {
            for (int y = 0; y < mapSizeY; y++) {
                if ((x % (blockSize + 1) == 0) && (y % (blockSize + 1) == 0)) {
                    //Kreuzung
                    result.add(createRoxelTuple(x,y,Direction.TODECIDE, true));
                } else if ((x % (blockSize + 1) != 0) && (y % (blockSize + 1) == 0)) {
                    result.add(createRoxelTuple(x,y,Direction.EAST, false));
                } else if ((x % (blockSize + 1) == 0) && (y % (blockSize + 1) != 0)) {
                    result.add(createRoxelTuple(x,y,Direction.SOUTH,false));
                }
            }
        }
        return result;
    }

}
