import java.util.ArrayList;


public class SensorHandler extends Thread implements SensorHandlerInt {
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: Updates Sensors for every given interval
	*/
	
	private ArrayList<UpdatingSensor> allSensors;
	private int interval;
	
	/**Constructor for SensorHandler
	 * initializes the Sensor ArrayList and loads interval from Config
	 */
	public SensorHandler() {
		allSensors = new ArrayList<UpdatingSensor>();
		this.interval = Config.LIGHT_INTERVAL;
	}
	
	/**Overloading Constructor for SensorHandler
	 * initializes the Sensor ArrayList
	 * @param interval the interval for UltraSonicSensorUpdating
	 */
	public SensorHandler(int interval) {
		this.interval = interval;
		allSensors = new ArrayList<UpdatingSensor>();
	}
	
	/**
	 * Starts the thread and constantly updates the sensors
	 */
	@Override
	public void run() {
		while (true) {
			for (UpdatingSensor s: allSensors) {
				s.updateState();
			}
			try {
				Thread.sleep(this.interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds a sensor to the list of sensors
	 * @param sensor The sensor to be added
	 */
	@Override
	public void addSensor(UpdatingSensor sensor) {
		this.allSensors.add(sensor);
	}
}
