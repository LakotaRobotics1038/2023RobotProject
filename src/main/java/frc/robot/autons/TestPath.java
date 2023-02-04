package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

public class TestPath extends Auton {
    public TestPath() {
        PathPlannerTrajectory trajectory = Trajectories.TestPath();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory));
        this.setInitialPose(trajectory);
    }
}
