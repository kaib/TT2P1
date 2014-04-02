package simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle CarThreads
 * Created by tobi on 02.04.14.
 */
public class CarPool {

    private List<Thread> carWorkerList;

    public CarPool(int numberOfWorker) {
        carWorkerList = new ArrayList<>();
        for (int i = 0; i < numberOfWorker; i++) {
            Thread carWorkerThread = new CarWorker();
            carWorkerList.add(carWorkerThread);
        }
    }

    public void startAllCars(){
        for (Thread thread : carWorkerList) {
            thread.start();
        }
    }

    public void stopAllCars(){
        for (Thread thread : carWorkerList) {
            thread.interrupt();
        }
    }

    public void createAndStartNewCarWorker(){
        Thread carWorkerThread = new CarWorker();
        carWorkerThread.start();
        carWorkerList.add(carWorkerThread);
    }
}
