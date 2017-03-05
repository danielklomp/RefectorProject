import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;


public class Config {
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: Holds constant final variables for basic control across all classes 
	 */
	
	public static final SensorPort LightSensor = SensorPort.S4; ///< The port for the lightsensor
	public static final SensorPort UltrasonicSensor = SensorPort.S3; ///< The port for the ultrasonic sensor
	public static final SensorPort ColorSensor = SensorPort.S2; ///< The port for the colorsensor
	public static final SensorPort TouchSensor = SensorPort.S1; ///< The port for the touchsensor
	public static final NXTRegulatedMotor MotorRight = Motor.A; ///< The right motor
	public static final NXTRegulatedMotor MotorLeft = Motor.B; ///< The left motor
	 
	//Alles is in centimeters
	public static final float WHEEL_DIAMETER = 5.6f;
	public static final float TRACK_WIDTH = 9.5f; //10.5f; ///< Distance between middle of left wheel and middle of right wheel
	
	//last known working combination: 400, 2f, 5, 60
	public static final int MAX_SPEED = 400; //< The maximum speed of the robot
	public static final float ACCEL_INC = 10; ///< The corner Acceleration speed
	public static final int SPEED_INCREASE = 15; ///< Wheel speed increase for linefollowing 
	public static final int SPEED_DECREASE = 1; ///< Temp
	public static final int WHITE_BLACK_BORDER = 60; ///< Difference value between a black and a white value 
	
	public static final int DISTANCE = 20; ///< The distance at which the obstacledetector detects a obstacle
	public static final int AVOID_DIST = 30; ///< Distance where the robot sees a obstacle
	public static final int SECOND_AVOID_DIST = 40; ///< Second distance, used when the robot checks for obstacles in the second routine
	public static final int ROTATE_DIFF = 8; ///< Angle at which the robots rotates to avoid obstacles
	public static final int FINAL_ROTATE = 12; ///< Last rotate to make the robot not collide with the wheels
	public static final int TRAVEL_DISTANCE = 40; //< Distance the robot travels to avoid obstacles
	
	public static final int LIGHT_INTERVAL = 25; ///< Interval between loops for lightsensor updating
	public static final int ULTRA_INTERVAL = 75; ///< Interval between loops for ultrasonic sensor updating
}

