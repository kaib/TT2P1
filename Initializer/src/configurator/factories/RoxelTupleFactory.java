package configurator.factories;

import others.Direction;
import others.IdGenerator;
import tuples.NoCarTuple;
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

    public RoxelTuple createRoxelTuple(Integer positionX, Integer positionY, Direction direction, Boolean isCrossing, Integer routing){
        return new RoxelTuple(IdGenerator.getNewID(),positionX,positionY,new NoCarTuple(), direction, isCrossing, routing);
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
        int shardCountX = 0;
        int shardCountY = 0;
        for (int x = 0; x < mapSizeX; x++) {
            if ((x+2)%5==0){shardCountX=shardCountY+1;}
            shardCountY = shardCountX;
            for (int y = 0; y < mapSizeY; y++) {

                if((y+2)%5==0){shardCountY++;}
                if ((x % (blockSize + 1) == 0) && (y % (blockSize + 1) == 0)) {
                    //Kreuzung
                    result.add(createRoxelTuple(x,y,Direction.TODECIDE, Boolean.TRUE,shardCountY));
                } else if ((x % (blockSize + 1) != 0) && (y % (blockSize + 1) == 0)) {
                    result.add(createRoxelTuple(x,y,Direction.EAST, Boolean.FALSE,shardCountY));
                } else if ((x % (blockSize + 1) == 0) && (y % (blockSize + 1) != 0)) {
                    result.add(createRoxelTuple(x,y,Direction.SOUTH,Boolean.FALSE,shardCountY));
                }

            }
        }
        return result;
    }

}
