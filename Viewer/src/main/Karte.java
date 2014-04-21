package main;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
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
        this.configurationTupel= gigaSpace.read(new ConfigurationTupel(), NO_TIMEOUT);
        System.out.println(configurationTupel);
        this.streetPartFactory = new StreetPartFactory(new Location(configurationTupel.getMapSizeX(), configurationTupel.getMapSizeY()),configurationTupel.getBlockSize(),configurationTupel.getRoxels());
       gameGrid =
                new GameGrid(configurationTupel.getMapSizeX(), configurationTupel.getMapSizeY(), 30, "/../media/city_above.png");

        for(StreetPart streetPart : streetPartFactory.getStreetPartList()) {
            gameGrid.addActor(streetPart, streetPart.getLocation());
        }

        System.out.println("test");
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
        streetPartsUpdates = gigaSpace.readMultiple(new StreetPartUpdateTuple());

        for(StreetPartUpdateTuple updateTuple : streetPartsUpdates) {
            log.info(String.format("Changing Roxel %s at Position %d|%d to %s", updateTuple.getId(), updateTuple.getLocation().getX(), updateTuple.getLocation().getY(), updateTuple.getDirection()));
            String image = "/../media/street-kreutz.png";
            switch (updateTuple.getDirection()){
                case NORTH:
                case SOUTH:
                    image = "/../media/street-kreutz_South.png";
                    break;
                case EAST:
                case WEST:
                    image = "/../media/street-kreutz_EAST.png";
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
        List<Car> actCarActors = getCarUpdates();
        for (Car car : actCarActors) {
            carActors.put(car.getId(), car);
        }

        List<StreetPart> actStretPartActors = getStreetPartUpdates();
        for (StreetPart streetPart : actStretPartActors){
            streetPartActors.put(streetPart.getId(),streetPart);
        }

        updateActors();
        /*gameGrid.removeActors(main.Car.class);
        for(main.Car car : carActors.values()) {
            System.out.println("Add main.Car: " + car);
            gameGrid.addActor(car,car.getOurLocation());
        }*/
    }

    private void updateActors(){
//        List<Actor> currentKarteStreetPartActors = gameGrid.getActors(StreetPart.class);
//        List<Integer> knownStreetPartActors = new LinkedList<>();
//
//
//
//        //update known street Part actors
//        for(Actor actor : currentKarteStreetPartActors) {
//            StreetPart streetPart = (StreetPart) actor;
//            gameGrid.removeActorsAt(actor.getLocation(),StreetPart.class);
//            gameGrid.addActor(streetPart, actor.getLocation());
//        }


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