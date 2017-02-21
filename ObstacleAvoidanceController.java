import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;


public class ObstacleAvoidanceController implements SensorListener{
	
	/**
	 * @author Alexander Freeman, Jop van Buuren, Marc Kuijpers en Daniel Klomp
	 * @version 1.00
	 * Responsibility: Controls motors during the avoidence of objects.
	 */
	
	private UpdatingUltrasonicSensor sonicSensor;
	private LineFollowingController lineFollower;
	
	private DifferentialPilot pilot;  ///< The main pilot we use to control the device
	private boolean notSeenBlack; ///< A flag we use to check if the sensor has been black after certain moments.
	private boolean active; ///< If the controller is currently controlling the device
	private UpdatingLightSensor lightSensor; ///< The UpdatingLightSensor we use
	private UpdatingColorSensor colorSensor; ///< The UpdatingColorSensor we use
	
	/**
	 * Constructor for ObstacleAvoidanceController
	 * @param pilot The differential pilot used for controlling the device
	 * @param LineFollowingController The LineFollowingController used to detect the line
	 * @param UpdatingUltraSonicSensor The UpdatingUltraSonicSensor used to detect obstacles
	 */
	public ObstacleAvoidanceController(
			DifferentialPilot pilot, 
			LineFollowingController lineFollower, 
			UpdatingUltrasonicSensor sonicSensor,
			UpdatingColorSensor colorSensor, 
			UpdatingLightSensor lightSensor) {
		
		this.notSeenBlack = true;
		
		this.lineFollower = lineFollower;
		this.pilot = pilot;
		this.active = false;
		this.sonicSensor = sonicSensor;
		this.sonicSensor.addListener(this);
		this.lightSensor = lightSensor;
		this.lightSensor.addListener(this);
		this.colorSensor = colorSensor;
		this.colorSensor.addListener(this);
	}
	
	/**
	 * Inherited from SensorListener. The method is run when a sensor has a new value
	 * @param sensor The sensor that has been updated
	 * @param oldValue The value before it changed
	 * @param newValue The value after the change
	 */
	public void stateChanged(UpdatingSensor s, float oldValue, float newValue) {
		
		//If the new value comes from the Ultrasonic Sensor
		if (s == this.sonicSensor) {
			
			//And the distance is below our threshold AND it is not active
			if (newValue < Config.DISTANCE && !this.active) {
				//pilot.stop();
				
				//Revoke the control from the line follower
				lineFollower.revokeControl();
				this.grantControl();
				
				if(newValue< 5){
					pilot.travel(-5);
				}
				
				this.avoid(newValue);
			}
		}
		else {
			if (isBlack(newValue)) {
				notSeenBlack = false;
			}
		}
	}
	
	public boolean isBlack(float value) {
		if (value < Config.WHITE_BLACK_BORDER) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * Grants the controller permission to do its task
	 */
	public void grantControl() {
		this.active = true;
	}

	/**
	 * Starts the obstacle avoidance procedure 
	 */
	private void avoid(float ob) {
		
		int object_distance = (int)ob;
		int distance = 1000;
		int rotated = 0;
		int calculated_distance = 0;
		
		
		Sound.setVolume(100);
		Sound.twoBeeps();
		
		do {
			pilot.rotate(-Config.ROTATE_DIFF);
			rotated -= Config.ROTATE_DIFF;
			this.sonicSensor.continuous();
			distance = this.sonicSensor.getDistance();
			//System.out.println(distance);
		}
		while(distance < Config.AVOID_DIST);
		
		rotated -= Config.FINAL_ROTATE;
		pilot.rotate(-Config.FINAL_ROTATE);
		pilot.setTravelSpeed(10);
		Sound.beep();
		
		/*
		calculated_distance = (int) (object_distance / Math.cos(Math.toRadians(rotated)));
		//System.out.println("Calc_d: " + calculated_distance + ", Corner: " + rotated + ", Obj_d: " + object_distance + " Calc_cor: " + Math.cos(rotated));


		pilot.travel(calculated_distance);
		
		pilot.rotate(-2* rotated);
		
		
		do {
			pilot.rotate(Config.ROTATE_DIFF);
			distance = this.sonicSensor.getDistance();
		}
		while(distance < Config.SECOND_AVOID_DIST);
		
		
		pilot.travel(travel_dist);
		Sound.beep();
		pilot.rotate(rotated);
		
		
		
		this.notSeenBlack = true;
		
		pilot.travel(Config.TRAVEL_DISTANCE*2, true);
		
		
		
		
		pilot.stop();
		pilot.travel(5);
		pilot.rotate(10);
		this.revokeControl();
		this.lineFollower.grantControl();
		
		*/
		
		this.notSeenBlack = true;
		
		pilot.arc(30, 50, true);

		while (pilot.isMoving() && notSeenBlack) {
			Thread.yield();
		}
		
		pilot.arc(13, 310, true);
		
		while(notSeenBlack) {
			Thread.yield();
		}
		
		pilot.travel(2);
		pilot.setRotateSpeed(20);
		pilot.rotate(-110);
		
		notSeenBlack = true;
		pilot.rotate(360, true);
		
		while(notSeenBlack) {
			Thread.yield();
		}
		
		pilot.stop();
		
		
		this.revokeControl();
		this.lineFollower.grantControl();
		
	}
	
	
	/**
	 * Sets the controller to inactive
	 */
	private void revokeControl() {
		this.active = false;
	}

}