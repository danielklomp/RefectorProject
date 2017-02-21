import java.util.ArrayList;

import lejos.nxt.ColorSensor;

public class UpdatingColorSensor extends ColorSensor implements UpdatingSensor {
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: is the boundary class of a colorSensor 
	 * that gives updates to the sensorListener containing his values.
	 */
	
	private int _zero = 1023;
	private int _hundred = 0;
	private float value;
	
	private ArrayList<SensorListener> listeners;
	
	/**
	 * Constructor for UpdatingColorSensor
	 * Initializes listener ArrayList
	 * Initializes the ColorSensor
	 */
	public UpdatingColorSensor() {
		super(Config.ColorSensor);
		value = 0;
		listeners = new ArrayList<SensorListener>();
	}
	
	/**
	 * Adds a listener to the list of listeners
	 * @param sensor
	 */
	public void addListener(SensorListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Sets the value that corresponds with 0%.
	 */
	public void setLow(int low) {
		_zero = low;
	}

	/**
	 * Sets the value that corresponds with 100%
	 */
	public void setHigh(int high) {
		_hundred = high;
	}

	/** Gets a normalized value between the lower value and the upper value set with setHigh and setLow
	 */
	@Override
	public int getLightValue() {
		if(_hundred == _zero) return 0;
		
		int value = super.getRawLightValue();
		
		// Scale the value in de range [_zero.._hundred]
		int t = 100 * (value - _zero) / (_hundred - _zero);
		if(t>100){
			t=100;
		}
		if(t<0){
			t=0;
		}
		return t;
	}

	/** Updates the sensors state
	 * 
	 */
	public void updateState() {
		float now = (float) this.getLightValue();
		if (now != value) {
			for (SensorListener s : listeners) {
				s.stateChanged(this, value, now);
			}
		}
		this.value = now;
	}
	
}

