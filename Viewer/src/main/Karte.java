package main;

import ch.aplu.jgamegrid.*;
import connector.GigaSpaceConnector;
import factories.StreetPartFactory;
import org.openspaces.core.GigaSpace;
import others.CarLocation;
import tuples.CarPositionUpdateTuple;
import tuples.config.ConfigurationTupel;
import static others.GlobalConstances.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Karte
{

    private GigaSpace gigaSpace;
    private ConfigurationTupel configurationTupel;
    private StreetPartFactory streetPartFactory;
    private GameGrid gameGrid;
    private Map<Integer,Car> carActors = new HashMap<>();

    public Karte(){
        this.gigaSpace = GigaSpaceConnector.getGigaSpace();
        this.configurationTupel= gigaSpace.read(new ConfigurationTupel(), NO_TIMEOUT);
        System.out.println(configurationTupel.getBlockSize());
        this.streetPartFactory = new StreetPartFactory(new Location(configurationTupel.getMapSizeX(), configurationTupel.getMapSizeY()),configurationTupel.getBlockSize());
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
            System.out.println("Moving: " + updateTuple.getCarId() + " to: " +updateTuple.getLocation().getX() + "/" + updateTuple.getLocation().getY() );
            result.add(new Car(updateTuple.getCarId(), mapLocations(updateTuple.getLocation())));
        }
        return result;
    }

    public void doStep(){
       List<Car> actCarActors = getCarUpdates();
        for(Car car : actCarActors) {
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

    private Location mapLocations(CarLocation carLocation) {
        return new Location(carLocation.getX(),carLocation.getY());
    }

    public static void main(String[] args)
    {
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