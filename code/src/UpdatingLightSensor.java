import java.util.ArrayList;

import lejos.nxt.I2CPort;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class UpdatingLightSensor extends LightSensor implements UpdatingSensor {
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: A boundary Class of a LightSensor 
	 * that gives Updates to the SensorListener containing his values.
	 */
	

	private ArrayList<SensorListener> listeners;
	private float value;
	
	/**
	 * Constructor for UpdatingLightSensor
	 * Initializes listener ArrayList
	 * Initializes LightSensor
	 */
	public UpdatingLightSensor() {
		super(Config.LightSensor);
		this.listeners = new ArrayList<SensorListener>();
		this.value = 0;
	}
	
	/**
	 * Adds a listener to the list of listeners
	 * @param listener The new listener
	 */
	public void addListener(SensorListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Inherited from UpdatingSensor. Updates the state of the sensor
	 */
	public void updateState() {
		float now = this.getLightValue();
		if (now != value) {
			for (SensorListener s : listeners) {
				s.stateChanged(this, value, now);
			}
		}
		value = now;
	}
	
	/** 
	 * Get last known value
	 */
	public float lastValue() {
		return value;
	}
}
