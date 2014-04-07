package main;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

/**
 * Created by dude on 03.04.2014.
 */
public class Car extends Actor {
    private int id;
    private Location ourLocation;
    public Car(int id, Location location)
    {
        super("media/carfront.png");
        this.ourLocation = location;
        this.id = id;
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
