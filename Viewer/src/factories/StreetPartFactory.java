package factories;

import ch.aplu.jgamegrid.Location;
import main.StreetPart;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dude on 03.04.2014.
 */
public class StreetPartFactory {
    Location boardSize;
    List<StreetPart> streetPartList;
    int blockSize = -1;

    public StreetPartFactory(Location boardSize, int blockSize) {
        this.boardSize = boardSize;
        this.blockSize = blockSize;
    }

    public List<StreetPart> getStreetPartList() {
       if(streetPartList == null){
           streetPartList = createStreetParts();
       }
        return streetPartList;
    }

    private List<StreetPart> createStreetParts(){
        List<StreetPart> result = new LinkedList<>();
        for(int x = 0; x < boardSize.getX(); x++) {
            for(int y = 0; y < boardSize.getY(); y++) {
                if((x%(blockSize+1) == 0) && (y%(blockSize+1) == 0)) {
                    result.add(new StreetPart(new Location(x,y),"/../media/street-kreutz.png"));
                }
                else if((x%(blockSize+1) != 0) && (y%(blockSize+1) == 0)) {
                    result.add(new StreetPart(new Location(x,y),"/../media/street-east.png"));
                }
                else if((x%(blockSize+1) == 0) && (y%(blockSize+1) != 0)) {
                    result.add(new StreetPart(new Location(x,y),"/../media/street-south.png"));
                }
            }
        }
        return result;
    }

}
