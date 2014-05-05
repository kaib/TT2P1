package main;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import com.j_spaces.core.client.SQLQuery;
import connector.GigaSpaceConnector;
import factories.StreetPartFactory;
import org.openspaces.core.GigaSpace;
import others.MapLocation;
import tuples.CarPositionUpdateTuple;
import tuples.StreetPartUpdateTuple;
import tuples.config.ConfigurationTupel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static others.GlobalConstances.NO_TIMEOUT;

public class Karte
{
    private static final Logger log = Logger.getLogger( Karte.class.getName() );
    private GigaSpace gigaSpace;
    private ConfigurationTupel configurationTupel;
    private StreetPartFactory streetPartFactory;
    private GameGrid gameGrid;
    private Map<Integer,Car> carActors = new HashMap<>();
    private Map<Integer, StreetPart> streetPartActors = new HashMap<>();

    public Karte(){
        this.gigaSpace = GigaSpaceConnector.getGigaSpace();
        while(configurationTupel == null) {
            this.configurationTupel = gigaSpace.read(new ConfigurationTupel());
            System.out.println(configurationTupel);
        }
        this.streetPartFactory = new StreetPartFactory(new Location(configurationTupel.getMapSizeX(), configurationTupel.getMapSizeY()),configurationTupel.getBlockSize(),configurationTupel.getRoxels());
       gameGrid =
                new GameGrid(configurationTupel.getMapSizeX(), configurationTupel.getMapSizeY(), 30, "/../media/city_above.png");

        for(StreetPart streetPart : streetPartFactory.getStreetPartList()) {
            gameGrid.addActor(streetPart, streetPart.getLocation());
        }
        gameGrid.show();
    }

    private List<Car> getCarUpdates() {
        List<Car> result = new LinkedList<>();
        CarPositionUpdateTuple[] carUpdates;
        carUpdates = gigaSpace.readMultiple(new CarPositionUpdateTuple());

        for(CarPositionUpdateTuple updateTuple : carUpdates) {
            log.info(String.format("Moving: %d to: %d/%d", updateTuple.getCarId(), updateTuple.getLocation().getX(), updateTuple.getLocation().getY()));
            result.add(new Car(updateTuple.getCarId(), mapLocations(updateTuple.getLocation()), updateTuple.getDirection()));
        }
        return result;
    }


    private List<StreetPart> getStreetPartUpdates(){
        List<StreetPart> result = new LinkedList<>();
        StreetPartUpdateTuple[] streetPartsUpdates;

        SQLQuery<StreetPartUpdateTuple> sql = new SQLQuery<>(StreetPartUpdateTuple.class,"crossroad = ?", Boolean.TRUE);
        streetPartsUpdates = gigaSpace.readMultiple(sql);

        for(StreetPartUpdateTuple updateTuple : streetPartsUpdates) {
            log.info(String.format("Changing Roxel %s at Position %d|%d to %s", updateTuple.getRoxelId(), updateTuple.getLocation().getX(), updateTuple.getLocation().getY(), updateTuple.getDirection()));
            String image = "/../media/street-kreutz.png";
            switch (updateTuple.getDirection()){
                case NORTH:
                case SOUTH:
                    image = "/../media/street-kreutz_South.png";
                    break;
                case EAST:
                case WEST:
                    image = "/../media/street-kreutz_East.png";
                    break;
                case TODECIDE:
                    image = "/../media/street-kreutz_ToDecide.png";
                    break;
                default:
                    image = "/../media/street-kreutz.png";
            }
            result.add(new StreetPart(updateTuple.getRoxelId(),new Location(updateTuple.getLocation().getX(),updateTuple.getLocation().getY()), true, image));
        }
        return result;
    }

    public void doStep(){
        List<StreetPart> streetPartActorsList = getStreetPartUpdates();
        for (StreetPart streetPart : streetPartActorsList){
            if (streetPart.isCrossRoad()) {
                streetPartActors.put(streetPart.getId(), streetPart);
            }
        }

        List<Car> actCarActors = getCarUpdates();
        for (Car car : actCarActors) {
            carActors.put(car.getId(), car);
        }

        updateActors();
        /*gameGrid.removeActors(main.Car.class);
        for(main.Car car : carActors.values()) {
            System.out.println("Add main.Car: " + car);
            gameGrid.addActor(car,car.getOurLocation());
        }*/
    }

    private void updateActors(){
        //update known street Part actors
        for(Map.Entry<Integer,StreetPart> entrySet : streetPartActors.entrySet()) {
               StreetPart streetPart = entrySet.getValue();
            if (streetPart.isCrossRoad()) {
                gameGrid.removeActorsAt(streetPart.getLocation(),StreetPart.class);
                gameGrid.addActor(streetPart, streetPart.getLocation());
            }
        }
        List<Actor> currentKarteActors = gameGrid.getActors(Car.class);
        List<Integer> knownCarActors = new LinkedList<>();

        //update known car actors
        for(Actor actor : currentKarteActors) {
            Car car = (Car) actor;
            car.setOurLocation(carActors.get(car.getId()).getOurLocation());
            car.setLocation(car.getOurLocation());
            knownCarActors.add(car.getId());
        }

        //add unknown carActors to gamegrid
        for(Map.Entry<Integer,Car> car : carActors.entrySet()) {
            if(!(knownCarActors.contains(car.getKey()))) {
                gameGrid.addActor(car.getValue(),car.getValue().getOurLocation());
            }
        }
    }

    private Location mapLocations(MapLocation mapLocation) {
        return new Location(mapLocation.getX(), mapLocation.getY());
    }

    public static void main(String[] args)
    {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        Karte karte = new Karte();
        while(true) {
            karte.doStep();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}