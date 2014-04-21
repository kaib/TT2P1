package main;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;



public class StreetPart extends Actor
{
    private Location location;
    private boolean isCrossRoad;
    private int id;
    public StreetPart(int id, Location location, boolean isCrossRoad, String image)
    {
        super(image);
        this.isCrossRoad = isCrossRoad;
        this.location=location;
        this.id = id;
    }

    public StreetPart(int id, Location location)
    {
        super("/../media/street-grey.png");
        this.location=location;
    }


    public boolean isCrossRoad() {
        return isCrossRoad;
    }

    public void setCrossRoad(boolean isCrossRoad) {
        this.isCrossRoad = isCrossRoad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreetPart that = (StreetPart) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}