import java.util.ArrayList;

import lejos.nxt.I2CPort;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class UpdatingUltrasonicSensor extends UltrasonicSensor implements UpdatingSensor {
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: A boundary Class of a UltraSonicSensor 
	 * that gives Updates to the SensorListener containing his values.
	 */
	

	private ArrayList<SensorListener> listeners;
	private float value; ///< current value
	
	/**
	 * Constructor for UpdatingUltraSonicSensor
	 * Initializes listener ArrayList
	 * Initializes the UltranSonicSensor
	 */
	public UpdatingUltrasonicSensor() {
		super(Config.UltrasonicSensor);
		this.listeners = new ArrayList<SensorListener>();
		this.value = 0;
	}
	
	/**
	 * Adds a listener to the list of listeners
	 * @param sensor
	 */
	public void addListener(SensorListener sensor) {
		this.listeners.add(sensor);
	}

	/** 
	 * Updates the state of the sensor
	 */
	public void updateState() {
		this.ping();
		float now = this.getDistance();
		if (now != value) {
			for (SensorListener s : listeners) {
				s.stateChanged(this, value, now);
			}
		}
		value = now;
	}
}
