/**
 * Created by Daniel on 24-2-2017.
 */
public interface SensorHandlerInt extends Runnable {
    void run();

    void addSensor(UpdatingSensor sensor);
}
