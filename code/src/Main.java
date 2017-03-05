import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class Main {

	//refactor: Extract Constant
	public static final DifferentialPilot DP = new DifferentialPilot(Config.WHEEL_DIAMETER, Config.TRACK_WIDTH, Motor.A, Motor.B);
	//rectactor: Extract Field
	private static LineFollowingController lfc;

	public static void main(String[] args) {
		SensorHandler sh = new SensorHandler(Config.LIGHT_INTERVAL);
		SensorHandler uh = new SensorHandler(Config.ULTRA_INTERVAL);
		UpdatingUltrasonicSensor uus = new UpdatingUltrasonicSensor();
		UpdatingLightSensor uls = new UpdatingLightSensor();
		UpdatingColorSensor ucs = new UpdatingColorSensor();

		DifferentialPilot DP = DP;

		lfc = new LineFollowingController(DP, ucs, uls);
		ObstacleAvoidanceController oac = new ObstacleAvoidanceController(DP, lfc, uus, ucs, uls);

		
		CalibrationController cc = new CalibrationController();
	 	cc.run(ucs, uls);
		
		//Finish setup
		uh.addSensor(uus);
		sh.addSensor(uls);
		sh.addSensor(ucs);
		
		sh.start();
		uh.start();
		
		DP.setRotateSpeed(Config.MAX_SPEED/2);
		//dp.forward();
		Motor.A.forward();
		Motor.B.forward();
		
		Button.ENTER.waitForPressAndRelease();
		
	}

}
