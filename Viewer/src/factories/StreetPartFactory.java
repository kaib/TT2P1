package factories;

import ch.aplu.jgamegrid.Location;
import main.StreetPart;
import others.Direction;
import tuples.RoxelTuple;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dude on 03.04.2014.
 */
public class StreetPartFactory {
    private Location boardSize;
    private List<StreetPart> streetPartList;
    private int blockSize = -1;
    private List<RoxelTuple> roxels;

    public StreetPartFactory(Location boardSize, int blockSize, List<RoxelTuple> roxels) {
        this.boardSize = boardSize;
        this.blockSize = blockSize;
        this.roxels = roxels;
    }

    public List<StreetPart> getStreetPartList() {
       if(streetPartList == null){
           streetPartList = createStreetParts(roxels);
       }
        return streetPartList;
    }


    private List<StreetPart> createStreetParts(List<RoxelTuple> roxels) {
        List<StreetPart> result = new LinkedList<>();
        for(RoxelTuple roxel:roxels) {
            if(roxel.getDirection() == Direction.EAST) {
                result.add(new StreetPart(new Location(roxel.getPositionX(), roxel.getPositionY()), "/../media/street-east.png"));
            } else if(roxel.getDirection() == Direction.SOUTH) {
                result.add(new StreetPart(new Location(roxel.getPositionX(), roxel.getPositionY()), "/../media/street-south.png"));
            } else if(roxel.isCrossroad()) {
                result.add(new StreetPart(new Location(roxel.getPositionX(), roxel.getPositionY()), "/../media/street-kreutz.png"));
            }
        }
        return result;
    }
}
