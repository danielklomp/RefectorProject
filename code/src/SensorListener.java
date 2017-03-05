
public interface SensorListener {
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: Gives classes that implements this interface the possibility to get updates from different sensors.
	 */
	
	/**
	 * This method is run when a sensor has a new value. DO NOT BLOCK THE THREAD or all sensors will wait with updating.
	 * @param sensor The sensor that has been updated
	 * @param oldValue The value before it changed
	 * @param newValue The value after the change
	 */
	public void stateChanged(UpdatingSensor sensor, float oldValue, float newValue);
}
