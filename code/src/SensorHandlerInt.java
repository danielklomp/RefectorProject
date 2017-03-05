/**
 * Created by Daniel on 24-2-2017.
 */

//refactor Extract interface
public interface SensorHandlerInt extends Runnable {
    void run();

    void addSensor(UpdatingSensor sensor);
}
