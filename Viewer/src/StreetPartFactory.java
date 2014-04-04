import ch.aplu.jgamegrid.Location;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dude on 03.04.2014.
 */
public class StreetPartFactory {
    Location boardSize;
    List<StreetPart> streetPartList;
    int blockSize;
    public StreetPartFactory(Location boardSize, int blockSize) {
        this.boardSize = boardSize;
        this.streetPartList=createStreetParts();
        this.blockSize = blockSize;
    }

    public List<StreetPart> getStreetPartList() {
       return this.streetPartList;
    }

    private List<StreetPart> createStreetParts(){
        List<StreetPart> result = new LinkedList<>();
        for(int x = 0; x < boardSize.getX(); x+= blockSize +1) {
            for(int y = 0; y < boardSize.getY(); y+= blockSize +1) {
                result.add(new StreetPart(new Location(x,y)));
            }
        }
    }
}
