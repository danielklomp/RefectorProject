
public interface UpdatingSensor {
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: Gives classes that implement this interface 
	 * a unified way of updating there own values.
	 */
	
	
	/**
	 * Updates the state of the sensor
	 */
	public void updateState();
}
