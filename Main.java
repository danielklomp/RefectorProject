import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class Main {

	public static void main(String[] args) {
		SensorHandler sh = new SensorHandler(Config.LIGHT_INTERVAL);
		SensorHandler uh = new SensorHandler(Config.ULTRA_INTERVAL);
		UpdatingUltrasonicSensor uus = new UpdatingUltrasonicSensor();
		UpdatingLightSensor uls = new UpdatingLightSensor();
		UpdatingColorSensor ucs = new UpdatingColorSensor();
		
		DifferentialPilot dp = new DifferentialPilot(Config.WHEEL_DIAMETER, Config.TRACK_WIDTH, Motor.A, Motor.B);

		LineFollowingController lfc = new LineFollowingController(dp, ucs, uls);
		ObstacleAvoidanceController oac = new ObstacleAvoidanceController(dp, lfc, uus, ucs, uls);

		
		CalibrationController cc = new CalibrationController();
	 	cc.run(ucs, uls);
		
		//Finish setup
		uh.addSensor(uus);
		sh.addSensor(uls);
		sh.addSensor(ucs);
		
		sh.start();
		uh.start();
		
		dp.setRotateSpeed(Config.MAX_SPEED/2);
		//dp.forward();
		Motor.A.forward();
		Motor.B.forward();
		
		Button.ENTER.waitForPressAndRelease();
		
	}

}
