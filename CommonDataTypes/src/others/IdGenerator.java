package others;

/**
 * Created by tobi on 02.04.14.
 */
public class IdGenerator {

    private static int currentID = 1;

    /**
     * Erzeugt eindeutige IDs
     * ACHTUNG: IDs sind eindeutig pro Gestarteter Applications-Instanz, nicht pro TupleSpace.
     *  Bisher war dies ausreichend, da alle Tuple eines Typs von der gleichen Instanz erzeugt wurden.
     *  Ggf. muss der ID-Generator erweitert werden.
     * @return
     */
    public static synchronized Integer getNewID(){
        return currentID++;
    }
}
