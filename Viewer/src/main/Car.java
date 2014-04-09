package main;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import others.Direction;

/**
 * Created by dude on 03.04.2014.
 */
public class Car extends Actor {
    private int id;
    private Location ourLocation;
    public Car(int id, Location location, Direction direction)
    {
        super(true, getCarImagePath(direction));
        this.ourLocation = location;
        this.id = id;
    }

    public static String getCarImagePath(Direction direction){
        String result;
        switch (direction){
            case EAST:
                result = "/../media/carEast.png";
                break;
            case WEST:
                result = "/../media/carWest.png";
                break;
            case SOUTH:
                result = "/../media/carSouth.png";
                break;
            case NORTH:
                result = "/../media/carNorth.png";
                break;
            default:
                result = "/../media/carfront.png";
                break;
        }
        return result;
    }

    public int getId(){
        return this.id;
    }

    public Location getOurLocation(){
        return ourLocation;
    }

    public void setOurLocation(Location newLocation){
        this.ourLocation = newLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (id != car.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
