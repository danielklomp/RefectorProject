import lejos.nxt.Motor;

import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

public class LineFollowingController implements SensorListener{
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: Controlling motor speeds to follow a line
	 */
	
	private UpdatingColorSensor colorSensor; ///< ColorSensor, detects right side of the line.
	private UpdatingLightSensor lightSensor; ///< LightSensor, detects left side of the line.
	
	private boolean active; ///< State of lineFollowing controller when not active the Object avoidance controller is in control.
	private boolean oldLeftBlack;
	private boolean newLeftBlack; ///<  old and new left values for the left sensor (LightSensor).
	private boolean oldRightBlack, newRightBlack; ///< Stores old and new right (ColorSensor) values for the left sensor.
	private float accelLeft, accelRight, accel_inc; ///< Stores corner acceleration for left and right wheel and the acceleration increase.
	
	private final int whiteBlackBorder = Config.WHITE_BLACK_BORDER; ///< The value that determines if the sensor value is black or white
	private int max_speed = Config.MAX_SPEED; ///< The maximum speed the motors can turn 
	private int right_speed = max_speed/4; ///< Starting speed right wheel is 1/4 of maximum speed
	private int left_speed = max_speed/4; ///< Starting speed left wheel is 1/4 of maximum speed
	private int speed_increase = Config.SPEED_INCREASE; ///< Increase of speed when cornering (without acceleration)
	private int speed_decrease = Config.SPEED_DECREASE; ///< Not is use speed decrease
	private NXTRegulatedMotor motorRight = Config.MotorRight; ///< the right motor
	private NXTRegulatedMotor motorLeft = Config.MotorLeft; /// the left motor
	
	
	/**
	 * Constructor for LineFollowingController
	 * @param pilot The differential pilot used for controlling the device
	 * @param colorSensor The UpdatingColorSensor used to detect the line
	 * @param lightSensor The UpdatingLightSensor used to detect the line
	 */
	public LineFollowingController(
			DifferentialPilot pilot, 
			UpdatingColorSensor colorSensor, 
			UpdatingLightSensor lightSensor) {
		
		this.accelLeft = 1;
		this.accelRight = 1;

		this.accel_inc = Config.ACCEL_INC;
		this.oldLeftBlack = false;
		this.oldRightBlack = false;
		this.newLeftBlack = false;
		this.newRightBlack = false;

		
		this.active = true;
		this.lightSensor = lightSensor;
		this.lightSensor.addListener(this);
		this.colorSensor = colorSensor;
		this.colorSensor.addListener(this);
		this.colorSensor.setFloodlight(true);
	}

	/**
	 * Inherited from SensorListener. The method is run when a sensor has a new value
	 * @param sensor The sensor that has been updated
	 * @param oldValue The value before it changed
	 * @param newValue The value after the change
	 */
	public void stateChanged(UpdatingSensor sensor, float oldValue, float newValue) {
		if(this.active) {
			if (sensor == colorSensor) {
				if(isBlack(newValue)) {
					newLeftBlack =true;
					left_speed += speed_increase;
					left_speed *= accelLeft;
					if(left_speed>max_speed){
						left_speed = max_speed; 
						if(!newRightBlack){right_speed = 0;}
					}
					
					if(isBlack(oldValue)){
						accelLeft += accel_inc;
					}
				}
				else {
					newLeftBlack =false;
					accelLeft = 1;

					//refactor Inline
					boolean result;
					if(oldLeftBlack && (oldRightBlack && (newLeftBlack && newRightBlack))){
						result = true;
                    }else{
						result = false;
                    }

					if(!result){
						right_speed = max_speed/2;
						left_speed = max_speed/2;
					}
					oldLeftBlack = newLeftBlack;
				}
			}
			
			if (sensor == lightSensor) {
				if(isBlack(newValue)) {
					newRightBlack =true;
					right_speed += speed_increase;
					right_speed *= accelRight;
					
					if(right_speed > max_speed){
						right_speed = max_speed;
						if(!newLeftBlack){left_speed = 0;}
					}
					
					if(isBlack(oldValue)){
						accelRight += accel_inc;
					}
				}
				else {
					newRightBlack =false;
					accelRight = 1;

					//refactor Inline
					boolean result;
					if(oldLeftBlack && (oldRightBlack && (newLeftBlack && newRightBlack))){
						result = true;
                    }else{
						result = false;
                    }

					if(!result){
						right_speed = max_speed/2;
						left_speed = max_speed/2;
					}
					oldRightBlack = newRightBlack;
				}
			}
			
			if(newLeftBlack && newRightBlack){
				right_speed = max_speed/8;
				left_speed = max_speed/8;
			}
			
			
		
			motorRight.setSpeed(right_speed);
			motorLeft.setSpeed(left_speed);
			motorLeft.forward();
			motorRight.forward();
		}
	}
	
	/**
	 * Returns the color corresponding with the value from the sensor
	 * @param value The value from the sensor
	 * @returns true for black, or false for white 
	 */
	public boolean isBlack(float value) {
		if (value < whiteBlackBorder) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Removes the permission to do its task
	 */
	public void revokeControl() {
		this.active = false;
	}
	
	/**
	 * Grants the controller permission to do its task
	 */
	public void grantControl() {
		this.active = true;
	}

	

}
