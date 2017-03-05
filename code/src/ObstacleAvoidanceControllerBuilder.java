public class ObstacleAvoidanceControllerBuilder {
    //refactor introduce Builder

    private DifferentialPilot pilot;
    private LineFollowingController lineFollower;
    private UpdatingUltrasonicSensor sonicSensor;
    private UpdatingColorSensor colorSensor;
    private UpdatingLightSensor lightSensor;

    public ObstacleAvoidanceControllerBuilder setPilot(DifferentialPilot pilot) {
        this.pilot = pilot;
        return this;
    }

    public ObstacleAvoidanceControllerBuilder setLineFollower(LineFollowingController lineFollower) {
        this.lineFollower = lineFollower;
        return this;
    }

    public ObstacleAvoidanceControllerBuilder setSonicSensor(UpdatingUltrasonicSensor sonicSensor) {
        this.sonicSensor = sonicSensor;
        return this;
    }

    public ObstacleAvoidanceControllerBuilder setColorSensor(UpdatingColorSensor colorSensor) {
        this.colorSensor = colorSensor;
        return this;
    }

    public ObstacleAvoidanceControllerBuilder setLightSensor(UpdatingLightSensor lightSensor) {
        this.lightSensor = lightSensor;
        return this;
    }

    public ObstacleAvoidanceController createObstacleAvoidanceController() {
        return new ObstacleAvoidanceController(pilot, lineFollower, sonicSensor, colorSensor, lightSensor);
    }
}