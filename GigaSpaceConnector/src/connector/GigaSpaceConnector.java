package connector;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

/**
 * Created by tobi on 31.03.14.
 */
public class GigaSpaceConnector {

    private static GigaSpace gigaSpace;


    /**
     * Liefert eine Instanz des gigaSpaces für die Applikation zurück
     * @return
     */
    public static GigaSpace getGigaSpace(){
        if (gigaSpace == null){
            UrlSpaceConfigurer configurer = new UrlSpaceConfigurer("jini://*/*/carSimulation");
            gigaSpace = new GigaSpaceConfigurer(configurer).gigaSpace();
        }
        return gigaSpace;
    }
}
