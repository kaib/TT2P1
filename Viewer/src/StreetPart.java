import ch.aplu.jgamegrid.*;

import java.awt.*;

public class StreetPart extends Actor
{
    private Location location;
    public StreetPart(Location location)
    {
        super("media/street-grey.png");
        this.location=location;
    }

    public Location getLocation() {
        return location;
    }
}