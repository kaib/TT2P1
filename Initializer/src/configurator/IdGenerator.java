package configurator;

/**
 * Created by tobi on 02.04.14.
 */
public class IdGenerator {

    private static int currentID = 1;

    public static synchronized Integer getNewID(){
        return currentID++;
    }
}
