import ch.aplu.jgamegrid.*;
import connector.GigaSpaceConnector;
import org.openspaces.core.GigaSpace;
import tuples.config.ConfigurationTupel;
import static others.GlobalConstances.*;
import java.awt.Color;

public class Karte
{

    private GigaSpace gigaSpace;
    private ConfigurationTupel configurationTupel;
    private StreetPartFactory streetPartFactory;
    public Karte()
    {
        this.gigaSpace = GigaSpaceConnector.getGigaSpace();
        this.configurationTupel= gigaSpace.read(new ConfigurationTupel(), NO_TIMEOUT);
        this.streetPartFactory = new StreetPartFactory(new Location(configurationTupel.getMapSizeX(), configurationTupel.getMapSizeY()),configurationTupel.getBlockSize());
        GameGrid gg =
                new GameGrid(configurationTupel.getMapSizeX(), configurationTupel.getMapSizeY(), 30, Color.red);
        gg.addActor(new Car(), new Location(2, 4));

        for(StreetPart streetPart : streetPartFactory.getStreetPartList()) {
            gg.addActor(streetPart,streetPart.getLocation());
        }
        System.out.println("test");
        gg.show();




    }

    public static void main(String[] args)
    {
        new Karte();
    }
}